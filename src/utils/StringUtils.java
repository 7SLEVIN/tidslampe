package utils;

public class StringUtils {
	// Kode taget fra https://github.com/apache/commons-lang
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
