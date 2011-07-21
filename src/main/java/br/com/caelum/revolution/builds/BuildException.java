package br.com.caelum.revolution.builds;

public class BuildException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BuildException(Exception e) {
		super(e);
	}
}
