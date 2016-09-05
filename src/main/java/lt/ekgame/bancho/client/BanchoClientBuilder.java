package lt.ekgame.bancho.client;

import lt.ekgame.bancho.client.impl.BanchoClientImpl;

public class BanchoClientBuilder {
	
	private String username, password;
	private boolean isSecure = false, isVerbose = false; 
	
	public BanchoClientBuilder(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public BanchoClientBuilder setSecure(boolean isSecure) {
		this.isSecure = isSecure;
		return this;
	}
	
	public BanchoClientBuilder setVerbose(boolean isVerbose) {
		this.isVerbose = isVerbose;
		return this;
	}
	
	public BanchoClient build() {
		BanchoClientImpl client = new BanchoClientImpl(username, password, isSecure, isVerbose);
		client.start();
		return client;
	}

}
