package edu.usp.ime.revolution.analyzers;

import static edu.usp.ime.revolution.scm.ChangeSetBuilder.aChangeSet;
import static edu.usp.ime.revolution.scm.ChangeSetBuilder.aCollectionWith;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.usp.ime.revolution.analyzers.observers.AnalyzerObserver;
import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildException;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetCollection;
import edu.usp.ime.revolution.scm.ChangeSetInfo;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.tools.MetricTool;
import edu.usp.ime.revolution.tools.ToolException;

public class DefaultAnalyzerTest {

	private ChangeSet changeSet;
	private ChangeSetCollection changeSets;
	private Build build;
	private SCM scm;

	@Before
	public void setUp() {
		changeSet = aChangeSet(new ChangeSetInfo("123", Calendar.getInstance()));
		changeSets = aCollectionWith(changeSet);
		build = mock(Build.class);
		scm = mock(SCM.class);
	}
	
	@Test
	public void shouldBuildAllChangeSets() throws BuildException {		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, someMetricTools());
		
		analyzer.start(changeSets);
		
		verify(build).build(changeSet);
	}
	
	@Test
	public void shouldCalculateAllMetrics() throws ToolException {
		MetricTool tool = mock(MetricTool.class);
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(tool));
		analyzer.start(changeSets);
		
		verify(tool).calculate(any(ChangeSet.class), any(BuildResult.class));
	}
	
	@Test
	public void shouldTellAllObserversAboutAChangeSet() {
		AnalyzerObserver observer = mock(AnalyzerObserver.class);
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(mock(MetricTool.class)));
		analyzer.addObserver(observer);

		analyzer.start(changeSets);
		
		verify(observer).notify(any(ChangeSet.class));
	}
	
	@Test
	public void shouldGenerateAErrorIfAToolFails() throws ToolException {
		MetricTool failedTool = mock(MetricTool.class);
		doThrow(new ToolException(new Exception())).when(failedTool).calculate(any(ChangeSet.class), any(BuildResult.class));
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(failedTool));
		analyzer.start(changeSets);
		
		assertEquals(1, analyzer.getErrors().size());
	}
	
	@Test
	public void shouldGenerateAErrorIfSomethingFailsInChangeset() throws BuildException {
		when(build.build(any(ChangeSet.class))).thenThrow(new BuildException(new Exception()));
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(mock(MetricTool.class)));
		analyzer.start(changeSets);
		
		assertEquals(1, analyzer.getErrors().size());
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
