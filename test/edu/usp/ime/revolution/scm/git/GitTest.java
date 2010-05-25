package edu.usp.ime.revolution.scm.git;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetInfo;
import edu.usp.ime.revolution.scm.SCMException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import edu.usp.ime.revolution.executor.CommandExecutor;

public class GitTest {

	private final String output = "some/log/here";
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
		List<ChangeSetInfo> csList = aChangeSetList();
		
		when(exec.getCommandOutput()).thenReturn(output);
		when(logParser.parse(output)).thenReturn(csList);
		
		List<ChangeSetInfo> retrievedList = new Git(repository, logParser, exec).getChangeSetList();
		
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
		ChangeSetInfo specificChangeSet = new ChangeSetInfo("abcd", Calendar.getInstance());
		ChangeSet cs = new Git(repository, logParser, exec).getChangeSet(specificChangeSet);
		
		assertEquals(repository, cs.getPath());
		assertEquals(specificChangeSet, cs.getInfo());
		verify(exec).setWorkingDirectory(repository);
		verify(exec, times(3)).runCommand(any(String.class));		
	}
	
	@Test(expected=SCMException.class)
	public void ShouldThrowSCMExceptionIfGetChangeSetFails() throws Exception {
		when(exec.runCommand(any(String.class))).thenThrow(new Exception());
		
		new Git(repository, logParser, exec).getChangeSet(new ChangeSetInfo("123", Calendar.getInstance()));
	}

	private List<ChangeSetInfo> aChangeSetList() {
		List<ChangeSetInfo> list = new ArrayList<ChangeSetInfo>();
		list.add(new ChangeSetInfo("1234567890123456789012345678901234567890", Calendar.getInstance()));
		list.add(new ChangeSetInfo("9999999999999999999999999999999999999999", Calendar.getInstance()));
		return list;
	}
}
