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
	public void ShouldGetPackageStats() throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException {
		JDependXMLInterpreter interp = new JDependXMLInterpreter();
		
		List<JDependInfo> infos = interp.interpret(aStreamWith(someJDependXML()));
		
		assertEquals(1, infos.size());
		assertEquals("edu.usp.ime.revolution", infos.get(0).getName());
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
		"                <TotalClasses>3</TotalClasses>"+
		"                <ConcreteClasses>3</ConcreteClasses>"+
		"                <AbstractClasses>0</AbstractClasses>"+
		"                <Ca>0</Ca>"+
		"                <Ce>10</Ce>"+
		"                <A>0</A>"+
		"                <I>1</I>"+
		"                <D>0</D>"+
		"                <V>1</V>"+
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
