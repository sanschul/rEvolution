package edu.usp.ime.revolution.metrics;

import java.util.ArrayList;
import java.util.List;

public class MetricStore {

	private final List<MetricSet> sets;

	public MetricStore() {
		this.sets = new ArrayList<MetricSet>();
	}

	public MetricSet setFor(String changeSetId) {
		MetricSet set = find(changeSetId);
		if(set == null) {
			set = new MetricSet(changeSetId);
			sets.add(set);
		}
		
		return set;
	}
	
	private MetricSet find(String changeSetId) {
		for(MetricSet set : sets) {
			if(set.getName().equals(changeSetId)) return set;
		}
		
		return null;
	}
}
