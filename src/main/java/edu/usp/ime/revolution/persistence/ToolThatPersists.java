package edu.usp.ime.revolution.persistence;

import java.util.List;

import org.hibernate.Session;

public interface ToolThatPersists {

	List<Class<?>> classesToPersist();
	void setSession(Session session);
}
