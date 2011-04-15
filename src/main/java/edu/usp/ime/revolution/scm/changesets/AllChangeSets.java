package edu.usp.ime.revolution.scm.changesets;

import java.util.Iterator;
import java.util.List;

import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetCollection;
import edu.usp.ime.revolution.scm.ChangeSetInfo;
import edu.usp.ime.revolution.scm.SCM;

public class AllChangeSets implements ChangeSetCollection {

	private final SCM scm;
	private List<ChangeSetInfo> changeSets;
	private Iterator<ChangeSetInfo> iterator;

	public AllChangeSets(SCM scm) {
		this.scm = scm;
		this.changeSets = scm.getChangeSetList();
		this.iterator = changeSets.iterator();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public ChangeSet next() {
		ChangeSetInfo changeSetInfo = iterator.next();
		return scm.getChangeSet(changeSetInfo);
	}

	public void remove() {
		throw new UnsupportedOperationException(); 
	}

	public Iterator<ChangeSet> iterator() {
		return this;
	}

}
