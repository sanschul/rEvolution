package br.com.caelum.revolution.tools.cc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JavaNCSSOutputParseTest {

	@Test
	public void shouldParseAListWithJavaNCSSXmlOutput() {
		String xml = "<?xml version=\"1.0\"?>"
				+ "<javancss>"
				+ "<date>2011-08-20</date>"
				+ "<time>04:17:26</time>"
				+ "<objects>"
				+ "<object>"
				+ "<name>br.com.caelum.revolution.analyzers.Analyzer</name>"
				+ "<ncss>2</ncss>"
				+ "<functions>1</functions>"
				+ "<classes>0</classes>"
				+ "<javadocs>0</javadocs>"
				+ "</object>"
				+ "<object>"
				+ "<name>br.com.caelum.revolution.analyzers.AnalyzerFactory</name>"
				+ "<ncss>8</ncss>" + "<functions>1</functions>"
				+ "<classes>0</classes>" + "<javadocs>0</javadocs>"
				+ "</object>" + "</objects>" + "</javancss>";

		ParsedJavaNCSS parsedStuff = new JavaNCSSOutputParse().parse(xml);

		assertEquals(2, parsedStuff.getObjects().size());

		assertEquals("br.com.caelum.revolution.analyzers.Analyzer", parsedStuff
				.getObjects().get(0).getName());
		assertEquals(2, parsedStuff.getObjects().get(0).getNcss());
		assertEquals("br.com.caelum.revolution.analyzers.AnalyzerFactory",
				parsedStuff.getObjects().get(1).getName());
		assertEquals(8, parsedStuff.getObjects().get(1).getNcss());
	}

	@Test
	public void shouldParseEvenAnXmlWithNoObjectsAtAll() {
		String xml = "<?xml version=\"1.0\"?>" + "<javancss>"
				+ "<date>2011-08-20</date>" + "<time>04:23:24</time>"
				+ "<objects>" + "<averages>" + "<ncss>0.00</ncss>"
				+ "<functions>0.00</functions>" + "<classes>0.00</classes>"
				+ "<javadocs>0.00</javadocs>" + "</averages>"
				+ "<ncss>0.00</ncss>" + "</objects>" + "</javancss>";

		ParsedJavaNCSS parsedStuff = new JavaNCSSOutputParse().parse(xml);

		assertEquals(0, parsedStuff.getObjects().size());

	}
}
