package edu.usp.ime.revolution.tools.jdepend;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class JDependXMLInterpreterTest {

	@Test
	public void ShouldGetPackages() throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException {
		JDependXMLInterpreter interp = new JDependXMLInterpreter();
		
		List<JDependInfo> infos = interp.interpret(aStreamWith(someJDependXML()));
		
		assertEquals(1, infos.size());
		assertEquals("edu.usp.ime.revolution", infos.get(0).getName());
	}
	
	@Test
	public void ShouldGetPackageStats() throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException {
		JDependXMLInterpreter interp = new JDependXMLInterpreter();
		
		List<JDependInfo> infos = interp.interpret(aStreamWith(someJDependXML()));
		
		assertEquals(1, infos.get(0).getTotalClasses());
		assertEquals(2, infos.get(0).getConcreteClasses());
		assertEquals(3, infos.get(0).getAbstractClasses());
		assertEquals(4, infos.get(0).getCa());
		assertEquals(5, infos.get(0).getCe());
		assertEquals(6, infos.get(0).getAbstraction());
		assertEquals(7, infos.get(0).getInstability());
		assertEquals(8, infos.get(0).getDistanceFromMainLine());
		assertEquals(9, infos.get(0).getVolatility());
	}
	
	private InputStream aStreamWith(String text) throws UnsupportedEncodingException {
		return new ByteArrayInputStream(text.getBytes());
	}

	private String someJDependXML() {
		return 
		"<?xml version=\"1.0\"?>"+
		"<JDepend>"+
		"    <Packages>"+
		"        <Package name=\"edu.usp.ime.revolution\">"+
		"            <Stats>"+
		"                <TotalClasses>1</TotalClasses>"+
		"                <ConcreteClasses>2</ConcreteClasses>"+
		"                <AbstractClasses>3</AbstractClasses>"+
		"                <Ca>4</Ca>"+
		"                <Ce>5</Ce>"+
		"                <A>6</A>"+
		"                <I>7</I>"+
		"                <D>8</D>"+
		"                <V>9</V>"+
		"            </Stats>"+
		"            <AbstractClasses>"+
		"            </AbstractClasses>"+
		"            <ConcreteClasses>"+
		"                <Class sourceFile=\"Revolution.java\">"+
		"                    edu.usp.ime.revolution.Revolution"+
		"                </Class>"+
		"                <Class sourceFile=\"RevolutionFactory.java\">"+
		"                    edu.usp.ime.revolution.RevolutionFactory"+
		"                </Class>"+
		"                <Class sourceFile=\"Runner.java\">"+
		"                    edu.usp.ime.revolution.Runner"+
		"                </Class>"+
		"            </ConcreteClasses>"+
		"            <DependsUpon>"+
		"                <Package>edu.usp.ime.revolution.analyzers</Package>"+
		"                <Package>edu.usp.ime.revolution.analyzers.observers</Package>"+
		"                <Package>edu.usp.ime.revolution.builds</Package>"+
		"                <Package>edu.usp.ime.revolution.config</Package>"+
		"                <Package>edu.usp.ime.revolution.metrics</Package>"+
		"                <Package>edu.usp.ime.revolution.persistence</Package>"+
		"                <Package>edu.usp.ime.revolution.scm</Package>"+
		"                <Package>edu.usp.ime.revolution.tools</Package>"+
		"                <Package>java.io</Package>"+
		"                <Package>java.lang</Package>"+
		"            </DependsUpon>"+
		"            <UsedBy>"+
		"            </UsedBy>"+
		"        </Package>"+
		"    </Packages>"+
		"</JDepend>";
	}
}
