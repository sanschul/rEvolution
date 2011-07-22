package br.com.caelum.revolution.tools.bugorigin;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.ArtifactKind;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.domain.Modification;
import br.com.caelum.revolution.domain.ModificationKind;
import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.tools.ToolException;


public class SearchBugOriginToolTest {

	private SearchBugOriginTool tool;
	private Session session;
	private SCM scm;

	@Before
	public void setUp() {
		session = mock(Session.class);
		scm = mock(SCM.class);
		tool = new SearchBugOriginTool(new String[] { "bug", "fix"});
		tool.setSession(session);
		tool.setSCM(scm);
	}
	
	@Test
	public void shouldIdentifyLineNumbersInTheMiddleOfTheDiff() throws ToolException {
		String code = "+ line added\r\n" 
			+ "@@ -10, 8 @@ bla bla\r\n" +
			"- line removed\r\n"
			+ "common line\r\n" 
			+ "+line added";
		
		// bugged commit
		Commit buggedCommit = new Commit();
		Criteria criteria = mock(Criteria.class);
		when(session.createCriteria(Commit.class)).thenReturn(criteria);
		when(criteria.add(any(Criterion.class))).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(buggedCommit);

		// blame returning the bugged hash
		when(scm.blameCurrent("file 1", 11)).thenReturn("bugged hash");
		
		// creating current artifact
		Artifact artifact = new Artifact("file 1", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact, ModificationKind.DEFAULT);
		commit.setMessage("a bug here");
		commit.addArtifact(artifact);
		commit.addModification(modification);
		
		tool.calculate(commit, new BuildResult("any path"));
		
		ArgumentCaptor<BugOrigin> argument = ArgumentCaptor.forClass(BugOrigin.class);
		verify(session).save(argument.capture());
		
		BugOrigin value = argument.getValue();
		
		assertSame(buggedCommit, value.getBuggedCommit());
		verify(scm).blameCurrent("file 1", 11);
		
	}
	
	@Test
	public void shouldIgnoreCommitMessagesThatDoNotContainKeywords() throws ToolException {
		Commit commit = new Commit();
		commit.setMessage("no matched keyword here");
		
		tool.calculate(commit, new BuildResult("path"));
		
		verify(session, times(0)).save(any(Object.class));
	}
	
	@Test
	public void shouldGetCommitsForAllBuggedLines() throws ToolException {
		String code = "+ line added\r\n" 
			+ "- line removed\r\n"
			+ "common line\r\n" 
			+ "+line added";
		
		// bugged commit
		Commit buggedCommit = new Commit();
		Criteria criteria = mock(Criteria.class);
		when(session.createCriteria(Commit.class)).thenReturn(criteria);
		when(criteria.add(any(Criterion.class))).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(buggedCommit);

		// blame returning the bugged hash
		when(scm.blameCurrent("file 1", 2)).thenReturn("bugged hash");
		
		// creating current artifact
		Artifact artifact = new Artifact("file 1", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact, ModificationKind.DEFAULT);
		commit.setMessage("a bug here");
		commit.addArtifact(artifact);
		commit.addModification(modification);

		
		tool.calculate(commit, new BuildResult("any path"));
		
		ArgumentCaptor<BugOrigin> argument = ArgumentCaptor.forClass(BugOrigin.class);
		verify(session).save(argument.capture());
		
		BugOrigin value = argument.getValue();
		
		assertSame(buggedCommit, value.getBuggedCommit());
		verify(scm).blameCurrent("file 1", 2);
		
	}
	
	@Test
	public void shouldNotGetRepeatedCommits() throws ToolException {
		String code = "+ line added\r\n" 
			+ "- line removed\r\n"
			+ "- line removed\r\n"
			+ "common line\r\n" 
			+ "+line added";
		
		// bugged commit
		Commit buggedCommit = new Commit();
		Criteria criteria = mock(Criteria.class);
		when(session.createCriteria(Commit.class)).thenReturn(criteria);
		when(criteria.add(any(Criterion.class))).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(buggedCommit);

		// blame returning the bugged hash
		when(scm.blameCurrent("file 1", 2)).thenReturn("bugged hash");
		when(scm.blameCurrent("file 1", 3)).thenReturn("bugged hash");
		
		// creating current artifact
		Artifact artifact = new Artifact("file 1", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact, ModificationKind.DEFAULT);
		commit.setMessage("a fix here");
		commit.addArtifact(artifact);
		commit.addModification(modification);

		
		tool.calculate(commit, new BuildResult("any path"));
		
		ArgumentCaptor<BugOrigin> argument = ArgumentCaptor.forClass(BugOrigin.class);
		verify(session, times(1)).save(argument.capture());
		
		BugOrigin value = argument.getValue();
		
		assertSame(buggedCommit, value.getBuggedCommit());
		verify(scm).blameCurrent("file 1", 2);
		
	}
}
