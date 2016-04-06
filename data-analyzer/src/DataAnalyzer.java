import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

// partially reuse code from 
// http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
public class DataAnalyzer {
	public static int sum(List<Integer> data) {
		int sum = 0;
		for(Integer d : data) {
			sum += d;
		}
		return sum;
	}
	
	public static float mean(List<Integer> data) {
		int sum = 0;
		for(Integer d : data) {
			sum += d;
		}
		return sum / data.size();
	}
	
	public static double sd(List<Integer> data) {
		float meanVal = mean(data);
		double sumVal = 0;
		for(Integer d : data) {
			sumVal += Math.abs(d - meanVal) * Math.abs(d - meanVal);
		}

		return Math.sqrt(sumVal / data.size());
	}
	
	public static void main(String args[]) {
		FileReader in = null;
		try {
			in = new FileReader("p1.graphFXD.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(in);

		List<Integer> data = new ArrayList<Integer>();
		try {
			while (br.readLine() != null) {
				String line = br.readLine();
				String[] tokens = line.split("\t");
				data.add(Integer.parseInt(tokens[2]));
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("data");
			doc.appendChild(rootElement);

			Element sumElement = doc.createElement("sum");
			sumElement.appendChild(doc.createTextNode(Integer.toString(sum(data))));
			rootElement.appendChild(sumElement);
						
			Element meanElement = doc.createElement("mean");
			meanElement.appendChild(doc.createTextNode(Float.toString(mean(data))));
			rootElement.appendChild(meanElement);
			
			Element sdElement = doc.createElement("sd");
			sdElement.appendChild(doc.createTextNode(Double.toString(sd(data))));
			rootElement.appendChild(sdElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
	
			// Output to console for testing
			StreamResult result = new StreamResult(System.out);
	
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
	  }
	
	}
}
