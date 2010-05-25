package edu.usp.ime.revolution.metrics;

import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;


public class MetricSet {

	private final String name;
	private final Map<String, Double> metrics;
	private final Calendar date;

	MetricSet(String name, Calendar date) {
		this.name = name;
		this.date = date;
		this.metrics = new Hashtable<String, Double>();
	}

	public String getName() {
		return name;
	}
	
	public Calendar getDate() {
		return date;
	}

	public void setMetric(String name, double value) {
		if(metrics.containsKey(name)) throw new MetricAlreadyInSetException();
		
		metrics.put(name, value);
	}

	public double getMetric(String name) {
		return metrics.get(name);
	}

	public Map<String, Double> getMetrics() {
		return Collections.unmodifiableMap(metrics);
	}

}
