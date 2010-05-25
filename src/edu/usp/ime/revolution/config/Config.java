package edu.usp.ime.revolution.config;

public interface Config {
	String get(String key);
	boolean contains(String key);
}
