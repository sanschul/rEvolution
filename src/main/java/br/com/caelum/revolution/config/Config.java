package br.com.caelum.revolution.config;

public interface Config {
	String get(String key);
	boolean contains(String key);
}
