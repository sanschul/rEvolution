package edu.usp.ime.revolution.persistence;

import edu.usp.ime.revolution.config.Config;

public class PersistenceFactory {

	public MetricPersistence basedOn(Config config) {
		return new SqlitePersistence(config.get("persistence.file"), config.get("project.name"));
	}
}
