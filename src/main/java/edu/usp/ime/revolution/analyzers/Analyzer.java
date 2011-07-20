package edu.usp.ime.revolution.analyzers;

import java.util.List;

import edu.usp.ime.revolution.postaction.PostAction;
import edu.usp.ime.revolution.scm.changesets.ChangeSetCollection;

public interface Analyzer {
	void start(ChangeSetCollection collection);
	void addPostAction(PostAction observer);
	
	List<Error> getErrors();
}
