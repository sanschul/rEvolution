package edu.usp.ime.revolution.scm.git;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.SCMException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import edu.usp.ime.revolution.executor.CommandExecutor;

public class GitTest {

	private final String output = "1234567890123456789012345678901234567890 message\n9999999999999999999999999999999999999999 message 2";
	private final String repository = "/some/path/to/rep";
	private CommandExecutor exec;
	private GitLogParser logParser;
	
	@Before
	public void SetUp() {
		exec = mock(CommandExecutor.class);
		logParser = mock(GitLogParser.class);
	}
	
	@Test
	public void ShouldReturnChangeSetList() throws Exception {
		List<String> csList = aChangeSetList();
		
		when(exec.getCommandOutput()).thenReturn(output);
		when(logParser.parse(output)).thenReturn(csList);
		
		List<String> retrievedList = new Git(repository, logParser, exec).getChangeSetList();
		
		assertEquals(retrievedList, csList);
		verify(exec).setWorkingDirectory(repository);
		verify(exec).getCommandOutput();
		verify(logParser).parse(output);
		verify(exec).runCommand(any(String.class));
	}
	
	@Test(expected=SCMException.class)
	public void ShouldThrowSCMExceptionIfChangeSetListFails() throws Exception {
		when(exec.runCommand(any(String.class))).thenThrow(new Exception());
		
		new Git(repository, logParser, exec).getChangeSetList();
	}
	
	@Test
	public void ShouldGoToASpecificChangeSet() throws Exception {
		String specificChangeSet = "1234";
		ChangeSet cs = new Git(repository, logParser, exec).getChangeSet(specificChangeSet);
		
		assertEquals(repository, cs.getPath());
		assertEquals(specificChangeSet, cs.getId());
		verify(exec).setWorkingDirectory(repository);
		verify(exec, times(2)).runCommand(any(String.class));		
	}
	
	@Test(expected=SCMException.class)
	public void ShouldThrowSCMExceptionIfGetChangeSetFails() throws Exception {
		when(exec.runCommand(any(String.class))).thenThrow(new Exception());
		
		new Git(repository, logParser, exec).getChangeSet("123");
	}

	private List<String> aChangeSetList() {
		List<String> list = new ArrayList<String>();
		list.add("1234567890123456789012345678901234567890");
		list.add("9999999999999999999999999999999999999999");
		return list;
	}
}
