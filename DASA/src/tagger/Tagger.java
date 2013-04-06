package tagger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

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
			result = tagger.tagString(readFile(this.in));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result;
	}
	
	private String readFile(File file) throws IOException {
		FileInputStream stream = new FileInputStream(file);
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Tagger newTagger = new Tagger("corpus/test1");
		System.out.println(newTagger.addTags());

	}

}
