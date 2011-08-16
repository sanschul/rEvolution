package br.com.caelum.revolution.builds.ant;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.BuildException;
import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.scm.SCM;

public class AutomatedAnt implements Build {

	private final String srcDir;
	private final String destDir;

	private Project currentProject = new Project();
	private final String[] classpaths;

	public AutomatedAnt(String srcDir, String destDir, String[] classpaths) {
		this.srcDir = srcDir;
		this.destDir = destDir;
		this.classpaths = classpaths;

	}

	class ExtendedJavaC extends Javac {
		@SuppressWarnings("deprecation")
		public ExtendedJavaC() {
			project = currentProject;
			project.init();
			taskType = "javac";
			taskName = "javac";
			target = new Target();
		}
	}

	public BuildResult build(String commitId, SCM scm) throws BuildException {
		scm.goTo(commitId);

		ExtendedJavaC javac = new ExtendedJavaC();
		javac.setSrcdir(new Path(currentProject, srcDir));
		javac.setDestdir(new File(destDir));
		javac.setIncludeDestClasses(true);
		javac.setIncludejavaruntime(true);

		Path path = new Path(currentProject);
		
		for(String classpath : classpaths){
			FileSet fs = new FileSet();
			fs.setDir(new File(classpath));
			path.addFileset(fs);
		}
		
		javac.setClasspath(path);

		javac.execute();

		return new BuildResult(destDir);
	}

}
