package edu.usp.ime.revolution.algorithms;

import org.junit.Test;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetCollection;
import static org.mockito.Mockito.*;
import static edu.usp.ime.revolution.scm.ChangeSetBuilder.*;

public class DefaultAnalyzerTest {

	@Test
	public void ShouldBuildAllChangeSets() {
		ChangeSet changeSet = aChangeSet("cs 1");
		ChangeSetCollection collection = aCollectionWith(changeSet);
		Build build = mock(Build.class);
		
		DefaultAnalyzer analyzer = new DefaultAnalyzer(build);
		
		analyzer.start(collection);
		
		verify(build).build(changeSet);
	}
}
