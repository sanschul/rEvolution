package br.com.caelum.revolution.analyzers;

import static br.com.caelum.revolution.scm.changesets.ChangeSetBuilder.aCollectionWith;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.BuildException;
import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.domain.CommitConverter;
import br.com.caelum.revolution.persistence.HibernatePersistence;
import br.com.caelum.revolution.postaction.PostAction;
import br.com.caelum.revolution.scm.CommitData;
import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.scm.changesets.ChangeSet;
import br.com.caelum.revolution.scm.changesets.ChangeSetCollection;
import br.com.caelum.revolution.tools.Tool;
import br.com.caelum.revolution.tools.ToolException;


public class DefaultAnalyzerTest {

	private ChangeSet changeSet;
	private ChangeSetCollection changeSets;
	private Build build;
	private SCM scm;
	private HibernatePersistence persistence;
	private Session session;
	private CommitConverter converter;

	@Before
	public void setUp() {
		changeSet = new ChangeSet("123", Calendar.getInstance());
		changeSets = aCollectionWith(changeSet);
		build = mock(Build.class);
		scm = mock(SCM.class);
		converter = mock(CommitConverter.class);
		
		session = mock(Session.class);
		persistence = mock(HibernatePersistence.class);
		when(persistence.getSession()).thenReturn(session);
	}
	
	@Test
	public void shouldBuildAllChangeSets() throws BuildException {		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, someMetricTools(), converter, persistence);
		
		String path = "/repo/path";
		when(scm.goTo(changeSet)).thenReturn(path);
		analyzer.start(changeSets);
		
		verify(scm).goTo(changeSet);
		verify(build).build(path);
	}
	
	@Test
	public void shouldCalculateAllMetrics() throws ToolException {
		Tool tool = mock(Tool.class);
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(tool), converter, persistence);
		analyzer.start(changeSets);
		
		verify(tool).calculate(any(Commit.class), any(BuildResult.class));
	}
	
	@Test
	public void shouldTellAllObserversAboutAChangeSet() {
		PostAction observer = mock(PostAction.class);
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(mock(Tool.class)), converter, persistence);
		analyzer.addPostAction(observer);

		analyzer.start(changeSets);
		
		verify(observer).notify(any(Commit.class));
	}

	@Test @Ignore
	public void shouldGiveSessionToTools() {
		
	}

	@Test @Ignore
	public void shouldGiveScmToTools() {
		
	}

	
	@Test @Ignore
	public void shouldStartPersistEngine() {
		
	}

	@Test @Ignore
	public void shouldConfigurePersistentClasses() {
		
	}

	@Test @Ignore
	public void shouldPersistCurrentCommit() {
		
	}
	
	@Test @Ignore
	public void shouldEndPersistenceEngine() {
		
	}
	
	@Test @Ignore
	public void shouldOpenAndCommitTransaction() {
		
	}

	@Test @Ignore
	public void shouldConvertScmDataToCommit() {
		
	}
	
	@Test
	public void shouldDetailACommit() {
		CommitData commit = aCommitDataWithId("123");
		when(scm.detail("123")).thenReturn(commit);
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(mock(Tool.class)), converter,  persistence);
		analyzer.start(changeSets);
		
		verify(scm).detail("123");
	}
	@Test
	public void shouldGenerateAErrorIfAToolFails() throws ToolException {
		Tool failedTool = mock(Tool.class);
		when(failedTool.getName()).thenReturn("bad tool");
		
		CommitData commit = aCommitDataWithId("123");
		when(scm.detail("123")).thenReturn(commit);
		doThrow(new ToolException(new Exception())).when(failedTool).calculate(any(Commit.class), any(BuildResult.class));
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(failedTool), converter, persistence);
		analyzer.start(changeSets);
		
		assertEquals(1, analyzer.getErrors().size());
	}
	
	@Test
	public void shouldGenerateAErrorIfSomethingFailsInChangeset() throws BuildException {
		when(build.build(any(String.class))).thenThrow(new BuildException(new Exception()));
		Analyzer analyzer = new DefaultAnalyzer(scm, build, aToolListWith(mock(Tool.class)), converter, persistence);
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
	
	
	private CommitData aCommitDataWithId(String id) {
		CommitData c = mock(CommitData.class);
		when(c.getCommitId()).thenReturn(id);
		return c;
	}
	
}
