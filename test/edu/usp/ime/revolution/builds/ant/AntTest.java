package edu.usp.ime.revolution.builds.ant;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.ChangeSet;

public class AntTest {

	private Build ant;
	private CommandExecutor executor;
	private String task;

	@Before
	public void SetUp() {
		task = "compile";
		executor = mock(CommandExecutor.class);
		ant = new Ant(executor, task);
	}
	
	@Test
	public void ShouldCallAnt() throws Exception {
		ChangeSet set = mock(ChangeSet.class);
		
		when(set.getPath()).thenReturn("some/path");
		ant.build(set);
		
		verify(executor).runCommand("ant " + task);
		verify(executor).setWorkingDirectory(set.getPath());
	}
}
