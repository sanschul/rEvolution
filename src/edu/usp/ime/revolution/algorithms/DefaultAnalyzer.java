package edu.usp.ime.revolution.algorithms;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.changesets.ChangeSet;
import edu.usp.ime.revolution.changesets.ChangeSetCollection;

public class DefaultAnalyzer {

	private final Build build;

	public DefaultAnalyzer(Build build) {
		this.build = build;
	}

	public void start(ChangeSetCollection collection) {
		for(ChangeSet set : collection) {
			build.build(set);
		}
		
	}

}
