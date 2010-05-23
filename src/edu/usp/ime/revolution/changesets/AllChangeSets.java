package edu.usp.ime.revolution.changesets;

import java.util.Iterator;
import java.util.List;

import edu.usp.ime.revolution.exceptions.SCMException;
import edu.usp.ime.revolution.scm.SCM;

public class AllChangeSets implements ChangeSetCollection {

	private final SCM scm;
	private List<String> changeSets;
	private Iterator<String> iterator;

	public AllChangeSets(SCM scm) throws SCMException {
		this.scm = scm;
		this.changeSets = scm.getChangeSetList();
		this.iterator = changeSets.iterator();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public ChangeSet next() {
		String changeSetName = iterator.next();
		return scm.getChangeSet(changeSetName);
	}

	public void remove() {
		throw new UnsupportedOperationException(); 
	}

	public Iterator<ChangeSet> iterator() {
		return this;
	}

}
