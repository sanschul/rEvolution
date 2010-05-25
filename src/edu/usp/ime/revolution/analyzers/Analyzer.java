package edu.usp.ime.revolution.analyzers;

import edu.usp.ime.revolution.analyzers.observers.AnalyzerObserver;
import edu.usp.ime.revolution.scm.ChangeSetCollection;

public interface Analyzer {
	void start(ChangeSetCollection collection);
	void addObserver(AnalyzerObserver observer);
}
