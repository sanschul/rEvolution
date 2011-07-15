package edu.usp.ime.revolution.scm;

import java.util.List;

import edu.usp.ime.revolution.domain.Commit;

public interface SCM {
	List<ChangeSet> getChangeSets();

	String goTo(ChangeSet cs);

	Commit detail(String id);
}
