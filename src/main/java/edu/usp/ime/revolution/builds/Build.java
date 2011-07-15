package edu.usp.ime.revolution.builds;


public interface Build {

	BuildResult build(String path) throws BuildException;

}
