// Extracted from http://www.javalobby.org/java/forums/t53333.html

package edu.usp.ime.revolution.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
/**
 * Usage of following class can go as ...
 * <P><PRE><CODE>
 * 		SysCommandExecutor cmdExecutor = new SysCommandExecutor();
 * 		cmdExecutor.setOutputLogDevice(new LogDevice());
 * 		cmdExecutor.setErrorLogDevice(new LogDevice());
 * 		int exitStatus = cmdExecutor.runCommand(commandLine);
 * </CODE></PRE></P>
 * 
 * OR
 * 
 * <P><PRE><CODE>
 * 		SysCommandExecutor cmdExecutor = new SysCommandExecutor(); 		
 * 		int exitStatus = cmdExecutor.runCommand(commandLine);
 * 
 * 		String cmdError = cmdExecutor.getCommandError();
 * 		String cmdOutput = cmdExecutor.getCommandOutput(); 
 * </CODE></PRE></P> 
 */
public class SysCommandExecutor implements CommandExecutor
{	
	private ILogDevice fOuputLogDevice = null;
	private ILogDevice fErrorLogDevice = null;
	private String fWorkingDirectory = null;
	private List<EnvironmentVar> fEnvironmentVarList = null;
	
	private StringBuffer fCmdOutput = null;
	private StringBuffer fCmdError = null;
	private AsyncStreamReader fCmdOutputThread = null;
	private AsyncStreamReader fCmdErrorThread = null;	
	
	/* (non-Javadoc)
	 * @see edu.usp.ime.revolution.executor.CommandExecutor#setOutputLogDevice(edu.usp.ime.revolution.executor.ILogDevice)
	 */
	public void setOutputLogDevice(ILogDevice logDevice)
	{
		fOuputLogDevice = logDevice;
	}
	
	/* (non-Javadoc)
	 * @see edu.usp.ime.revolution.executor.CommandExecutor#setErrorLogDevice(edu.usp.ime.revolution.executor.ILogDevice)
	 */
	public void setErrorLogDevice(ILogDevice logDevice)
	{
		fErrorLogDevice = logDevice;
	}
	
	/* (non-Javadoc)
	 * @see edu.usp.ime.revolution.executor.CommandExecutor#setWorkingDirectory(java.lang.String)
	 */
	public void setWorkingDirectory(String workingDirectory) {
		fWorkingDirectory = workingDirectory;
	}
	
	/* (non-Javadoc)
	 * @see edu.usp.ime.revolution.executor.CommandExecutor#setEnvironmentVar(java.lang.String, java.lang.String)
	 */
	public void setEnvironmentVar(String name, String value)
	{
		if( fEnvironmentVarList == null )
			fEnvironmentVarList = new ArrayList<EnvironmentVar>();
		
		fEnvironmentVarList.add(new EnvironmentVar(name, value));
	}
	
	/* (non-Javadoc)
	 * @see edu.usp.ime.revolution.executor.CommandExecutor#getCommandOutput()
	 */
	public String getCommandOutput() {		
		return fCmdOutput.toString();
	}
	
	/* (non-Javadoc)
	 * @see edu.usp.ime.revolution.executor.CommandExecutor#getCommandError()
	 */
	public String getCommandError() {
		return fCmdError.toString();
	}
	
	/* (non-Javadoc)
	 * @see edu.usp.ime.revolution.executor.CommandExecutor#runCommand(java.lang.String)
	 */
	public int runCommand(String commandLine) throws Exception
	{
		/* run command */
		Process process = runCommandHelper(commandLine);
		
		/* start output and error read threads */
		startOutputAndErrorReadThreads(process.getInputStream(), process.getErrorStream());
	    
		/* wait for command execution to terminate */
		int exitStatus = -1;
		try {
			exitStatus = process.waitFor();
					
		} catch (Throwable ex) {
			throw new Exception(ex.getMessage());
			
		} finally {
			/* notify output and error read threads to stop reading */
			notifyOutputAndErrorReadThreadsToStopReading();
		}
		
		return exitStatus;
	}	
	
	private Process runCommandHelper(String commandLine) throws IOException
	{
		Process process = null;		
		if( fWorkingDirectory == null )
			process = Runtime.getRuntime().exec(commandLine, getEnvTokens());
		else
			process = Runtime.getRuntime().exec(commandLine, getEnvTokens(), new File(fWorkingDirectory));
		
		return process;
	}
	
	private void startOutputAndErrorReadThreads(InputStream processOut, InputStream processErr)
	{
		fCmdOutput = new StringBuffer();
		fCmdOutputThread = new AsyncStreamReader(processOut, fCmdOutput, fOuputLogDevice, "OUTPUT");		
		fCmdOutputThread.start();
		
		fCmdError = new StringBuffer();
		fCmdErrorThread = new AsyncStreamReader(processErr, fCmdError, fErrorLogDevice, "ERROR");
		fCmdErrorThread.start();
	}
	
	private void notifyOutputAndErrorReadThreadsToStopReading()
	{
		fCmdOutputThread.stopReading();
		fCmdErrorThread.stopReading();
	}
	
	private String[] getEnvTokens()
	{
		if( fEnvironmentVarList == null )
			return null;
		
		String[] envTokenArray = new String[fEnvironmentVarList.size()];
		Iterator<EnvironmentVar> envVarIter = fEnvironmentVarList.iterator();
		int nEnvVarIndex = 0; 
		while (envVarIter.hasNext() == true)
		{
			EnvironmentVar envVar = (EnvironmentVar)(envVarIter.next());
			String envVarToken = envVar.fName + "=" + envVar.fValue;
			envTokenArray[nEnvVarIndex++] = envVarToken;
		}
		
		return envTokenArray;
	}	
}
 
class AsyncStreamReader extends Thread
{
	private StringBuffer fBuffer = null;
	private InputStream fInputStream = null;
	private boolean fStop = false;
	private ILogDevice fLogDevice = null;
	
	public AsyncStreamReader(InputStream inputStream, StringBuffer buffer, ILogDevice logDevice, String threadId)
	{
		fInputStream = inputStream;
		fBuffer = buffer;
		fLogDevice = logDevice;
		
		System.getProperty("line.separator");
	}	
	
	public String getBuffer() {		
		return fBuffer.toString();
	}
	
	public void run()
	{
		try {
			readCommandOutput();
		} catch (Exception ex) {
			//ex.printStackTrace(); //DEBUG
		}
	}
	
	private void readCommandOutput() throws IOException
	{		
		BufferedReader bufOut = new BufferedReader(new InputStreamReader(fInputStream));		
		
		char c = '\0';
		while ( (fStop == false) && ((c = (char)bufOut.read()) != -1) )
		{
			fBuffer.append(c);
			printToDisplayDevice(c);
		}		
		bufOut.close();
		//printToConsole("END OF: " + fThreadId); //DEBUG
	}
	
	public void stopReading() {
		fStop = true;
	}
	
	private void printToDisplayDevice(char c)
	{
		if( fLogDevice != null )
			fLogDevice.log(c);
		else
		{
			//printToConsole(c);//DEBUG
		}
	}
}

class EnvironmentVar
{
	public String fName = null;
	public String fValue = null;
	
	public EnvironmentVar(String name, String value)
	{
		fName = name;
		fValue = value;
	}
}

