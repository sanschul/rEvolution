package edu.usp.ime.revolution.metrics;

import java.util.Hashtable;

public class MetricSet {

	private final String name;
	private final Hashtable<String, Double> metrics;

	public MetricSet(String name) {
		this.name = name;
		this.metrics = new Hashtable<String, Double>();
	}

	public String getName() {
		return name;
	}

	public void setMetric(String name, double value) {
		metrics.put(name, value);
	}

	public double getMetric(String name) {
		return metrics.get(name);
	}

}
