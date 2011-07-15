package edu.usp.ime.revolution.scm;

import java.util.List;

import edu.usp.ime.revolution.domain.Commit;


public interface SCM {
	List<ChangeSetInfo> getChangeSetList();
	ChangeSet getChangeSet(ChangeSetInfo cs);
	Commit detail(String id);
}
