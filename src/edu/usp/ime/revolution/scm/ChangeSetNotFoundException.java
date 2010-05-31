package edu.usp.ime.revolution.scm;

public class ChangeSetNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ChangeSetNotFoundException(Exception e) {
		super(e);
	}

}
