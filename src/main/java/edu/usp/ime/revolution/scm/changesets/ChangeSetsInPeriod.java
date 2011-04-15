package edu.usp.ime.revolution.scm.changesets;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetCollection;
import edu.usp.ime.revolution.scm.ChangeSetInfo;
import edu.usp.ime.revolution.scm.SCM;

public class ChangeSetsInPeriod implements ChangeSetCollection {

	private final Calendar startPeriod;
	private final Calendar endPeriod;
	private final List<ChangeSetInfo> changeSets;
	private final Iterator<ChangeSetInfo> iterator;
	private final SCM scm;
	private ChangeSetInfo currentChangeSetInfo;

	public ChangeSetsInPeriod(SCM scm, Calendar startPeriod, Calendar endPeriod) {
		this.scm = scm;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
		this.changeSets = scm.getChangeSetList();
		this.iterator = changeSets.iterator();
	}

	public boolean hasNext() {
		while (iterator.hasNext()) {
			currentChangeSetInfo = iterator.next();
			if(isInPeriod()) {
				return true;
			}
		}
		
		return false;
	}

	private boolean isInPeriod() {
		return currentChangeSetInfo.getTime().after(startPeriod) && currentChangeSetInfo.getTime().before(endPeriod);
	}

	public ChangeSet next() {
		return scm.getChangeSet(currentChangeSetInfo);
	}

	public void remove() {
		throw new UnsupportedOperationException(); 
	}

	public Iterator<ChangeSet> iterator() {
		return this;
	}

	public Calendar getStartPeriod() {
		return startPeriod;
	}

	public Calendar getEndPeriod() {
		return endPeriod;
	}

}
