package edu.usp.ime.revolution.scm;

import java.util.List;


public interface SCM {
	List<String> getChangeSetList() throws SCMException;
	ChangeSet getChangeSet(String name);
}
