package edu.usp.ime.revolution.scm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import edu.usp.ime.revolution.scm.AllChangeSets;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.SCM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static edu.usp.ime.revolution.scm.ChangeSetBuilder.*;

public class AllChangeSetsTest {

	@Test
	public void ShouldIterateIntoAllChangeSets() {
		SCM scm = mock(SCM.class);
		when(scm.getChangeSetList()).thenReturn(someChangeSets());
		when(scm.getChangeSet("abcd")).thenReturn(aChangeSet("cs 1"));
		when(scm.getChangeSet("efgh")).thenReturn(aChangeSet("cs 2"));
		
		AllChangeSets collection = new AllChangeSets(scm);
		
		Iterator<ChangeSet> sets = collection.iterator();
		ChangeSet cs1 = sets.next();
		ChangeSet cs2 = sets.next();
		
		assertEquals("cs 1", cs1.getId());
		assertEquals("cs 2", cs2.getId());
		assertFalse(sets.hasNext());
		
		verify(scm).getChangeSetList();
		verify(scm).getChangeSet("abcd");
		verify(scm).getChangeSet("efgh");
	}

	private List<String> someChangeSets() {
		List<String> changeSets = new ArrayList<String>();
		changeSets.add("abcd");
		changeSets.add("efgh");
		return changeSets;
	}
}
