package rs.raf.os.disk;

public class DiskUtil {

	/**
	 * Extract a part of an array, and return it as a new array.
	 * If there is overhead, it will be zero-filled
	 * @param data The original large array.
	 * @param start The position at which to start the extraction.
	 * @param length The length of the extraction, and size of resulting array.
	 * @return The extracted data from the larger array. If {@code start+length} is larger than
	 * the length of the original array, the remainder of data will be zero-filled.
	 */
	public static byte[] slice(byte[] data, int start, int length) {
		byte[] toReturn = new byte[length];
		
		for (int i = 0; i < length; i++) {
			if (start+i < data.length) {
				toReturn[i] = data[start+i];
			} else {
				toReturn[i] = 0;
			}
		}
		
		return toReturn;
	}
}
