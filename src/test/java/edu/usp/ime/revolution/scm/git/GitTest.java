package edu.usp.ime.revolution.scm.git;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.SCMException;

public class GitTest {

	private final String output = "some/log/here";
	private final String repository = "/some/path/to/rep";
	private CommandExecutor exec;
	private GitLogParser logParser;
	
	@Before
	public void setUp() {
		exec = mock(CommandExecutor.class);
		logParser = mock(GitLogParser.class);
	}
	
	@Test
	public void shouldReturnChangeSetList() throws Exception {
		List<ChangeSet> csList = aChangeSetList();
		
		when(exec.getCommandOutput()).thenReturn(output);
		when(logParser.parse(output)).thenReturn(csList);
		
		List<ChangeSet> retrievedList = new Git(repository, logParser, exec).getChangeSets();
		
		assertEquals(retrievedList, csList);
		verify(exec).setWorkingDirectory(repository);
		verify(exec).getCommandOutput();
		verify(logParser).parse(output);
		verify(exec).runCommand(any(String.class));
	}
	
	@Test(expected=SCMException.class)
	public void shouldThrowSCMExceptionIfChangeSetListFails() throws Exception {
		when(exec.runCommand(any(String.class))).thenThrow(new Exception());
		
		new Git(repository, logParser, exec).getChangeSets();
	}
	
	@Test
	public void shouldGoToASpecificChangeSet() throws Exception {
		ChangeSet specificChangeSet = new ChangeSet("abcd", Calendar.getInstance());
		String path = new Git(repository, logParser, exec).goTo(specificChangeSet);
		
		assertEquals(repository, path);
		verify(exec).setWorkingDirectory(repository);
		verify(exec, times(3)).runCommand(any(String.class));		
	}
	
	@Test(expected=SCMException.class)
	public void shouldThrowSCMExceptionIfGetChangeSetFails() throws Exception {
		when(exec.runCommand(any(String.class))).thenThrow(new Exception());
		
		new Git(repository, logParser, exec).goTo(new ChangeSet("123", Calendar.getInstance()));
	}

	private List<ChangeSet> aChangeSetList() {
		List<ChangeSet> list = new ArrayList<ChangeSet>();
		list.add(new ChangeSet("1234567890123456789012345678901234567890", Calendar.getInstance()));
		list.add(new ChangeSet("9999999999999999999999999999999999999999", Calendar.getInstance()));
		return list;
	}
}
