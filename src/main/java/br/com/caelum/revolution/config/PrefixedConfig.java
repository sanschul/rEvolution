package br.com.caelum.revolution.config;

public class PrefixedConfig implements Config{

	private final Config cfg;
	private final String prefix;

	public PrefixedConfig(Config cfg, String prefix) {
		this.cfg = cfg;
		this.prefix = prefix + ".";
	}

	public String get(String key) {
		return cfg.get(prefix + key);
	}

	public boolean contains(String key) {
		return cfg.contains(prefix + key);
	}
}
