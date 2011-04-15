package edu.usp.ime.revolution.metrics;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import edu.usp.ime.revolution.scm.ChangeSetInfo;
import static edu.usp.ime.revolution.scm.ChangeSetBuilder.*;

public class MetricSetFactoryTest {
	
	private MetricSetFactory store;
	
	@Before
	public void setUp() {
		store = new MetricSetFactory();
	}
	
	@Test
	public void shouldBuildAMetricSet() {
		MetricSet set = store.setFor(aChangeSet(new ChangeSetInfo("123", Calendar.getInstance())));		
		
		assertEquals("123", set.getName());
	}

}
