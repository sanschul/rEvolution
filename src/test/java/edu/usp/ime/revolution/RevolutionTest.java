package edu.usp.ime.revolution;

import org.junit.Test;
import static org.mockito.Mockito.*;
import edu.usp.ime.revolution.analyzers.Analyzer;
import edu.usp.ime.revolution.analyzers.AnalyzerRunner;
import edu.usp.ime.revolution.scm.changesets.ChangeSetCollection;

public class RevolutionTest {

	@Test
	public void shouldAnalyzeRepo() {
		Analyzer analyzer = mock(Analyzer.class);
		ChangeSetCollection collection = mock(ChangeSetCollection.class);
		
		AnalyzerRunner rev = new AnalyzerRunner(analyzer, collection);
		rev.start();
		
		verify(analyzer).start(collection);
	}
}
