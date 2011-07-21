package edu.usp.ime.revolution.tools.bugorigin;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Artifact;
import edu.usp.ime.revolution.domain.ArtifactStatus;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.tools.ToolException;

public class SearchBugOriginTest {

	private SearchBugOrigin tool;
	private Session session;
	private SCM scm;

	@Before
	public void setUp() {
		session = mock(Session.class);
		scm = mock(SCM.class);
		tool = new SearchBugOrigin();
		tool.setSession(session);
		tool.setSCM(scm);
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
		Artifact artifact = new Artifact("file 1", code, ArtifactStatus.DEFAULT);
		Commit commit = new Commit();
		commit.addArtifact(artifact);
		
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
		Artifact artifact = new Artifact("file 1", code, ArtifactStatus.DEFAULT);
		Commit commit = new Commit();
		commit.addArtifact(artifact);
		
		tool.calculate(commit, new BuildResult("any path"));
		
		ArgumentCaptor<BugOrigin> argument = ArgumentCaptor.forClass(BugOrigin.class);
		verify(session, times(1)).save(argument.capture());
		
		BugOrigin value = argument.getValue();
		
		assertSame(buggedCommit, value.getBuggedCommit());
		verify(scm).blameCurrent("file 1", 2);
		
	}
}
