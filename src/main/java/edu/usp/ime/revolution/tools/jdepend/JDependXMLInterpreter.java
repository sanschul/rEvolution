package edu.usp.ime.revolution.tools.jdepend;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface JDependXMLInterpreter {

	public abstract List<JDependInfo> interpret(InputStream xml)
			throws ParserConfigurationException, SAXException, IOException;

}