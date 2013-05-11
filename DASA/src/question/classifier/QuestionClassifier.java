package question.classifier;

import java.util.HashMap;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;

public class QuestionClassifier {
	private ColumnDataClassifier dataColumnClassifier;
	private Classifier<String, String> classifier;
	private static boolean trained = false;
	private static HashMap<String, Integer> classTotal = new HashMap<String, Integer>();
	private static HashMap<String, Count> classCount = new HashMap<String, Count>();
	private static HashMap<String, Integer> classGTotal = new HashMap<String, Integer>();
	private static HashMap<String, Count> classGCount = new HashMap<String, Count>();
	private static boolean computeAccuracy = true;
	private static int total = 0;

	public QuestionClassifier() {
		train("question/question_5500.train");
	}

	private static void oldMain() {
		ColumnDataClassifier cdc = new ColumnDataClassifier(
				"question/question.prop");
		Classifier<String, String> cl = cdc.makeClassifier(cdc
				.readTrainingExamples("question/question_5500.train"));
		for (String line : ObjectBank.getLineIterator("question/question.test")) {
			Datum<String, String> d = cdc.makeDatumFromLine(line, 0);
			String classLabel = cl.classOf(d);
			System.out.println(line + "  ==>  " + classLabel);
			if (computeAccuracy) {
				total++;
				int index = line.indexOf("\t");
				if (index >= 0) {
					String actualClass = line.substring(0, index);
					// count them
					Integer count = classTotal.get(actualClass);
					count = (count == null) ? new Integer(1) : new Integer(
							count.intValue() + 1);
					classTotal.put(actualClass, count);
					// true positives and such
					Count c = classCount.get(actualClass);
					Count c1 = classCount.get(classLabel);
					c = (c == null) ? new Count() : c;
					c1 = (c1 == null) ? new Count() : c1;
					if (classLabel.equals(actualClass)) {
						c.tp = c.tp + 1;
						increaseTNToAllExcept(classCount, actualClass);
						classCount.put(actualClass, c);
					} else {
						c1.fp = c1.fp + 1;
						classCount.put(classLabel, c1);
						c.fn = c.fn + 1;
						classCount.put(actualClass, c);
					}

					actualClass = actualClass.substring(0,
							actualClass.indexOf(":"));
					classLabel = classLabel.substring(0,
							classLabel.indexOf(":"));
					count = classGTotal.get(actualClass);
					count = (count == null) ? new Integer(1) : new Integer(
							count.intValue() + 1);
					classGTotal.put(actualClass, count);
					// true positives and such
					c = classGCount.get(actualClass);
					c1 = classGCount.get(classLabel);
					c = (c == null) ? new Count() : c;
					c1 = (c1 == null) ? new Count() : c1;
					if (classLabel.equals(actualClass)) {
						c.tp = c.tp + 1;
						increaseTNToAllExcept(classGCount, actualClass);
						classGCount.put(actualClass, c);
					} else {
						c1.fp = c1.fp + 1;
						classGCount.put(classLabel, c1);
						c.fn = c.fn + 1;
						classGCount.put(actualClass, c);
					}
				}
			}
		}
		if (computeAccuracy) {
			double precision = 0;
			double recall = 0;
			double fscore = 0;
			for (String c : classCount.keySet()) {
				Count count = classCount.get(c);
				if (classTotal.get(c) == null) {
					classTotal.put(c, new Integer(0));
				}
				count.precision = ((count.tp + count.fp) == 0) ? 0
						: (double) count.tp / (double) (count.tp + count.fp);
				count.recall = ((count.tp + count.fn) == 0) ? 0
						: (double) count.tp / (double) (count.tp + count.fn);
				count.fscore = ((count.precision + count.recall) == 0) ? 0
						: (double) (2 * count.precision * count.recall)
								/ (double) (count.precision + count.recall);
				System.out.println(c + "[" + classTotal.get(c) + "] " + count
						+ " ->" + count.precision + " " + count.recall + " "
						+ count.fscore + " ");
				precision += (classTotal.get(c).doubleValue() / (double) total)
						* count.precision;
				recall += (classTotal.get(c).doubleValue() / (double) total)
						* count.recall;
				fscore += (classTotal.get(c).doubleValue() / (double) total)
						* count.fscore;
			}
			fscore = (2 * precision * recall) / (precision + recall);

			double precisionG = 0;
			double recallG = 0;
			double fscoreG = 0;
			for (String c : classGCount.keySet()) {
				Count count = classGCount.get(c);
				if (classGTotal.get(c) == null) {
					classGTotal.put(c, new Integer(0));
				}
				count.precision = ((count.tp + count.fp) == 0) ? 0
						: (double) count.tp / (double) (count.tp + count.fp);
				count.recall = ((count.tp + count.fn) == 0) ? 0
						: (double) count.tp / (double) (count.tp + count.fn);
				count.fscore = ((count.precision + count.recall) == 0) ? 0
						: (double) (2 * count.precision * count.recall)
								/ (double) (count.precision + count.recall);
				System.out.println(c + "[" + classGTotal.get(c) + "] " + count
						+ " ->" + count.precision + " " + count.recall + " "
						+ count.fscore + " ");
				precisionG += (classGTotal.get(c).doubleValue() / (double) total)
						* count.precision;
				recallG += (classGTotal.get(c).doubleValue() / (double) total)
						* count.recall;
				fscoreG += (classGTotal.get(c).doubleValue() / (double) total)
						* count.fscore;
			}
			fscoreG = (2 * precisionG * recallG) / (precisionG + recallG);
			System.out.println("Final: " + precision + " " + recall + " "
					+ fscore);
			System.out.println("Final G: " + precisionG + " " + recallG + " "
					+ fscoreG);
		}
	}

