package edu.usp.ime.revolution.analyzers.observers;

import org.junit.Test;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.persistence.MetricPersistence;
import edu.usp.ime.revolution.scm.ChangeSetInfo;
import static org.mockito.Mockito.*;
import static edu.usp.ime.revolution.scm.ChangeSetBuilder.*;

public class PersistMetricsTest {
	@Test
	public void ShouldPersistMetricSet() {
		MetricPersistence persistence = mock(MetricPersistence.class);
		ChangeSetInfo cs = mock(ChangeSetInfo.class);
		MetricSet set = mock(MetricSet.class);
		
		AnalyzerObserver observer = new PersistMetrics(persistence);
		
		observer.notify(aChangeSet(cs), set);
		
		verify(persistence).save(set);
	}

}
