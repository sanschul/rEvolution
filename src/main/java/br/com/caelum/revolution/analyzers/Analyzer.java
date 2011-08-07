package br.com.caelum.revolution.analyzers;

import br.com.caelum.revolution.changesets.ChangeSetCollection;


public interface Analyzer {
	void start(ChangeSetCollection collection);
}
