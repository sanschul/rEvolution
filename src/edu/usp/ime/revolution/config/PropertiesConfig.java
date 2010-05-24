package edu.usp.ime.revolution.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfig implements Config {

	private Properties props;

	public PropertiesConfig(InputStream stream) throws IOException {
		props = new Properties();
		props.load(stream);
	}

	public String get(String key) {
		return props.getProperty(key);
	}

}
