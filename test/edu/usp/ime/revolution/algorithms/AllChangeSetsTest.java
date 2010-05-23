package edu.usp.ime.revolution.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import edu.usp.ime.revolution.changeset.AllChangeSets;
import edu.usp.ime.revolution.changeset.ChangeSet;
import edu.usp.ime.revolution.exceptions.SCMException;
import edu.usp.ime.revolution.scm.SCM;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AllChangeSetsTest {

	@Test
	public void ShouldIterateIntoAllChangeSets() throws SCMException {
		SCM scm = mock(SCM.class);
		when(scm.getChangeSetList()).thenReturn(someChangeSets());
		when(scm.getChangeSet("abcd")).thenReturn(aChangeSet("cs 1"));
		when(scm.getChangeSet("efgh")).thenReturn(aChangeSet("cs 2"));
		
		AllChangeSets collection = new AllChangeSets(scm);
		
		Iterator<ChangeSet> sets = collection.iterator();
		ChangeSet cs1 = sets.next();
		ChangeSet cs2 = sets.next();
		
		assertEquals("cs 1", cs1.getName());
		assertEquals("cs 2", cs2.getName());
		
		verify(scm).getChangeSetList();
		verify(scm).getChangeSet("abcd");
		verify(scm).getChangeSet("efgh");
	}

	private ChangeSet aChangeSet(final String name) {
		return new ChangeSet() {
			public String getName() {
				return name;
			}
		};
	}

	private List<String> someChangeSets() {
		List<String> changeSets = new ArrayList<String>();
		changeSets.add("abcd");
		changeSets.add("efgh");
		return changeSets;
	}
}
