package br.com.caelum.revolution.scm.changesets;

import java.util.Iterator;

import br.com.caelum.revolution.scm.changesets.ChangeSet;
import br.com.caelum.revolution.scm.changesets.ChangeSetCollection;


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
