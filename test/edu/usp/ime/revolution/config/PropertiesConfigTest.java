package edu.usp.ime.revolution.config;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class PropertiesConfigTest {

	@Test
	public void ShouldFindConfig() throws IOException {
		PropertiesConfig config = new PropertiesConfig(basedOnConfig());
		
		assertEquals("git", config.get(Configs.SCM));
	}
	
	@Test(expected=ConfigNotFoundException.class)
	public void ShouldWarnWhenConfigWasNotFound() throws IOException {
		PropertiesConfig config = new PropertiesConfig(basedOnConfig());
		
		config.get("inexistent-config");
	}

	private InputStream basedOnConfig() {
		String config = "scm=git\n";
		
		return new ByteArrayInputStream(config.getBytes());
	}
}
