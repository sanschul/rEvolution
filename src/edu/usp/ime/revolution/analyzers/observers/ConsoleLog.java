package edu.usp.ime.revolution.analyzers.observers;

import java.util.Map;

import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.scm.ChangeSet;

public class ConsoleLog implements AnalyzerObserver {

	public void notify(ChangeSet cs, MetricSet set) {
		System.out.println("CS: " + cs.getId() + " " + cs.getTime());
		System.out.println("Number of metrics: " + set.getMetrics().size());
		
		for(Map.Entry<String, Double> metric: set.getMetrics().entrySet()) {
			System.out.println("-> " + metric.getKey() + "=" + metric.getValue());	
		}
		
		System.out.println("---------------------------------------");
	}

}
