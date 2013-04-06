package preprocessor;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import tagger.Tagger;

import common.Constants;
import common.FileInOut;
import common.StringParse;


public class Preprocessor {
	
	File corpus_folder;
	File stopwords_file;
	
	public Preprocessor() {
		corpus_folder = new File(Constants.CORPUS_FOLDERNAME);
	}
	
	/*
	 * Preprocessing includes:
	 * 		- splitting all corpus in sentences, group by files
	 * 		- tagging all words using Stanford Tagger
	 * 		- removing punctuation ?
	 * 		- removing all stopwords
	 * 		- saving the parsed graph from all tagged words
	 */
	public void preprocess() {
		ArrayList<String> corpus_filenames = FileInOut.getFiles(corpus_folder);
		Hashtable<String, String> tagged_content = tagSentences(corpus_filenames);
		Hashtable<String, ArrayList<Sentence>> tagged_sentences = StringParse.splitInSentences(tagged_content);
		System.out.println("Done preprocessing! :)");
	}
	
	public Hashtable<String, String> tagSentences(ArrayList<String> filenames) {
		Hashtable<String, String> tagged_sentences = new Hashtable<>();
		for (String filename : filenames) {
			Tagger tagger = new Tagger(filename);
			tagged_sentences.put(filename, tagger.addTags());
		}
		return tagged_sentences;
	}
	
	// TODO: delete this main
	public static void main(String[] args) {
        Preprocessor preprocessor = new Preprocessor();
        preprocessor.preprocess();
        
    }
	
}
