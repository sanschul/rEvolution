package edu.usp.ime.revolution.persistence;

public class PersistenceException extends RuntimeException {

	public PersistenceException(Exception e) {
		super(e);
	}

	private static final long serialVersionUID = 1L;

}
