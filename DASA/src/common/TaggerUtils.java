package common;

public class TaggerUtils {
	public static String fromStanfordTagToWordnetTag(String tag) {
		String pos_tag = "";
		if (tag.startsWith("V"))
			pos_tag = "v";
		else if (tag.startsWith("J"))
			pos_tag = "a";
		else if (tag.startsWith("RB"))
			pos_tag = "r";
		else
			pos_tag = Constants.DEFAULT_POS;
		
		return pos_tag;
	}
}
