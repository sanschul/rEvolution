package edu.usp.ime.revolution.metrics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static edu.usp.ime.revolution.scm.ChangeSetBuilder.*;

public class MetricStoreTest {
	
	private MetricStore store;
	
	@Before
	public void SetUp() {
		store = new MetricStore();
	}
	
	@Test
	public void ShouldBuildAMetricSet() {
		MetricSet set = store.setFor(aChangeSet("id"));		
		
		assertEquals("id", set.getName());
	}
	
	@Test
	public void ShouldFindASet() {
		MetricSet set = store.setFor(aChangeSet("id"));
		MetricSet setFound = store.find("id");
		
		assertEquals(setFound, set);
	}
	
	@Test(expected=MetricSetDoesNotExistException.class)
	public void ShouldWarnIfSetWasNotFound() {
		store.find("inexistent-set");
	}

}
