package br.com.caelum.revolution.tools.files;

import org.junit.Test;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.tools.Tool;
import br.com.caelum.revolution.tools.files.NumberOfFilesTool;
import br.com.caelum.revolution.tools.files.NumberOfFilesFactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NumberOfFilesFactoryTest {

	@Test
	public void shouldBuildNumberOfTilesTool() {
		Config config = mock(Config.class);
		when(config.get("extension")).thenReturn("java");
		
		Tool tool = new NumberOfFilesFactory().build(config);
		
		verify(config).get("extension");
		assertEquals(tool.getClass(), NumberOfFilesTool.class);
	}
}
