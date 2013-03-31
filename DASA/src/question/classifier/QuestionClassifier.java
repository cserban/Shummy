package question.classifier;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;

public class QuestionClassifier {
	private  ColumnDataClassifier dataColumnClassifier;
	private Classifier<String,String> classifier;
	private static boolean trained = false;
	public static void main(String[] args) {
	    ColumnDataClassifier cdc = new ColumnDataClassifier("question.prop");
	    Classifier<String,String> cl =
	        cdc.makeClassifier(cdc.readTrainingExamples("question.train"));
	    for (String line : ObjectBank.getLineIterator("question.test")) {
	      Datum<String,String> d = cdc.makeDatumFromLine(line, 0);
	      System.out.println(line + "  ==>  " + cl.classOf(d));
	    }
	  }

	public void train(String trainFileName) {
			dataColumnClassifier = new ColumnDataClassifier("question.prop");
			classifier =
					dataColumnClassifier.makeClassifier(dataColumnClassifier.readTrainingExamples(trainFileName));
			trained = true;
	}

	public String test(String line) {
		if(trained) {
			Datum<String,String> d = dataColumnClassifier.makeDatumFromLine(line, 0);
			String c = classifier.classOf(d);
			return c;
		}
		return null;
	}
}
