package utilities;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Utility class for reading test data from XML files using XPath. Designed to
 * extract values based on node names from structured XML documents.
 */
public class ReadXMLUtil {

	/**
	 * Fetches the value of the first XML node matching the given node name. Useful
	 * for retrieving static test data from an XML file.
	 *
	 * @param nodeName The tag name of the node to search for
	 * @return The trimmed text content of the node, or null if not found
	 */
	public static String getTestData(String nodeName) {
		String finalValue = null;
		try {
			File xmlFile = new File("src\\test\\resources\\data\\Activities&HotelsData.xml");

			// Parse XML file
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// Apply XPath to find matching node
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
