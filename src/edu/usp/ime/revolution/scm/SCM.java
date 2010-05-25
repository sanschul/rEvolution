package edu.usp.ime.revolution.scm;

import java.util.List;


public interface SCM {
	List<ChangeSetInfo> getChangeSetList();
	ChangeSet getChangeSet(String name);
}
