package edu.usp.ime.revolution.metrics;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.usp.ime.revolution.exceptions.MetricAlreadyInSetException;;

public class MetricSetTest {

	@Test
	public void ShouldHaveAName() {
		MetricSet set = new MetricSet("set name");
		
		assertEquals("set name", set.getName());
	}
	
	@Test
	public void ShouldStoreAMetric() {
		MetricSet set = new MetricSet("set name");
		
		set.setMetric("metric name", 1.34);
		
		assertEquals(1.34, set.getMetric("metric name"), 0.01);
	}
	
	@Test(expected=MetricAlreadyInSetException.class)
	public void ShouldNotReplaceAnExistingMetric() {
		MetricSet set = new MetricSet("set name");
		
		set.setMetric("lcom", 1.34);
		set.setMetric("lcom", 5.1);	
	}
}
