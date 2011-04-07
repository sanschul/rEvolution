package edu.usp.ime.revolution.scm.changesets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static edu.usp.ime.revolution.scm.ChangeSetBuilder.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetInfo;
import edu.usp.ime.revolution.scm.SCM;

public class ChangeSetsInPeriodTest {
	private Calendar someNewDate;
	private Calendar someOldDate;
	private Calendar startPeriod;
	private Calendar endPeriod;
	
	@Before
	public void setUp(){
		someNewDate = Calendar.getInstance();
		someNewDate.set(2010, 05, 20);
		
		someOldDate = Calendar.getInstance();
		someOldDate.set(2009, 05, 20);
		
		startPeriod = Calendar.getInstance();
		endPeriod = Calendar.getInstance();
	}

	@Test
	public void shouldReturnOnlyChangeSetsInPeriod() {
		SCM scm = mock(SCM.class);
		
		List<ChangeSetInfo> changeSets = someChangeSets();
		when(scm.getChangeSetList()).thenReturn(changeSets);
		when(scm.getChangeSet(changeSets.get(0))).thenReturn(aChangeSet(changeSets.get(0)));
		when(scm.getChangeSet(changeSets.get(1))).thenReturn(aChangeSet(changeSets.get(1)));
		
		startPeriod.set(2010, 05, 01);
		endPeriod.set(2010, 06, 01);
		
		ChangeSetsInPeriod collection = new ChangeSetsInPeriod(scm, startPeriod, endPeriod);
		
		Iterator<ChangeSet> sets = collection.iterator();
		assertTrue(sets.hasNext());
		
		ChangeSet cs1 = sets.next();
		assertEquals("abcd", cs1.getInfo().getId());
		
		assertFalse(sets.hasNext());
		
		verify(scm).getChangeSetList();
		verify(scm).getChangeSet(changeSets.get(0));
	}
	
	private List<ChangeSetInfo> someChangeSets() {
		List<ChangeSetInfo> changeSets = new ArrayList<ChangeSetInfo>();
		changeSets.add(new ChangeSetInfo("abcd", someNewDate));
		changeSets.add(new ChangeSetInfo("efgh", someOldDate));
		return changeSets;
	}
}
