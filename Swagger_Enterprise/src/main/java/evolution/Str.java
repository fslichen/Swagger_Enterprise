package evolution;

public class Str {
	public static String minus(String string0, String string1) {
		return string0.substring(string1.length() + 1);
	}
	
	public static void println(Object object) {
		System.out.println(object);
	}
	
	public static Integer count(String string, char character) {
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == character) {
				count++;
			}
		}
		return count;
	}
	
	public static Integer backIndexOf(String string, char character, int occurrence) {
		int i = string.length() - 1;
		int count = 0;
		for (; i >= 0; i--) {
			if (string.charAt(i) == character) {
				count++;
				if (count == occurrence) {
					return i;
				}
			}
		}
		return -1;
	}
}
