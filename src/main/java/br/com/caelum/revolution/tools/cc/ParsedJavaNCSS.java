package br.com.caelum.revolution.tools.cc;

import java.util.List;

public class ParsedJavaNCSS {

	private String date;
	private String time;
	private List<ParsedJavaNCSSObject> objects;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<ParsedJavaNCSSObject> getObjects() {
		return objects;
	}
	public void setObjects(List<ParsedJavaNCSSObject> objects) {
		this.objects = objects;
	}
	
	
}
