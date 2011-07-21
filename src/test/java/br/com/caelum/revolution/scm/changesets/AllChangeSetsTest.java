package br.com.caelum.revolution.scm.changesets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.scm.changesets.AllChangeSets;
import br.com.caelum.revolution.scm.changesets.ChangeSet;


public class AllChangeSetsTest {

	@Test
	public void shouldIterateIntoAllChangeSets() {
		SCM scm = mock(SCM.class);
		
		List<ChangeSet> changeSets = someChangeSets();
		when(scm.getChangeSets()).thenReturn(changeSets);
		
		AllChangeSets collection = new AllChangeSets(scm);
		
		Iterator<ChangeSet> sets = collection.iterator();
		assertTrue(sets.hasNext());
		ChangeSet cs1 = sets.next();
		ChangeSet cs2 = sets.next();
		
		assertEquals("abcd", cs1.getId());
		assertEquals("efgh", cs2.getId());
		assertFalse(sets.hasNext());
		
		verify(scm).getChangeSets();
	}

	private List<ChangeSet> someChangeSets() {
		List<ChangeSet> changeSets = new ArrayList<ChangeSet>();
		changeSets.add(new ChangeSet("abcd", Calendar.getInstance()));
		changeSets.add(new ChangeSet("efgh", Calendar.getInstance()));
		return changeSets;
	}
}
