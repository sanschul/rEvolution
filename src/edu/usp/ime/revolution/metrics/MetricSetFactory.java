package edu.usp.ime.revolution.metrics;

import edu.usp.ime.revolution.scm.ChangeSet;

public class MetricSetFactory {

	public MetricSet setFor(ChangeSet changeSet) {
		MetricSet set = new MetricSet(changeSet.getInfo().getId(), changeSet.getInfo().getTime());
		return set;
	}
}
