package br.com.caelum.revolution.tools.cc;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class JavaNCSSOutputParse {

	public ParsedJavaNCSS parse(String xml) {
		XStream xs = new XStream(new DomDriver());
		xs.alias("javancss", ParsedJavaNCSS.class);
		xs.alias("object", ParsedJavaNCSSObject.class);
		
		return (ParsedJavaNCSS) xs.fromXML(xml);
	}

}
