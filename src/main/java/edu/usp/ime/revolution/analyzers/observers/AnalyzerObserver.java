package edu.usp.ime.revolution.analyzers.observers;

import edu.usp.ime.revolution.scm.ChangeSet;

public interface AnalyzerObserver {
	void notify(ChangeSet cs);
}
