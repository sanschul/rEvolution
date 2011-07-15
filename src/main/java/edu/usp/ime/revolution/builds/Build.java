package edu.usp.ime.revolution.builds;

import edu.usp.ime.revolution.scm.changesets.ChangeSet;

public interface Build {

	BuildResult build(String path) throws BuildException;

}
