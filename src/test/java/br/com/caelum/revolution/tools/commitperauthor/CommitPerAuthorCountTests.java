package br.com.caelum.revolution.tools.commitperauthor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommitPerAuthorCountTests {

	@Test
	public void shouldIncreaseCount() {
		CommitPerAuthorCount commit = new CommitPerAuthorCount();
		
		assertEquals(0, commit.getCount());
		commit.increaseCount();
		assertEquals(1, commit.getCount());
		commit.increaseCount();
		assertEquals(2, commit.getCount());
	}
}
