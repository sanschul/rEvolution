package edu.usp.ime.revolution.algorithms;

import java.util.Iterator;

import org.junit.Test;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.changeset.ChangeSet;
import edu.usp.ime.revolution.changeset.ChangeSetCollection;
import static org.mockito.Mockito.*;

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

	private ChangeSet aChangeSet(final String name) {
		return new ChangeSet() {
			public String getName() {
				return name;
			}
		};
	}

	private ChangeSetCollection aCollectionWith(final ChangeSet changeSet) {
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
