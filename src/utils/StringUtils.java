package utils;

public class StringUtils {
	// Make a string with trailing zeroes
	public static String trailingZero(int number) {
		return number < 9 ? "0" + number : "" + number;
	}
}
