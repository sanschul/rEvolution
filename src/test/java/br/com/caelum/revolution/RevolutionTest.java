package br.com.caelum.revolution;

import org.junit.Test;

import br.com.caelum.revolution.analyzers.Analyzer;
import br.com.caelum.revolution.analyzers.DefaultAnalyzerRunner;
import br.com.caelum.revolution.scm.changesets.ChangeSetCollection;
import static org.mockito.Mockito.*;

public class RevolutionTest {

	@Test
	public void shouldAnalyzeRepo() {
		Analyzer analyzer = mock(Analyzer.class);
		ChangeSetCollection collection = mock(ChangeSetCollection.class);
		
		AnalyzerRunner rev = new DefaultAnalyzerRunner(analyzer, collection);
		rev.start();
		
		verify(analyzer).start(collection);
	}
}
