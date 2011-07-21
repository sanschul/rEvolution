package edu.usp.ime.revolution.scm;

import java.util.List;

import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.scm.changesets.ChangeSet;

public interface SCM {
	List<ChangeSet> getChangeSets();
	String goTo(ChangeSet cs);
	Commit detail(String id);
	String sourceOf(String hash, String fileName);
	
	String getPath();
}
