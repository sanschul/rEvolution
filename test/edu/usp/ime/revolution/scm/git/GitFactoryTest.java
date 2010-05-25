package edu.usp.ime.revolution.scm.git;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.config.Configs;
import edu.usp.ime.revolution.scm.SCM;

public class GitFactoryTest {
	
	@Test
	public void ShouldBuildNumberOfTilesTool() {
		Config config = mock(Config.class);
		when(config.get(Configs.SCM_REPOSITORY)).thenReturn("/some/repo");
		
		SCM scm = new GitFactory().build(config);
		
		verify(config).get(Configs.SCM_REPOSITORY);
		assertEquals(scm.getClass(), Git.class);
	}
}
