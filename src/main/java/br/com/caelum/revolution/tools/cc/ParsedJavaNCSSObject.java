package br.com.caelum.revolution.tools.cc;

public class ParsedJavaNCSSObject {

	private String name;
	private int ncss;
	private int functions;
	private int classes;
	private int javadocs;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNcss() {
		return ncss;
	}
	public void setNcss(int ncss) {
		this.ncss = ncss;
	}
	public int getFunctions() {
		return functions;
	}
	public void setFunctions(int functions) {
		this.functions = functions;
	}
	public int getClasses() {
		return classes;
	}
	public void setClasses(int classes) {
		this.classes = classes;
	}
	public int getJavadocs() {
		return javadocs;
	}
	public void setJavadocs(int javadocs) {
		this.javadocs = javadocs;
	}
	
	
}
