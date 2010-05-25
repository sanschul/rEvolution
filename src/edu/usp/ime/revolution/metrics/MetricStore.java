package edu.usp.ime.revolution.metrics;

import java.util.ArrayList;
import java.util.List;

import edu.usp.ime.revolution.scm.ChangeSet;

public class MetricStore {

	private final List<MetricSet> sets;

	public MetricStore() {
		this.sets = new ArrayList<MetricSet>();
	}

	public MetricSet setFor(ChangeSet changeSet) {
		MetricSet set = new MetricSet(changeSet.getInfo().getId(), changeSet.getInfo().getTime());
		sets.add(set);
		
		return set;
	}
	
	public List<MetricSet> getMetricSets() {
		return sets;
	}
}
