package edu.usp.ime.revolution.tools.jdepend;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JDependXMLInterpreter {

	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	private void startXMLEngine(InputStream xml) throws ParserConfigurationException, SAXException, IOException {
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();
		doc = docBuilder.parse (xml);
		doc.getDocumentElement().normalize();
	}
	public List<JDependInfo> interpret(InputStream xml) throws ParserConfigurationException, SAXException, IOException {
		startXMLEngine(xml);
		
		List<JDependInfo> infos = new ArrayList<JDependInfo>();
		
		NodeList listOfPackages = doc.getElementsByTagName("Package");
		for(int i=0; i < listOfPackages.getLength(); i++) {
			Node packageNode = listOfPackages.item(i);
			if (isElementNode(packageNode) && belongsToPackages(packageNode)) {
				infos.add(parsePackageInfo(packageNode));
			}
		}
		
		return infos;
	}
	private boolean belongsToPackages(Node packageNode) {
		return packageNode.getParentNode().getNodeName().equals("Packages");
	}
	private boolean isElementNode(Node packageNode) {
		return packageNode.getNodeType() == Node.ELEMENT_NODE;
	}
	
	private JDependInfo parsePackageInfo(Node item) {
		return new JDependInfo(item.getAttributes().getNamedItem("name").getNodeValue());
	}

}