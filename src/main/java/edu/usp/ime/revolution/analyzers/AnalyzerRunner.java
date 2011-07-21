package edu.usp.ime.revolution.analyzers;

import java.util.List;

import edu.usp.ime.revolution.scm.changesets.ChangeSetCollection;

public class AnalyzerRunner {

	private final Analyzer analyzer;
	private final ChangeSetCollection collection;

	public AnalyzerRunner(Analyzer analyzer, ChangeSetCollection collection) {
		this.analyzer = analyzer;
		this.collection = collection;
	}

	public void start() {
		analyzer.start(collection);
	}
	
	public List<Error> getErrors() {
		return analyzer.getErrors();
	}

}
