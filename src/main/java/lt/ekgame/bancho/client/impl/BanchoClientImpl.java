package lt.ekgame.bancho.client.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;

import lt.ekgame.bancho.api.exceptions.LoginException;
import lt.ekgame.bancho.api.exceptions.StateException;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.packets.Packets;
import lt.ekgame.bancho.api.packets.client.*;
import lt.ekgame.bancho.api.packets.server.PacketLoginReply.Status;
import lt.ekgame.bancho.client.BanchoClient;
import lt.ekgame.bancho.client.EventHandler;
import lt.ekgame.bancho.client.PacketHandler;
import lt.ekgame.bancho.client.RateLimiter;
import lt.ekgame.bancho.client.channels.ChannelManager;
import lt.ekgame.bancho.client.channels.impl.ChannelManagerImpl;
import lt.ekgame.bancho.client.events.Event;
import lt.ekgame.bancho.client.events.EventPostUpdate;
import lt.ekgame.bancho.client.events.EventPreUpdate;

public class BanchoClientImpl extends Thread implements BanchoClient {
	
	public static final String SCHEME_DEFAULT = "http";
	public static final String SCHEME_SECURE = "https";
	public static final String OSU_BANCHO_CONNECT = "/web/bancho_connect.php";
	
	public static final String OSU_HOST = "osu.ppy.sh";
	public static final String BANCHO_HOST = "c.ppy.sh";
	
	public static final String CLIENT_VERSION = "b20160403.6";
	public static final String EXE_HASH = "35f34a2110e715864cea6ebb5d7d7df8";
	
	public String username;
	public String password;
	public String countryCode;
	private String token = null;
	private HttpClient httpClient;
	private boolean isSecure = false;
	private boolean verbose = false;
	
	public static URI BANCHO_URI;
	
	private Queue<Packet> outgoingPackets = new LinkedList<>();
	private List<PacketHandler> packetHandlers = new ArrayList<>();
	private List<EventHandler> eventHandlers = new ArrayList<>();
	
	private BanchoClientManager clientManager;
	private ChannelManagerImpl channelManager;
	private RateLimiter messageRateLimiter = new RateLimiterImpl(1000);
	
	private boolean isRunning = true;
	
