package edu.usp.ime.revolution.analyzers;

import java.util.List;

import edu.usp.ime.revolution.analyzers.observers.AnalyzerObserver;
import edu.usp.ime.revolution.scm.changesets.ChangeSetCollection;

public interface Analyzer {
	void start(ChangeSetCollection collection);
	void addObserver(AnalyzerObserver observer);
	
	List<Error> getErrors();
}
