package edu.usp.ime.revolution.scm.changesets;

import java.util.Iterator;

import edu.usp.ime.revolution.scm.changesets.ChangeSet;
import edu.usp.ime.revolution.scm.changesets.ChangeSetCollection;

public class ChangeSetBuilder {

	public static ChangeSetCollection aCollectionWith(final ChangeSet changeSet) {
		return new ChangeSetCollection() {
			private boolean next = true;
			
			public Iterator<ChangeSet> iterator() {
				return this;
			}
			
			public void remove() {
				
			}
			
			public ChangeSet next() {
				return changeSet;
			}
			
			public boolean hasNext() {
				next = !next;
				return !next;
			}
		};
	}
}
