package br.com.caelum.revolution.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.ArtifactStatus;
import br.com.caelum.revolution.domain.Commit;

public class CommitTest {

	@Test
	public void shouldAddAnArtifact() {
		Commit c = new Commit();
		
		c.addArtifact(new Artifact("name", "content", ArtifactStatus.NEW));
		
		assertEquals("name", c.getArtifacts().get(0).getName());
		assertEquals("content", c.getArtifacts().get(0).getDiff());
		assertEquals(ArtifactStatus.NEW, c.getArtifacts().get(0).getStatus());
	}
}
