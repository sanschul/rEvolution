package edu.usp.ime.revolution.tools.files;

import org.junit.Test;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.tools.MetricTool;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NumberOfFilesFactoryTest {

	@Test
	public void shouldBuildNumberOfTilesTool() {
		Config config = mock(Config.class);
		when(config.get("tools.1.extension")).thenReturn("java");
		
		MetricTool tool = new NumberOfFilesFactory().build(config, "tools.1");
		
		verify(config).get("tools.1.extension");
		assertEquals(tool.getClass(), NumberOfFiles.class);
	}
}
