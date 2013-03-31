package question.classifier;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.classify.LinearClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;
import edu.stanford.nlp.util.ErasureUtils;

public class QuestionClassifier {
	public static void main(String[] args) {
	    ColumnDataClassifier cdc = new ColumnDataClassifier("question.prop");
	    Classifier<String,String> cl =
	        cdc.makeClassifier(cdc.readTrainingExamples("question.train"));
	    for (String line : ObjectBank.getLineIterator("question.test")) {
	      Datum<String,String> d = cdc.makeDatumFromLine(line, 0);
	      System.out.println(line + "  ==>  " + cl.classOf(d));
	    }
	  }
}
