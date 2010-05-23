package edu.usp.ime.revolution.algorithms;

import java.util.List;

import edu.usp.ime.revolution.scm.SCM;

public class AllChangeSetsAnalyzer {

	public void start(SCM scm) {
		List<String> availableChangeSets = scm.getChangeSetList();
		
		for(String changeSetName : availableChangeSets) {
			scm.getChangeSet(changeSetName);
		}
		
	}

}
