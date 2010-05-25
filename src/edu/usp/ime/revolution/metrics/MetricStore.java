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
	
	public MetricSet find(String name) {
		for(MetricSet set : sets) {
			if(set.getName().equals(name)) return set;
		}
		
		throw new MetricSetDoesNotExistException();
	}
	
	public List<MetricSet> getMetricSets() {
		return sets;
	}
}
