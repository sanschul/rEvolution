package edu.usp.ime.revolution.scm;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.config.Configs;
import edu.usp.ime.revolution.scm.git.Git;

public class SCMFactoryTest {
	@Test
	public void shouldBuildGit() {
		Config config = mock(Config.class);
		when(config.get(Configs.SCM)).thenReturn("edu.usp.ime.revolution.scm.git.GitFactory");
		
		assertTrue(new SCMFactory().basedOn(config).getClass() == Git.class);
	}
	
	@Test(expected=SCMNotFoundException.class)
	public void shouldWarnIfInvalidSCM() {
		Config config = mock(Config.class);
		when(config.get(Configs.SCM)).thenReturn("invalid-scm");
		
		new SCMFactory().basedOn(config);		
	}

}