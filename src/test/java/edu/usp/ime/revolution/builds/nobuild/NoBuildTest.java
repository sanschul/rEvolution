package edu.usp.ime.revolution.builds.nobuild;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import edu.usp.ime.revolution.builds.BuildException;

public class NoBuildTest {

	@Test
	public void shouldReturnNothing() throws BuildException {
		NoBuild noBuild = new NoBuild();
		assertNull(noBuild.build("any-path"));
	}
}
