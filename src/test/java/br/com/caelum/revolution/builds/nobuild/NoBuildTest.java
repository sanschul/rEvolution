package br.com.caelum.revolution.builds.nobuild;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import br.com.caelum.revolution.builds.BuildException;
import br.com.caelum.revolution.builds.nobuild.NoBuild;


public class NoBuildTest {

	@Test
	public void shouldReturnNothing() throws BuildException {
		NoBuild noBuild = new NoBuild();
		assertNull(noBuild.build("any-path"));
	}
}
