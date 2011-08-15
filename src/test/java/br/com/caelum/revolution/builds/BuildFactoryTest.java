package br.com.caelum.revolution.builds;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import br.com.caelum.revolution.builds.ant.Ant;
import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.config.Configs;

public class BuildFactoryTest {

	@Test
	public void shouldBuildAnt() {
		Config config = mock(Config.class);
		
		when(config.asString(Configs.BUILD)).thenReturn("br.com.caelum.revolution.builds.ant.AntFactory");
		
		assertTrue(new BuildFactory().basedOn(config).getClass() == Ant.class);
	}
	
	@Test(expected=BuildNotFoundException.class)
	public void shouldWarnIfInvalidBuild() {
		Config config = mock(Config.class);
		
		when(config.asString(Configs.BUILD)).thenReturn("invalid-build");
		
		new BuildFactory().basedOn(config);		
	}
}
