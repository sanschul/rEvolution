package br.com.caelum.revolution.builds;

import br.com.caelum.revolution.scm.SCM;


public interface Build {

	BuildResult build(String commitId, SCM scm) throws BuildException;

}
