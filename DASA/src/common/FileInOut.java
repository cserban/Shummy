package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class FileInOut {
	
	
	public static String readFile(File file) throws IOException {
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
	
	/*
	 * Returns all filenames within a specific folder
	 */
	public static ArrayList<String> getFiles(File folder) {
        ArrayList<String> filenames = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            filenames.add(folder.getAbsolutePath() + "/" + fileEntry.getName());
        }
        return filenames;
    }
}
