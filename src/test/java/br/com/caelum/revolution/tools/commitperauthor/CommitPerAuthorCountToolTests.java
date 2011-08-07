package br.com.caelum.revolution.tools.commitperauthor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Author;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.tools.ToolException;

public class CommitPerAuthorCountToolTests {

	private Session session;
	private CommitPerAuthorCountTool tool;

	@Before
	public void setUp() {
		session = mock(Session.class);
		tool = new CommitPerAuthorCountTool();
		tool.setSession(session);
	}
	
	@Test
	public void shouldCreateCounterForAFirstTimeAuthorCommitsAnArtifact() throws ToolException {
		
		Author author = new Author();
		Artifact artifact = new Artifact();
		
		Commit commit = aCommitWith(author, artifact);
		
		Criteria criteria = mock(Criteria.class);
		when(session.createCriteria(CommitPerAuthorCount.class)).thenReturn(criteria);
		when(criteria.add(any(Criterion.class))).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(null);
		
		tool.calculate(commit, null);
		
		ArgumentCaptor<CommitPerAuthorCount> argument = ArgumentCaptor.forClass(CommitPerAuthorCount.class);
		verify(session).save(argument.capture());
		
		CommitPerAuthorCount counter = argument.getValue();
		
		assertSame(author, counter.getAuthor());
		assertSame(artifact, counter.getArtifact());
		assertEquals(1, counter.getCount());

	}

	private Commit aCommitWith(Author author, Artifact artifact) {
		Commit commit = new Commit();
		commit.setAuthor(author);
		commit.addArtifact(artifact);
		return commit;
	}

	@Test
	public void shouldIncrementCounterForAnAuthorThatAlreadyCommittedAnArtifact() throws ToolException {
		
		Author author = new Author();
		Artifact artifact = new Artifact();
		
		Commit commit = aCommitWith(author, artifact);
		CommitPerAuthorCount counter = new CommitPerAuthorCount(artifact, author);
		
		Criteria criteria = mock(Criteria.class);
		when(session.createCriteria(CommitPerAuthorCount.class)).thenReturn(criteria);
		when(criteria.add(any(Criterion.class))).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(counter);
		
		tool.calculate(commit, null);
		
		assertEquals(2, counter.getCount());
	}
	
}
