package br.com.caelum.revolution.builds;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import br.com.caelum.revolution.builds.ant.Ant;
import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.config.Configs;
import br.com.caelum.revolution.scm.SCM;

public class BuildFactoryTest {

	@Test
	public void shouldBuildAnt() {
		Config config = mock(Config.class);
		SCM scm = mock(SCM.class);
		
		when(config.get(Configs.BUILD)).thenReturn("br.com.caelum.revolution.builds.ant.AntFactory");
		
		assertTrue(new BuildFactory().basedOn(config, scm).getClass() == Ant.class);
	}
	
	@Test(expected=BuildNotFoundException.class)
	public void shouldWarnIfInvalidBuild() {
		Config config = mock(Config.class);
		SCM scm = mock(SCM.class);
		
		when(config.get(Configs.BUILD)).thenReturn("invalid-build");
		
		new BuildFactory().basedOn(config, scm);		
	}
}
