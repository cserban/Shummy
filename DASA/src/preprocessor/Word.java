package preprocessor;

import wordnet.WordNetInterface;

import common.Constants;
import common.TaggerUtils;


public class Word {
	public String str;
	public String tag;
	public String[] synonims = new String[Constants.MAX_SYNONIMS];
	
	public Word() {
	}

	public Word(String str) {
		this.str = str;
	}

	public Word(String str, String tag) {
		this.str = str;
		this.tag = tag;
	};
	
	public void addSynonims() {
		String pos_tag = TaggerUtils.fromStanfordTagToWordnetTag(tag);
		if (pos_tag.length() > 0) {
			String[] synonims = WordNetInterface.getAllSynonymsForPOS(str, pos_tag, Constants.MAX_SYNONIMS);
			this.synonims = synonims;
		}
	}

	@Override
	public String toString() {
		return "( " + str + ", " + tag + " )";
	}
	
}
