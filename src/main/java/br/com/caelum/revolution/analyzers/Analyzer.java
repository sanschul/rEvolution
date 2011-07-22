package br.com.caelum.revolution.analyzers;

import java.util.List;

import br.com.caelum.revolution.scm.changesets.ChangeSetCollection;


public interface Analyzer {
	void start(ChangeSetCollection collection);
	List<Error> getErrors();
}
