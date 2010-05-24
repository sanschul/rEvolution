package edu.usp.ime.revolution.metrics;

import java.util.ArrayList;
import java.util.List;

import edu.usp.ime.revolution.exceptions.MetricSetDoesNotExistException;
import edu.usp.ime.revolution.scm.ChangeSet;

public class MetricStore {

	private final List<MetricSet> sets;

	public MetricStore() {
		this.sets = new ArrayList<MetricSet>();
	}

	public MetricSet setFor(ChangeSet changeSet) {
		MetricSet set = new MetricSet(changeSet.getId(), changeSet.getTime());
		sets.add(set);
		
		return set;
	}
	
	public MetricSet find(String changeSetId) {
		for(MetricSet set : sets) {
			if(set.getName().equals(changeSetId)) return set;
		}
		
		throw new MetricSetDoesNotExistException();
	}
	
	public List<MetricSet> getMetricSets() {
		return sets;
	}
}
