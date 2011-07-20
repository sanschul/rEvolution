package edu.usp.ime.revolution.builds.ant;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.executor.CommandExecutor;

public class AntTest {

	private Build ant;
	private CommandExecutor executor;
	private String task;
	private String buildPath;

	@Before
	public void setUp() {
		task = "compile";
		buildPath = "/build/path";
		
		executor = mock(CommandExecutor.class);
		ant = new Ant(executor, task, buildPath);
	}
	
	@Test @Ignore
	public void shouldCallAnt() throws Exception {
		String path = "some/path";
		ant.build(path);
		
		verify(executor).execute("ant " + task, path);
	}
	
	@Test @Ignore
	public void shouldReturnBuildPath() throws Exception {
		BuildResult result = ant.build("some/path");
		
		assertEquals(buildPath, result.getDirectory());
	}
}
