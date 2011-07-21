package br.com.caelum.revolution.analyzers;

import java.util.List;

import br.com.caelum.revolution.changesets.ChangeSetCollection;
import br.com.caelum.revolution.postaction.PostAction;


public interface Analyzer {
	void start(ChangeSetCollection collection);
	void addPostAction(PostAction observer);
	
	List<Error> getErrors();
}
