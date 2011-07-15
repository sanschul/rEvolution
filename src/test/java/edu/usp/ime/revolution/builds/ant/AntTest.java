package edu.usp.ime.revolution.builds.ant;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.changesets.ChangeSet;

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
	
	@Test @Ignore
	public void shouldCallAnt() throws Exception {
//		when(set.getPath()).thenReturn("some/path");
//		ant.build(set);
//		
//		verify(executor).runCommand("ant " + task);
//		verify(executor).setWorkingDirectory(set.getPath());
	}
	
	@Test @Ignore
	public void shouldReturnBuildPath() throws Exception {
//		when(set.getPath()).thenReturn("some/path");
//		BuildResult result = ant.build(set);
//		
//		assertEquals(buildPath, result.getDirectory());
	}
}
