package br.com.caelum.revolution.analyzers;

import br.com.caelum.revolution.changesets.ChangeSetCollection;


public class DefaultAnalyzerRunner implements AnalyzerRunner {

	private final Analyzer analyzer;
	private final ChangeSetCollection collection;

	public DefaultAnalyzerRunner(Analyzer analyzer, ChangeSetCollection collection) {
		this.analyzer = analyzer;
		this.collection = collection;
	}

	public void start() {
		analyzer.start(collection);
	}
	
}
