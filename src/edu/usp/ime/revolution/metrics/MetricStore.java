package edu.usp.ime.revolution.metrics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.usp.ime.revolution.exceptions.MetricSetDoesNotExistException;

public class MetricStore {

	private final String changeSetId;
	private final List<MetricSet> sets;
	private final Calendar metricTime;
	private final Calendar changeSetTime;

	public MetricStore(String changeSetId, Calendar changeSetTime, Calendar metricTime) {
		this.changeSetId = changeSetId;
		this.changeSetTime = changeSetTime;
		this.metricTime = metricTime;
		this.sets = new ArrayList<MetricSet>();
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

	public String getChangeSetId() {
		return changeSetId;
	}

	public Calendar getChangeSetTime() {
		return changeSetTime;
	}

	public Calendar getMetricTime() {
		return metricTime;
	}

	
}
