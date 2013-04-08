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
}
