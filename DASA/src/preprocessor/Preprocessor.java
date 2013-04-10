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
	
	Hashtable<String, ArrayList<Sentence>> clean_sentences;
	ArrayList<String> corpus_filenames;
	
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
		corpus_filenames = FileInOut.getFiles(corpus_folder);
		Hashtable<String, String> tagged_content = tagSentences(corpus_filenames);
		Hashtable<String, ArrayList<Sentence>> tagged_sentences = StringParse.splitInSentences(tagged_content);
		clean_sentences = StringParse.removeStopwordsAndPunctuation(tagged_sentences);
		addSynonims(clean_sentences);
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

	@SuppressWarnings("unchecked")
	public void addSynonims(Hashtable<String, ArrayList<Sentence>> sentences) {
		// add synonims to all words in sentences
		Iterator<?> it = sentences.entrySet().iterator();
	    while (it.hasNext()) {
	        @SuppressWarnings("rawtypes")
			Map.Entry pairs = (Map.Entry)it.next();
	        for (Sentence s : (ArrayList<Sentence>)pairs.getValue()) {
	        	s.addSynonims();
	        }
	    }
	}
	
	public void printSentences()
	{
        for (String fileName:corpus_filenames)
        {
        	for (Sentence sentence:clean_sentences.get(fileName))
        	{
        		System.out.println(sentence);
        	}
        }
	}

	// TODO: delete this main
	public static void main(String[] args) {
        Preprocessor preprocessor = new Preprocessor();
        preprocessor.preprocess();
        preprocessor.printSentences();

    }
}
