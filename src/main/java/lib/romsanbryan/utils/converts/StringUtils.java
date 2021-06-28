package lib.romsanbryan.utils.converts;

public class StringUtils {

	public static String capitalice(String str) {
		str = lowerString(str);
		StringBuilder sb = new StringBuilder(str.length());
		sb.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));
		return sb.toString();
	}
	
	public static String capitaliceAll(String str) {
		str = lowerString(str);
		StringBuilder sb = new StringBuilder(str.length());
		String[] words = str.split(" ");
		for (String word : words) {
			if (word.length() > 1)
				sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
			else
				sb.append(word.toUpperCase());

			sb.append(" ");
		}
		return sb.toString().trim();
	}
	
	public static String lowerString(String str) {
		if (str == null)
			return null;
		return str.toLowerCase();
	}
}