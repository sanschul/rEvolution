package br.com.caelum.revolution.builds;


public interface Build {

	BuildResult build(String path) throws BuildException;

}
