package utilities;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ReadXMLUtil {
	public static String getTestData(String nodeName) {

		String finalValue = null;
		try {
			File xmlFile = new File("src\\test\\resources\\data\\Activities&HotelsData.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = String.format("//%s", nodeName);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

			if (nodeList.getLength() > 0) {
				finalValue = nodeList.item(0).getTextContent().trim();
			}
		} catch (Exception e) {
			System.err.println("Error reading XML data: " + e.getMessage());
		}
		return finalValue;

	}
}
