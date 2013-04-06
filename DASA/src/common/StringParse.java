package common;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import preprocessor.Sentence;
import preprocessor.Word;

public class StringParse {
	
	@SuppressWarnings("rawtypes")
	public static Hashtable<String, ArrayList<Sentence>> splitInSentences(Hashtable<String, String> tagged_content) {
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        Hashtable<String, ArrayList<Sentence>> sentences_per_file = new Hashtable<String, ArrayList<Sentence>>();
        
        Iterator<Entry<String, String>> it = tagged_content.entrySet().iterator();
        while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
            String filename = (String)pairs.getKey();
            String content = (String)pairs.getValue();
            ArrayList<Sentence> splitSentences = new ArrayList<>();
            
            iterator.setText(content);
            int start = iterator.first();
            for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            	String tagged_sentence = content.substring(start,end);
                splitSentences.add(new Sentence(tagged_sentence));
            }
            sentences_per_file.put(filename, splitSentences);
        }
        return sentences_per_file;
	}
}
