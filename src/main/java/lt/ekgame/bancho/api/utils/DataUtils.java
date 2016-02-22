package lt.ekgame.bancho.api.utils;

import java.nio.charset.Charset;

public class DataUtils {
	
	/**
	 * Calculates the size of a string in a byte array.
	 * @param string The string to calculate.
	 * @return Length of the string header and data in bytes.
	 */
	public static int stringLen(String string) {
		if (string == null)
			return 1;
		int size = 2;
		int integer = string.getBytes(Charset.forName("UTF-8")).length;
		size += integer;
		long v = integer & 0xFFFFFFFF;
        while (v >= 0x80) { 
        	size++;
            v >>= 7;
        }
        return size;
	}

}
