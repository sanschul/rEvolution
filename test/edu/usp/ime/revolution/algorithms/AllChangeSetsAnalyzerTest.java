package edu.usp.ime.revolution.algorithms;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.SCM;
import static org.mockito.Mockito.*;

public class AllChangeSetsAnalyzerTest {

	@Test
	public void ShouldIterateIntoAllChangeSets() {
		AllChangeSetsAnalyzer analyzer = new AllChangeSetsAnalyzer();
		
		SCM scm = mock(SCM.class);
		when(scm.getChangeSetList()).thenReturn(someChangeSets());
		when(scm.getChangeSet("abcd")).thenReturn(aChangeSet());
		when(scm.getChangeSet("efgh")).thenReturn(aChangeSet());
		
		analyzer.start(scm);
		
		verify(scm).getChangeSetList();
		verify(scm).getChangeSet("abcd");
		verify(scm).getChangeSet("efgh");
	}

	private ChangeSet aChangeSet() {
		return new ChangeSet() {
			
		};
	}

	private List<String> someChangeSets() {
		List<String> changeSets = new ArrayList<String>();
		changeSets.add("abcd");
		changeSets.add("efgh");
		return changeSets;
	}
}
