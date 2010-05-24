package edu.usp.ime.revolution.analyzers;

import edu.usp.ime.revolution.metrics.MetricStore;
import edu.usp.ime.revolution.scm.ChangeSetCollection;

public interface Analyzer {
	void start(ChangeSetCollection collection);
	MetricStore getMetricStore();
}
