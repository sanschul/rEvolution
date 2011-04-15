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
	public void shouldGetPackages() throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException {
		JDependXMLInterpreter interp = new DefaultJDependXMLInterpreter();
		
		List<JDependInfo> infos = interp.interpret(aStreamWith(someJDependXML()));
		
		assertEquals(1, infos.size());
		assertEquals("edu.usp.ime.revolution", infos.get(0).getName());
	}
	
	@Test
	public void shouldGetPackageStats() throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException {
		JDependXMLInterpreter interp = new DefaultJDependXMLInterpreter();
		
		List<JDependInfo> infos = interp.interpret(aStreamWith(someJDependXML()));
		
		assertEquals(1, infos.get(0).getTotalClasses(), 0.01);
		assertEquals(2, infos.get(0).getConcreteClasses(), 0.01);
		assertEquals(3, infos.get(0).getAbstractClasses(), 0.01);
		assertEquals(4, infos.get(0).getCa(), 0.01);
		assertEquals(5, infos.get(0).getCe(), 0.01);
		assertEquals(6, infos.get(0).getAbstraction(), 0.01);
		assertEquals(7.22, infos.get(0).getInstability(), 0.01);
		assertEquals(8, infos.get(0).getDistanceFromMainLine(), 0.01);
		assertEquals(9, infos.get(0).getVolatility(), 0.01);
	}
	
	@Test
	public void shouldIgnoreNotAnalyzedPackages() throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException {
		JDependXMLInterpreter interp = new DefaultJDependXMLInterpreter();
		
		List<JDependInfo> infos = interp.interpret(aStreamWith(ANotAnalyzedPackage()));
		
		assertEquals(0, infos.size());
	}
	
	@Test
	public void shouldIgnoreCyclesPackage() throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException {
		JDependXMLInterpreter interp = new DefaultJDependXMLInterpreter();
		
		List<JDependInfo> infos = interp.interpret(aStreamWith(someJDependXMLWithCycles()));
		
		assertEquals(1, infos.size());		
	}
	
	private String someJDependXMLWithCycles() {
		return 
		"<?xml version=\"1.0\"?>\n"+
		"<JDepend>\n"+
		"    <Packages>\n"+
		"        <Package name=\"edu.usp.ime.revolution\">\n"+
		"            <Stats>\n"+
		"                <TotalClasses>1</TotalClasses>\n"+
		"                <ConcreteClasses>2</ConcreteClasses>\n"+
		"                <AbstractClasses>3</AbstractClasses>\n"+
		"                <Ca>4</Ca>\n"+
		"                <Ce>5</Ce>\n"+
		"                <A>6</A>\n"+
		"                <I>7</I>\n"+
		"                <D>8</D>\n"+
		"                <V>9</V>\n"+
		"            </Stats>\n"+
		"            <AbstractClasses>\n"+
		"            </AbstractClasses>\n"+
		"            <ConcreteClasses>\n"+
		"                <Class sourceFile=\"Revolution.java\">\n"+
		"                    edu.usp.ime.revolution.Revolution\n"+
		"                </Class>\n"+
		"                <Class sourceFile=\"RevolutionFactory.java\">\n"+
		"                    edu.usp.ime.revolution.RevolutionFactory\n"+
		"                </Class>\n"+
		"                <Class sourceFile=\"Runner.java\">\n"+
		"                    edu.usp.ime.revolution.Runner\n"+
		"                </Class>\n"+
		"            </ConcreteClasses>\n"+
		"            <DependsUpon>\n"+
		"                <Package>edu.usp.ime.revolution.analyzers</Package>\n"+
		"                <Package>edu.usp.ime.revolution.analyzers.observers</Package>\n"+
		"                <Package>edu.usp.ime.revolution.builds</Package>\n"+
		"                <Package>edu.usp.ime.revolution.config</Package>\n"+
		"                <Package>edu.usp.ime.revolution.metrics</Package>\n"+
		"                <Package>edu.usp.ime.revolution.persistence</Package>\n"+
		"                <Package>edu.usp.ime.revolution.scm</Package>\n"+
		"                <Package>edu.usp.ime.revolution.tools</Package>\n"+
		"                <Package>java.io</Package>\n"+
		"                <Package>java.lang</Package>\n"+
		"            </DependsUpon>\n"+
		"            <UsedBy>\n"+
		"            </UsedBy>\n"+
		"        </Package>\n"+
		"    </Packages>\n"+
		"    <Cycles>\n"+
		"        <Package Name=\"edu.usp.ime.revolution\">\n"+
		"            <Package>edu.usp.ime.revolution.scm</Package>\n"+
		"            <Package>edu.usp.ime.revolution.builds</Package>\n"+
		"            <Package>edu.usp.ime.revolution.scm</Package>\n"+
		"        </Package>\n"+
	    "    </Cycles>\n"+
		"</JDepend>\n";
	}

	private InputStream aStreamWith(String text) throws UnsupportedEncodingException {
		return new ByteArrayInputStream(text.getBytes());
	}

	private String ANotAnalyzedPackage() {
        return
	        "<?xml version=\"1.0\"?>\n"+
			"<JDepend>\n"+
			"    <Packages>\n"+
		        	"<Package name=\"com.xyz.epayment\">\n"+ 
		        		"<error>No stats available: package referenced, but not analyzed.</error>\n"+ 
		        	"</Package>\n"+ 		
	        "    </Packages>\n"+
			"</JDepend>\n";
	}
	
	private String someJDependXML() {
		return 
		"<?xml version=\"1.0\"?>\n"+
		"<JDepend>\n"+
		"    <Packages>\n"+
		"        <Package name=\"edu.usp.ime.revolution\">\n"+
		"            <Stats>\n"+
		"                <TotalClasses>1</TotalClasses>\n"+
		"                <ConcreteClasses>2</ConcreteClasses>\n"+
		"                <AbstractClasses>3</AbstractClasses>\n"+
		"                <Ca>4</Ca>\n"+
		"                <Ce>5</Ce>\n"+
		"                <A>6</A>\n"+
		"                <I>7.22</I>\n"+
		"                <D>8</D>\n"+
		"                <V>9</V>\n"+
		"            </Stats>\n"+
		"            <AbstractClasses>\n"+
		"            </AbstractClasses>\n"+
		"            <ConcreteClasses>\n"+
		"                <Class sourceFile=\"Revolution.java\">\n"+
		"                    edu.usp.ime.revolution.Revolution\n"+
		"                </Class>\n"+
		"                <Class sourceFile=\"RevolutionFactory.java\">\n"+
		"                    edu.usp.ime.revolution.RevolutionFactory\n"+
		"                </Class>\n"+
		"                <Class sourceFile=\"Runner.java\">\n"+
		"                    edu.usp.ime.revolution.Runner\n"+
		"                </Class>\n"+
		"            </ConcreteClasses>\n"+
		"            <DependsUpon>\n"+
		"                <Package>edu.usp.ime.revolution.analyzers</Package>\n"+
		"                <Package>edu.usp.ime.revolution.analyzers.observers</Package>\n"+
		"                <Package>edu.usp.ime.revolution.builds</Package>\n"+
		"                <Package>edu.usp.ime.revolution.config</Package>\n"+
		"                <Package>edu.usp.ime.revolution.metrics</Package>\n"+
		"                <Package>edu.usp.ime.revolution.persistence</Package>\n"+
		"                <Package>edu.usp.ime.revolution.scm</Package>\n"+
		"                <Package>edu.usp.ime.revolution.tools</Package>\n"+
		"                <Package>java.io</Package>\n"+
		"                <Package>java.lang</Package>\n"+
		"            </DependsUpon>\n"+
		"            <UsedBy>\n"+
		"            </UsedBy>\n"+
		"        </Package>\n"+
		"    </Packages>\n"+
		"</JDepend>\n";
	}
}
