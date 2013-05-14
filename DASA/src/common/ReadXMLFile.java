package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXMLFile {

    public static void read() {
        try {
            File file = new File(Constants.QA4MRE_FILE);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            if (doc.hasChildNodes())
                printNote(doc.getChildNodes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printNote(NodeList nodeList) {
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.getNodeName().equals("doc")) {
                    // get d_id
                    Node node = tempNode.getAttributes().item(0);
                    String d_id = node.getNodeValue();
                    // write document content to file
                    Writer writer = null;
                    try {
                        writer = new BufferedWriter(new OutputStreamWriter(
                              new FileOutputStream(Constants.QA4MRE_FOLDER + "docs/" + d_id + ".txt"), "utf-8"));
                        writer.write(tempNode.getTextContent());
                    } catch (IOException ex){
                    } finally {
                       try {writer.close();} catch (Exception ex) {}
                    }
                }
                if (tempNode.getNodeName().equals("q_str")) {
                	// get doc_id
                	Node node = tempNode.getParentNode().getParentNode().getAttributes().item(0);
                    String d_id = node.getNodeValue();
                    // get question_id
                    node = tempNode.getParentNode().getAttributes().item(0);
                    String q_id = node.getNodeValue();
                    // write question content to file
                    Writer writer = null;
                    try {
                        writer = new BufferedWriter(new OutputStreamWriter(
                              new FileOutputStream(Constants.QA4MRE_FOLDER + "questions/" + d_id + "_" + q_id + ".txt"), "utf-8"));
                        writer.write(tempNode.getTextContent());
                    } catch (IOException ex){
                    } finally {
                       try {writer.close();} catch (Exception ex) {}
                    }
                }
                if (tempNode.getNodeName().equals("answer")) {
                	// get doc_id
                	Node node = tempNode.getParentNode().getParentNode().getAttributes().item(0);
                    String d_id = node.getNodeValue();
                    // get question_id
                    node = tempNode.getParentNode().getAttributes().item(0);
                    String q_id = node.getNodeValue();
                    node = tempNode.getAttributes().item(0);
                    String a_id = node.getNodeValue();
                    if (tempNode.getTextContent().toUpperCase().equals(Constants.DEFAULT_ANSWER) == false)
                    {
	                    // write each answer content to a file
	                    Writer writer = null;
	                    try {
	                        writer = new BufferedWriter(new OutputStreamWriter(
	                              new FileOutputStream(Constants.QA4MRE_FOLDER + "answers/" + d_id + "_" + q_id + "_" + a_id + ".txt"), "utf-8"));
	                        writer.write(tempNode.getTextContent());
	                    } catch (IOException ex){
	                    } finally {
	                       try {writer.close();} catch (Exception ex) {}
	                    }
                    }
                }
                if (tempNode.hasChildNodes()) {
                    // loop again if has child nodes
                    printNote(tempNode.getChildNodes());
                }
            }
        }
    }
}