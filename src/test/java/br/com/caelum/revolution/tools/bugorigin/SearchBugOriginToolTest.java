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
	
	private Commit aCommitWithId(String id) {
		Commit commit = new Commit();
		commit.setCommitId(id);
		return commit;
	}
	@Test
	public void shouldIdentifyLineNumbersInTheMiddleOfTheDiff() throws ToolException {
		String diff = "+ line added\r\n" 
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
		when(scm.blameCurrent("file 1", 10)).thenReturn("bugged hash");
		
		// creating current artifact
		Artifact artifact = new Artifact("file 1", ArtifactKind.CODE);
		Commit commit = new Commit();
		commit.setPriorCommit(aCommitWithId("prior-commit"));
		Modification modification = new Modification(diff, commit, artifact, ModificationKind.DEFAULT);
		commit.setMessage("a bug was fixed here");
		commit.addArtifact(artifact);
		commit.addModification(modification);
		
		tool.calculate(commit, new BuildResult("any path"));
		
		ArgumentCaptor<BugOrigin> argument = ArgumentCaptor.forClass(BugOrigin.class);
		verify(session).save(argument.capture());
		
		BugOrigin value = argument.getValue();
		assertSame(buggedCommit, value.getBuggedCommit());
		
		verify(scm).blameCurrent("file 1", 10);
		verify(scm).goTo("prior-commit");
		
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
		String diff = "old line\r\n" 
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
		commit.setPriorCommit(aCommitWithId("prior-commit"));
		Modification modification = new Modification(diff, commit, artifact, ModificationKind.DEFAULT);
		commit.setMessage("a bug was fixed here");
		commit.addArtifact(artifact);
		commit.addModification(modification);
		
		tool.calculate(commit, new BuildResult("any path"));
		
		ArgumentCaptor<BugOrigin> argument = ArgumentCaptor.forClass(BugOrigin.class);
		verify(session).save(argument.capture());
		
		BugOrigin value = argument.getValue();
		
		assertSame(buggedCommit, value.getBuggedCommit());
		verify(scm).blameCurrent("file 1", 2);
		verify(scm).blameCurrent("file 1", 3);
	}
	
	@Test
	public void shouldMakeTheRightMathIfLinesWereAddedInTheNewFileSoTheyDoNotExistInPrior() throws ToolException {
		String diff = "old line\r\n" 
			+ "- line removed\r\n"
			+ "+ line added that must be ignored in math\r\n"
			+ "+ line added that must be ignored in math\r\n"
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
		commit.setPriorCommit(aCommitWithId("prior-commit"));
		Modification modification = new Modification(diff, commit, artifact, ModificationKind.DEFAULT);
		commit.setMessage("a bug was fixed here");
		commit.addArtifact(artifact);
		commit.addModification(modification);
		
		tool.calculate(commit, new BuildResult("any path"));
		
		ArgumentCaptor<BugOrigin> argument = ArgumentCaptor.forClass(BugOrigin.class);
		verify(session).save(argument.capture());
		
		BugOrigin value = argument.getValue();
		
		assertSame(buggedCommit, value.getBuggedCommit());
		verify(scm).blameCurrent("file 1", 2);
		verify(scm).blameCurrent("file 1", 3);
	}
	
	
	
}
