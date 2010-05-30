package edu.usp.ime.revolution.builds;

public class BuildException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BuildException(Exception e) {
		super(e);
	}
}
