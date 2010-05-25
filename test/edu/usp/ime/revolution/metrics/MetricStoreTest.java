package edu.usp.ime.revolution.metrics;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import edu.usp.ime.revolution.scm.ChangeSetInfo;
import static edu.usp.ime.revolution.scm.ChangeSetBuilder.*;

public class MetricStoreTest {
	
	private MetricStore store;
	
	@Before
	public void SetUp() {
		store = new MetricStore();
	}
	
	@Test
	public void ShouldBuildAMetricSet() {
		MetricSet set = store.setFor(aChangeSet(new ChangeSetInfo("123", Calendar.getInstance())));		
		
		assertEquals("123", set.getName());
	}

}
