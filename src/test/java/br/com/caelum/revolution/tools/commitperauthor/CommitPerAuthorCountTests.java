package br.com.caelum.revolution.tools.commitperauthor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Author;

public class CommitPerAuthorCountTests {

	@Test
	public void shouldIncreaseCount() {
		CommitPerAuthorCount commit = new CommitPerAuthorCount(new Artifact(), new Author());
		
		assertEquals(1, commit.getCount());
		commit.increaseCount();
		assertEquals(2, commit.getCount());
		commit.increaseCount();
		assertEquals(3, commit.getCount());
	}
}
