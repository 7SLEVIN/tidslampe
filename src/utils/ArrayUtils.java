package utils;

public class ArrayUtils {
	static public String[] wrapElementsWith(String[] array, String left, String right) {
		String[] arr = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			arr[i] = left + array[i] + right;
		}
		return arr;
	}
	
	static public String[] wrapElementsWith(String[] array, String ch) {
		return wrapElementsWith(array, ch, ch);
	}

	// Kode fra https://github.com/apache/commons-lang
	public static String join(final String[] array, final char separator) {
	    if (array == null) {
	        return null;
	    }
	    final int startIndex = 0;
	    final int endIndex = array.length;
	    
	    final int noOfItems = endIndex - startIndex;
	    if (noOfItems <= 0) {
	        return "";
	    }
	    final StringBuilder buf = new StringBuilder(noOfItems * 16);
	    for (int i = startIndex; i < endIndex; i++) {
	        if (i > startIndex) {
	            buf.append(separator);
	        }
	        if (array[i] != null) {
	            buf.append(array[i]);
	        }
	    }
	    return buf.toString();
	}
}
