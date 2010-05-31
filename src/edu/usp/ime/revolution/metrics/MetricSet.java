package edu.usp.ime.revolution.metrics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MetricSet {

	private final String name;
	private final List<Metric> metrics;
	private final Calendar date;

	MetricSet(String name, Calendar date) {
		this.name = name;
		this.date = date;
		this.metrics = new ArrayList<Metric>();
	}

	public String getName() {
		return name;
	}
	
	public Calendar getDate() {
		return date;
	}

	public void setMetric(String name, double value, String target, String level, String tool) {
		if(find(name, target, tool) != null) throw new MetricAlreadyInSetException();
		
		Metric newMetric = new Metric(name, value, target, level, tool);
		metrics.add(newMetric);
	}

	public Metric getMetric(String name, String target, String tool) {
		Metric m = find(name, target, tool);
		return m;
	}

	private Metric find(String name, String target, String tool) {
		for(Metric metric : metrics) {
			if(metric.getName().equals(name) && 
					metric.getTarget().equals(target) && 
					metric.getTool().equals(tool)) return metric;
		}
		
		return null;
	}

	public List<Metric> getMetrics() {
		return Collections.unmodifiableList(metrics);
	}

}
