package edu.usp.ime.revolution.config;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class PropertiesConfigTest {

	@Test
	public void ShouldLoadConfig() throws IOException {
		PropertiesConfig config = new PropertiesConfig(basedOnConfig());
		
		assertEquals("git", config.get(Configs.SCM));
	}

	private InputStream basedOnConfig() {
		String config = "scm=git\n";
		
		return new ByteArrayInputStream(config.getBytes());
	}
}
