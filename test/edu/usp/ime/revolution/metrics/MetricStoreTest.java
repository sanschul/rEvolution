package edu.usp.ime.revolution.metrics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MetricStoreTest {
	
	private MetricStore store;
	
	@Before
	public void SetUp() {
		store = new MetricStore();
	}
	
	@Test
	public void ShouldBuildAMetricSet() {
		MetricSet set = store.setFor("cs 1");		
		set.setMetric("LCOM", 1);

		set = store.setFor("cs 1");
		
		assertEquals(1, set.getMetric("LCOM"), 0.01);
	}

}
