package edu.usp.ime.revolution.metrics;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.usp.ime.revolution.exceptions.MetricSetDoesNotExistException;

import org.junit.Before;
import org.junit.Test;

public class MetricStoreTest {
	
	private MetricStore store;
	private Calendar changeSetTime;
	private Calendar metricTime;
	
	@Before
	public void SetUp() {
		changeSetTime = GregorianCalendar.getInstance();
		metricTime = GregorianCalendar.getInstance();
		
		store = new MetricStore("id", changeSetTime, metricTime);
	}
	
	@Test
	public void ShouldHaveInformationAboutChangeSet() {
		assertEquals("id", store.getChangeSetId());
		assertEquals(changeSetTime, store.getChangeSetTime());
		assertEquals(metricTime, store.getMetricTime());
	}
	
	@Test
	public void ShouldBuildAMetricSet() {
		MetricSet set = store.buildSet("some plugin metrics");
		
		assertEquals(set, store.getMetricSet("some plugin metrics"));
	}
	
	@Test(expected=MetricSetDoesNotExistException.class)
	public void ShouldWarnWhenMetricDoesNotExist() {
		store.getMetricSet("anyone");
	}

}
