package edu.usp.ime.revolution.persistence;

import edu.usp.ime.revolution.metrics.MetricSet;

public interface MetricPersistence {
	void save(MetricSet set);
}
