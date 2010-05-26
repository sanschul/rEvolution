package edu.usp.ime.revolution.analyzers.observers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.usp.ime.revolution.metrics.Metric;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.scm.ChangeSet;

public class ConsoleLog implements AnalyzerObserver {

	public void notify(ChangeSet cs, MetricSet set) {
		System.out.println("CS: " + cs.getInfo().getId() + " " + formatDate(cs.getInfo().getTime()));
		System.out.println("Number of metrics: " + set.getMetrics().size());
		
		for(Metric metric: set.getMetrics()) {
			System.out.println("-> " + metric.getName() + "=" + metric.getValue());	
		}
		
		System.out.println("---------------------------------------");
	}

	private String formatDate(Calendar date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date.getTime());
	}

}
