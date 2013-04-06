package tagger;

import java.io.File;
import java.io.IOException;

import common.FileInOut;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Tagger {
	File in;
	
	Tagger (String path)
	{
		this.in = new File(path);
	}
	
	public String addTags()
	{
		MaxentTagger tagger = null;
		String result = new String ();
		try {
			tagger = new MaxentTagger(
			                "models/english-bidirectional-distsim.tagger");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			result = tagger.tagString(FileInOut.readFile(this.in));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result;
	}
	

		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Tagger newTagger = new Tagger("corpus/test1");
		System.out.println(newTagger.addTags());

	}

}
