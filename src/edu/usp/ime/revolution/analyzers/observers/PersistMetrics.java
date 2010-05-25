package edu.usp.ime.revolution.analyzers.observers;

import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.persistence.MetricPersistence;
import edu.usp.ime.revolution.scm.ChangeSet;

public class PersistMetrics implements AnalyzerObserver{

	private final MetricPersistence persistence;

	public PersistMetrics(MetricPersistence persistence) {
		this.persistence = persistence;
	}

	public void notify(ChangeSet cs, MetricSet set) {
		persistence.save(set);
	}

}
