package edu.usp.ime.revolution.scm;

import java.util.List;

public interface SCM {
	List<String> getChangeSetList();
	ChangeSet getChangeSet(String name);
}
