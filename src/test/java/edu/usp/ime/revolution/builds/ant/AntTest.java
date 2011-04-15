package edu.usp.ime.revolution.builds.ant;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.ChangeSet;

public class AntTest {

	private Build ant;
	private CommandExecutor executor;
	private String task;
	private ChangeSet set;
	private String buildPath;

	@Before
	public void setUp() {
		task = "compile";
		buildPath = "/build/path";
		
		executor = mock(CommandExecutor.class);
		ant = new Ant(executor, task, buildPath);
		set = mock(ChangeSet.class);
	}
	
	@Test
	public void shouldCallAnt() throws Exception {
		when(set.getPath()).thenReturn("some/path");
		ant.build(set);
		
		verify(executor).runCommand("ant " + task);
		verify(executor).setWorkingDirectory(set.getPath());
	}
	
	@Test
	public void shouldReturnBuildPath() throws Exception {
		when(set.getPath()).thenReturn("some/path");
		BuildResult result = ant.build(set);
		
		assertEquals(buildPath, result.getDirectory());
	}
}
