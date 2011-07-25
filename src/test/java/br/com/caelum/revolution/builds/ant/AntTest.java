package br.com.caelum.revolution.builds.ant;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.executor.CommandExecutor;
import br.com.caelum.revolution.scm.SCM;


public class AntTest {

	private Build ant;
	private CommandExecutor executor;
	private String task;
	private String buildPath;
	private SCM scm;

	@Before
	public void setUp() {
		task = "compile";
		buildPath = "/build/path";
		
		executor = mock(CommandExecutor.class);
		scm = mock(SCM.class);
		ant = new Ant(executor, task, buildPath);
	}
	
	@Test
	public void shouldCallAnt() throws Exception {
		String path = "some/path";
		String commitId = "123";
		
		when(scm.goTo(commitId)).thenReturn(path);
		
		ant.build(commitId, scm);
		
		verify(executor).execute("ant " + task, path);
	}
	
	@Test
	public void shouldReturnBuildPath() throws Exception {
		BuildResult result = ant.build("123", scm);
		when(scm.goTo("123")).thenReturn("some/path");
		
		assertEquals(buildPath, result.getDirectory());
	}
}
