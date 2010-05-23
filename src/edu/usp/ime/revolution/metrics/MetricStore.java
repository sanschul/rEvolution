package edu.usp.ime.revolution.metrics;

import java.util.ArrayList;
import java.util.List;

import edu.usp.ime.revolution.exceptions.MetricSetDoesNotExistException;

public class MetricStore {

	private final String changeSetId;
	private final List<MetricSet> sets;

	public MetricStore(String changeSetId) {
		this.changeSetId = changeSetId;
		this.sets = new ArrayList<MetricSet>();
	}

	public String getChangeSetId() {
		return changeSetId;
	}

	public MetricSet buildSet(String name) {
		MetricSet newSet = new MetricSet(name);
		sets.add(newSet);
		
		return newSet;
	}

	public MetricSet getMetricSet(String name) {
		for(MetricSet set : sets) {
			if(set.getName().equals(name)) return set;
		}
		
		throw new MetricSetDoesNotExistException();
	}

}
