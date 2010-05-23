package edu.usp.ime.revolution.algorithms;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetCollection;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.metrics.MetricStore;
import edu.usp.ime.revolution.metrics.MetricTool;
import static org.mockito.Mockito.*;
import static edu.usp.ime.revolution.scm.ChangeSetBuilder.*;

public class DefaultAnalyzerTest {

	private ChangeSet changeSet;
	private ChangeSetCollection changeSets;
	private Build build;
	private MetricStore store;

	@Before
	public void SetUp() {
		changeSet = aChangeSet("cs 1");
		changeSets = aCollectionWith(changeSet);
		build = mock(Build.class);
		store = mock(MetricStore.class);
	}
	
	@Test
	public void ShouldBuildAllChangeSets() {		
		DefaultAnalyzer analyzer = new DefaultAnalyzer(build, store, someMetricTools());
		
		analyzer.start(changeSets);
		
		verify(build).build(changeSet);
	}
	
	@Test
	public void ShouldCalculateAllMetrics() {
		MetricTool tool = mock(MetricTool.class);
		
		DefaultAnalyzer analyzer = new DefaultAnalyzer(build, store, aToolListWith(tool));
		analyzer.start(changeSets);
		
		verify(tool).calculate(any(ChangeSet.class), any(BuildResult.class), any(MetricSet.class));
		verify(store).buildSet(any(String.class));
	}

	private List<MetricTool> aToolListWith(MetricTool tool) {
		List<MetricTool> tools = new ArrayList<MetricTool>();
		tools.add(tool);
		return tools;
	}

	private List<MetricTool> someMetricTools() {
		List<MetricTool> tools = new ArrayList<MetricTool>();
		tools.add(mock(MetricTool.class));
		return tools;
	}
}
