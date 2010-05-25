package edu.usp.ime.revolution.tools;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import edu.usp.ime.revolution.config.Config;

public class ToolsFactoryTest {

	@Test
	public void ShouldAddAllToolsInConfig() {
		Config config = mock(Config.class);
		when(config.contains("tools.1")).thenReturn(true);
		when(config.get("tools.1")).thenReturn("number-of-files");
		when(config.contains("tools.2")).thenReturn(true);
		when(config.get("tools.2")).thenReturn("number-of-files");
		when(config.contains("tools.3")).thenReturn(false);
		
		List<MetricTool> tools = new ToolsFactory().basedOn(config);
		
		assertEquals(2, tools.size());
	}
	
	@Test
	public void ShouldLoadConfig() {
		Config config = mock(Config.class);
		when(config.contains("tools.1")).thenReturn(true);
		when(config.get("tools.1")).thenReturn("number-of-files");
		when(config.get("tools.1.extension")).thenReturn("java");
		when(config.contains("tools.2")).thenReturn(false);
		
		new ToolsFactory().basedOn(config);
		
		verify(config).get("tools.1.extension");
	}
}
