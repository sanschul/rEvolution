package br.com.caelum.revolution.tools.jdepend;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface JDependXMLInterpreter {

	public abstract List<JDependMetric> interpret(InputStream xml)
			throws ParserConfigurationException, SAXException, IOException;

}