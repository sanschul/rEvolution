package edu.usp.ime.revolution.executor;

public interface CommandExecutor {

	void setOutputLogDevice(ILogDevice logDevice);
	void setErrorLogDevice(ILogDevice logDevice);
	void setWorkingDirectory(String workingDirectory);
	void setEnvironmentVar(String name, String value);
	String getCommandOutput();
	String getCommandError();
	int runCommand(String commandLine) throws Exception;

}