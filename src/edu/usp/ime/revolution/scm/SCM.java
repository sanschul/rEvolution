package edu.usp.ime.revolution.scm;

import java.util.List;

import edu.usp.ime.revolution.changesets.ChangeSet;
import edu.usp.ime.revolution.exceptions.SCMException;

public interface SCM {
	List<String> getChangeSetList() throws SCMException;
	ChangeSet getChangeSet(String name);
}
