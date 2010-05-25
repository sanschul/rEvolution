package edu.usp.ime.revolution.scm;

import java.util.Iterator;
import java.util.List;

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
		ChangeSetInfo changeSetId = iterator.next();
		return scm.getChangeSet(changeSetId.getId());
	}

	public void remove() {
		throw new UnsupportedOperationException(); 
	}

	public Iterator<ChangeSet> iterator() {
		return this;
	}

}
