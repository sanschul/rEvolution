package edu.usp.ime.revolution.scm;

import java.util.ArrayList;
import java.util.Calendar;
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
		
		List<ChangeSetInfo> changeSets = someChangeSets();
		when(scm.getChangeSetList()).thenReturn(changeSets);
		when(scm.getChangeSet(changeSets.get(0))).thenReturn(aChangeSet(changeSets.get(0)));
		when(scm.getChangeSet(changeSets.get(1))).thenReturn(aChangeSet(changeSets.get(1)));
		
		AllChangeSets collection = new AllChangeSets(scm);
		
		Iterator<ChangeSet> sets = collection.iterator();
		ChangeSet cs1 = sets.next();
		ChangeSet cs2 = sets.next();
		
		assertEquals("abcd", cs1.getInfo().getId());
		assertEquals("efgh", cs2.getInfo().getId());
		assertFalse(sets.hasNext());
		
		verify(scm).getChangeSetList();
		verify(scm).getChangeSet(changeSets.get(0));
		verify(scm).getChangeSet(changeSets.get(1));
	}

	private List<ChangeSetInfo> someChangeSets() {
		List<ChangeSetInfo> changeSets = new ArrayList<ChangeSetInfo>();
		changeSets.add(new ChangeSetInfo("abcd", Calendar.getInstance()));
		changeSets.add(new ChangeSetInfo("efgh", Calendar.getInstance()));
		return changeSets;
	}
}