	public BanchoClientImpl(String username, String password, boolean secure, boolean verbose) {
		this.username = username;
		this.password = DigestUtils.md5Hex(password);
		this.isSecure = secure;
		this.verbose = verbose;
		
		try {
			BANCHO_URI = new URIBuilder()
					.setScheme(isSecure ? SCHEME_SECURE : SCHEME_DEFAULT)
					.setHost(BANCHO_HOST)
					.setPath("/")
					.build();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		
		addPacketHandler(clientManager = new BanchoClientManager(this));
		addPacketHandler(channelManager = new ChannelManagerImpl(this, messageRateLimiter));
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	
	public void onLoginSuccess(int userId) {
		System.out.println("Successfully logged in with client ID: " + userId);
		
	}
	
	public void onLoginFailed(Status status) {
		System.out.println("Login failed: " + status);
		isRunning = false;
	}
	
	public void run() {
		// 10 second timeout
		RequestConfig defaultRequestConfig = RequestConfig.custom()
		    .setSocketTimeout(10000)
		    .setConnectTimeout(10000)
		    .setConnectionRequestTimeout(10000)
		    .build();
		
		httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
		
		while (isRunning) {
			try {
				authentifyBancho();
				System.out.println("Update loop started.");
				doConnectionLoop();
				System.out.println("Disconnected, reconnecting.");
			} catch (LoginException e) {
				e.printStackTrace();
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Bancho loop stopped.");
	}
	
	private void authentifyBancho() throws IOException, LoginException {
		if (httpClient == null)
			throw new StateException("Invalid HTTP client.");
		
		HttpClientContext httpContext = new HttpClientContext();
		httpContext.setCookieStore(new BasicCookieStore());
		
		String requestBody = username + "\n" + password + "\n";
		// Documentation for the third line:
		// (osu client version)|(UTC offset)|(1 if your city should be public, 0 otherwise)|(MD5 hashed described below)|(1 if non-friend PMs should be blocked, 0 otherwise)
		// 4th argument: <MD5 hash for the executable>::<MD5 for empty string>:<MD5 for "unknown">:<MD5 for "unknown">
		// Only the first one seems to really matter.
		// Latest MD5 hash for the osu!.exe executable can be found here: https://goo.gl/IVUVA3
		TimeZone tz = TimeZone.getDefault();
		int offset = tz.getRawOffset()/3600000;
		requestBody += CLIENT_VERSION + "|" + offset + "|0|" + EXE_HASH + "::d41d8cd98f00b204e9800998ecf8427e:ad921d60486366258809553a3db49a4a:ad921d60486366258809553a3db49a4a:|0" + "\n";
		HttpEntity entity = new ByteArrayEntity(requestBody.getBytes("UTF-8"));
		
		HttpPost request = new HttpPost(BANCHO_URI);
		request.setEntity(entity);
		request.addHeader("osu-version", CLIENT_VERSION);
		request.addHeader("Accept-Encoding", "gzip");
		request.addHeader("User-Agent", "osu!");
		request.addHeader("Connection", "Keep-Alive");
		
		HttpResponse response = httpClient.execute(request, httpContext);
		
		for (Header header : response.getAllHeaders()) {
			if (header.getName().equals("cho-token")) {
				token = header.getValue();
			}
		}

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			System.out.println("Error: " + statusCode);
			if (statusCode != 520) {
				httpClient = null; // invalidate http client
				throw new LoginException("Failed to authentify to bancho (invalid creditials? offline?)");
			} else {
				System.out.println("Server error detected.");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {}
			}
		}
		
		InputStream content = response.getEntity().getContent();
		handleBanchoResponse(response, content);
	}

	long lastRequest = 0;
	
	public void doConnectionLoop() {
		int num403 = 0;
		while (isRunning) {
			dispatchEvent(new EventPreUpdate());
			
			Packet outgoingMessage = messageRateLimiter.getOutgoingPacket();
			if (outgoingMessage != null) sendPacket(outgoingMessage);
			
			HttpClientContext httpContext = new HttpClientContext();
			httpContext.setCookieStore(new BasicCookieStore());
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteDataOutputStream stream = new ByteDataOutputStream(out, this);
			
			lastRequest = System.currentTimeMillis();
			
			synchronized (outgoingPackets) {
				if (outgoingPackets.isEmpty())
					outgoingPackets.add(new PacketIdle());
				
				while (!outgoingPackets.isEmpty()) {
					Packet packet = outgoingPackets.poll();
					short id = (short) Packets.getId(packet);
					if (id == -1) {
						System.err.println("Can't find ID for " + packet.getClass());
						continue;
					}
					if (verbose && !(packet instanceof PacketIdle))
						System.out.printf("out >>  %s\n", packet.getClass().getName());
					try {
						stream.writeShort(id);
						stream.writeByte((byte) 0);
						stream.writeInt(packet.size(this));
						packet.write(stream);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				HttpEntity entity = new ByteArrayEntity(out.toByteArray());
				
				HttpPost request = new HttpPost(BANCHO_URI);
				request.setEntity(entity);
				request.addHeader("osu-token", token);
				request.addHeader("Accept-Encoding", "gzip");
				request.addHeader("User-Agent", "osu!");
				request.addHeader("Connection", "Keep-Alive");
				
				try {
					// XXX Packets will be lost if this fails
					HttpResponse response = httpClient.execute(request, httpContext);
					if (response.getStatusLine().getStatusCode() == 403) {
						System.out.println("Received 403");
						num403++;              // 403 means disconnected in this case, might be an error, so try a few more times
						if (num403 > 5) break; // end loop, force reconnect
					}
					else if (response.getStatusLine().getStatusCode() == 200) {
						handleBanchoResponse(response, response.getEntity().getContent());
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Thread.sleep(500); // on error, wait a little longer before continuing
					} catch (InterruptedException e1) {}
				} finally {
					request.releaseConnection();
				}
				
				dispatchEvent(new EventPostUpdate());
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	private void handleBanchoResponse(HttpResponse response, InputStream content) throws IOException {
		ByteDataInputStream in = new ByteDataInputStream(content, this);
		try {
			while (true) {
				int type = in.readShort();
				in.skipBytes(1);
				int len = in.readInt();
				byte[] bytes = new byte[len];
				for (int i = 0; i < len; i++)
					bytes[i] = in.readByte();
				
				Class<? extends Packet> packetClass = Packets.getById(type);
				if (packetClass != null) {
					try {
						Packet packet = (Packet) packetClass.newInstance();
						//if (verbose && !(packet instanceof PacketIdle) && !(packet instanceof PacketUserStats) && !(packet instanceof PacketUserPresenceSingle) && !(packet instanceof PacketUnknown0C)
						// && !(packet instanceof PacketRoomUpdate) && !(packet instanceof PacketUnknown1B)&& !(packet instanceof PacketUnknown1C))
						if (verbose)
							System.out.printf(" in >>  %s\n", packet.getClass().getName());
						ByteDataInputStream stream = new ByteDataInputStream(new ByteArrayInputStream(bytes), this);
						packet.read(stream, len);
						handlePacket(packet);
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					if (verbose) {
						System.out.printf("Unknown packet: %02X %08X \n- Data: ", type, len);
						for (int i = 0; i < len; i++)
							System.out.printf("%02x ", bytes[i]);
						System.out.println();
					}
				}
			}
			
		} catch (EOFException e) {
			// Finished
		}
	}
	
	private void handlePacket(Packet packet) {
		synchronized (packetHandlers) {
			for (PacketHandler handler : packetHandlers)
				handler.handlePacket(packet);
		}
	}
	
	@Override
	public void sendPacket(Packet packet) {
		synchronized (outgoingPackets) {
			outgoingPackets.add(packet);
		}
	}
	
	@Override
	public int getProtocolVersion() {
		return clientManager.getProtocolVersion();
	}
	
	@Override
	public boolean isConnected() {
		return clientManager.isConnected();
	}
	
	@Override
	public void dispatchEvent(Event event) {
		for (EventHandler handler : eventHandlers)
			handler.handleEvent(event);
	}

	@Override
	public void addEventHandler(EventHandler handler) {
		synchronized (eventHandlers) {
			if (!eventHandlers.contains(handler))
				eventHandlers.add(handler);
		}
	}

	@Override
	public void removeEventHandler(EventHandler handler) {
		synchronized (eventHandlers) {
			if (eventHandlers.contains(handler))
				eventHandlers.remove(handler);
		}
	}

	@Override
	public void addPacketHandler(PacketHandler handler) {
		synchronized (packetHandlers) {
			if (!packetHandlers.contains(handler))
				packetHandlers.add(handler);
		}
	}

	@Override
	public void removePacketHandler(PacketHandler handler) {
		synchronized (packetHandlers) {
			if (packetHandlers.contains(handler))
				packetHandlers.remove(handler);
		}
	}

	@Override
	public BanchoClientManager getClientManager() {
		return clientManager;
	}

	@Override
	public ChannelManager getChannelManager() {
		return channelManager;
	}
}
