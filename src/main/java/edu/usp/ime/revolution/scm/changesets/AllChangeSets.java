package edu.usp.ime.revolution.scm.changesets;

import java.util.Iterator;
import java.util.List;

import edu.usp.ime.revolution.scm.SCM;

public class AllChangeSets implements ChangeSetCollection {

	private List<ChangeSet> changeSets;
	private Iterator<ChangeSet> iterator;

	public AllChangeSets(SCM scm) {
		this.changeSets = scm.getChangeSets();
		this.iterator = changeSets.iterator();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public ChangeSet next() {
		return iterator.next();
	}

	public void remove() {
		throw new UnsupportedOperationException(); 
	}

	public Iterator<ChangeSet> iterator() {
		return this;
	}

}
