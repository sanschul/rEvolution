package edu.usp.ime.revolution.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MetricTest {
	
	@Test
	public void shouldContainNameValueTargetAndTool() {
		Metric m = new Metric("name", 1.23, "target", "level", "tool");
		
		assertEquals("name", m.getName());
		assertEquals(1.23, m.getValue(), 0.01);
		assertEquals("target", m.getTarget());
		assertEquals("level", m.getLevel());
		assertEquals("tool", m.getTool());
	}

}
