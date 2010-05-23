package edu.usp.ime.revolution.metrics;

import static org.junit.Assert.*;
import edu.usp.ime.revolution.exceptions.MetricSetDoesNotExistException;
import org.junit.Test;

public class MetricStoreTest {
	
	@Test
	public void ShouldHaveAChangeSetId() {
		MetricStore store = new MetricStore("id");
		
		assertEquals("id", store.getChangeSetId());
	}
	
	@Test
	public void ShouldBuildAMetricSet() {
		MetricStore store = new MetricStore("id");
		MetricSet set = store.buildSet("some plugin metrics");
		
		assertEquals(set, store.getMetricSet("some plugin metrics"));
	}
	
	@Test(expected=MetricSetDoesNotExistException.class)
	public void ShouldWarnWhenMetricDoesNotExist() {
		MetricStore store = new MetricStore("id");
		store.getMetricSet("anyone");
	}

}
