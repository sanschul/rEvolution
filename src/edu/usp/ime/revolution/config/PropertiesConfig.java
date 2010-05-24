package edu.usp.ime.revolution.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import edu.usp.ime.revolution.exceptions.ConfigNotFoundException;

public class PropertiesConfig implements Config {

	private Properties props;

	public PropertiesConfig(InputStream stream) throws IOException {
		props = new Properties();
		props.load(stream);
	}

	public String get(String key) {
		String value = props.getProperty(key);
		if(value == null) throw new ConfigNotFoundException("config not found: " + key);
		
		return value;
	}

}