	public static void main(String[] args) {
		QuestionClassifier qc = new QuestionClassifier();
		String predictedAnswerClass = qc
				.testQuestion("What is the life expectancy for crickets ?");
		System.out.println(predictedAnswerClass);
		predictedAnswerClass = qc
				.testQuestion("Where does Nicolas Vaaali live ?");
		System.out.println(predictedAnswerClass);
	}

	public void train(String trainFileName) {
		dataColumnClassifier = new ColumnDataClassifier(
				"question/question.prop");
		classifier = dataColumnClassifier.makeClassifier(dataColumnClassifier
				.readTrainingExamples(trainFileName));
		trained = true;
	}

	public String testQuestion(String question) {
		String line = "ceva\t" + question;
		return mapQuestionClass(test(line));
	}

	public static String mapQuestionClass(String questionClass) {
		if(questionClass.startsWith("LOC"))
			return "LOCATION";
		if(questionClass.equals(Topology.NUM_date)) {
			return "DATE";
		}
		if(questionClass.equals(Topology.HUM_gr)) {
			return "ORGANIZATION";
		}
		if(questionClass.startsWith("HUM")) {
			return "PERSON";
		}
		if(questionClass.equals(Topology.NUM_ord)) {
			return "ORDINAL";
		}
		if(questionClass.equals(Topology.NUM_period)) {
			return "DURATION";
		}
		if(questionClass.startsWith("NUM"))
			return "NUMBER";
		//TODO: SET????
		return "MISC";
	}

	public String test(String line) {
		if (trained) {
			Datum<String, String> d = dataColumnClassifier.makeDatumFromLine(
					line, 0);
			String c = classifier.classOf(d);
			return c;
		}
		return null;
	}

	// for the n binary classification problems
	public static void increaseTNToAllExcept(HashMap<String, Count> count,
			String key) {
		for (String keys : count.keySet()) {
			if (key.toUpperCase().equals(keys)) {
				continue;
			} else {
				Count c = count.get(keys);
				c.tn++;
				count.put(keys, c);
			}
		}
	}

}

class Count {
	int tp = 0;
	int tn = 0;
	int fp = 0;
	int fn = 0;
	double fscore = 0;
	double precision = 0;
	double recall = 0;

	public String toString() {
		// TODO Auto-generated method stub
		return "[" + tp + ";" + tn + ";" + fp + ";" + fn + "]";
	}
}
