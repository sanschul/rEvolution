package edu.usp.ime.revolution.analyzers;

import static edu.usp.ime.revolution.scm.changesets.ChangeSetBuilder.aCollectionWith;
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
import org.junit.Ignore;
import org.junit.Test;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildException;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.persistence.HibernatePersistence;
import edu.usp.ime.revolution.postaction.PostAction;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.changesets.ChangeSet;
import edu.usp.ime.revolution.scm.changesets.ChangeSetCollection;
import edu.usp.ime.revolution.tools.Tool;
import edu.usp.ime.revolution.tools.ToolException;

public class DefaultAnalyzerTest {

	private ChangeSet changeSet;
	private ChangeSetCollection changeSets;
	private Build build;
	private SCM scm;
	private HibernatePersistence persistence;

	@Before
	public void setUp() {
		changeSet = new ChangeSet("123", Calendar.getInstance());
		changeSets = aCollectionWith(changeSet);
		build = mock(Build.class);
		scm = mock(SCM.class);
		persistence = mock(HibernatePersistence.class);
	}
	
	@Test
	public void shouldBuildAllChangeSets() throws BuildException {		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, someMetricTools(), persistence);
		
		String path = "/repo/path";
		when(scm.goTo(changeSet)).thenReturn(path);
		analyzer.start(changeSets);
		
		verify(scm).goTo(changeSet);
		verify(build).build(path);
	}
	
	@Test
	public void shouldCalculateAllMetrics() throws ToolException {
		Tool tool = mock(Tool.class);
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(tool), persistence);
		analyzer.start(changeSets);
		
		verify(tool).calculate(any(Commit.class), any(BuildResult.class));
	}
	
	@Test
	public void shouldTellAllObserversAboutAChangeSet() {
		PostAction observer = mock(PostAction.class);
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(mock(Tool.class)), persistence);
		analyzer.addPostAction(observer);

		analyzer.start(changeSets);
		
		verify(observer).notify(any(Commit.class));
	}
	
	@Test @Ignore
	public void shouldStartPersistEngine() {
		
	}

	@Test @Ignore
	public void shouldConfigurePersistentClasses() {
		
	}
	
	@Test @Ignore
	public void shouldEndPersistenceEngine() {
		
	}
	
	@Test @Ignore
	public void shouldOpenAndCommitTransaction() {
		
	}
	
	@Test
	public void shouldDetailACommit() {
		Commit commit = aCommitWithId("123");
		when(scm.detail("123")).thenReturn(commit);
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(mock(Tool.class)), persistence);
		analyzer.start(changeSets);
		
		verify(scm).detail("123");
	}
	@Test
	public void shouldGenerateAErrorIfAToolFails() throws ToolException {
		Tool failedTool = mock(Tool.class);
		when(failedTool.getName()).thenReturn("bad tool");
		
		Commit commit = aCommitWithId("123");
		when(scm.detail("123")).thenReturn(commit);
		doThrow(new ToolException(new Exception())).when(failedTool).calculate(any(Commit.class), any(BuildResult.class));
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(failedTool), persistence);
		analyzer.start(changeSets);
		
		assertEquals(1, analyzer.getErrors().size());
	}
	
	@Test
	public void shouldGenerateAErrorIfSomethingFailsInChangeset() throws BuildException {
		when(build.build(any(String.class))).thenThrow(new BuildException(new Exception()));
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(mock(Tool.class)), persistence);
		analyzer.start(changeSets);
		
		assertEquals(1, analyzer.getErrors().size());
	}

	private List<Tool> aToolListWith(Tool tool) {
		List<Tool> tools = new ArrayList<Tool>();
		tools.add(tool);
		return tools;
	}

	private List<Tool> someMetricTools() {
		List<Tool> tools = new ArrayList<Tool>();
		tools.add(mock(Tool.class));
		return tools;
	}
	
	
	private Commit aCommitWithId(String id) {
		Commit c = mock(Commit.class);
		when(c.getId()).thenReturn(id);
		return c;
	}

}
