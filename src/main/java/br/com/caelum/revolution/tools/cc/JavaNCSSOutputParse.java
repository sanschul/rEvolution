package br.com.caelum.revolution.tools.cc;

import com.thoughtworks.xstream.XStream;

public class JavaNCSSOutputParse {

	public ParsedJavaNCSS parse(String xml) {

		if (notEmpty(xml)) {
			XStream xs = new XStream();
			xs.alias("javancss", ParsedJavaNCSS.class);
			xs.alias("object", ParsedJavaNCSSObject.class);

			return (ParsedJavaNCSS) xs.fromXML(xml);
		} else {
			return new ParsedJavaNCSS();
		}
	}

	private boolean notEmpty(String xml) {
		int objectsBegin = xml.indexOf("<objects>");
		return !xml.substring(objectsBegin).contains("<averages>");
	}

}
