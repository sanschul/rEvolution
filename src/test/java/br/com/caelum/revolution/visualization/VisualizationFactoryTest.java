package br.com.caelum.revolution.visualization;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.revolution.config.Config;

public class VisualizationFactoryTest {

	private Config config;

	@Before
	public void setUp() {
		config = mock(Config.class);
	}
	
	@Test
	public void shouldBuildBasedOnClassName() {
		assertTrue(new VisualizationFactory().basedOn(config, "FakeVisualizationFactory").getVisualization().getClass() == FakeVisualization.class);
	}
	
	@Test(expected=VisualizationNotFoundException.class)
	public void shouldWarnIfInvalidBuild() {
		
		new VisualizationFactory().basedOn(config, "invalid-visualization");		
	}

	
}
