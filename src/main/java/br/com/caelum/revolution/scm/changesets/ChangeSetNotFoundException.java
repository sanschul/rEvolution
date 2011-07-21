package br.com.caelum.revolution.scm.changesets;

public class ChangeSetNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ChangeSetNotFoundException(Exception e) {
		super(e);
	}

}
