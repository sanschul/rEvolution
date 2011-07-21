package edu.usp.ime.revolution.tools.files;

import java.io.File;

import org.hibernate.Session;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.persistence.ToolThatPersists;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.ToolThatUsesSCM;
import edu.usp.ime.revolution.tools.Tool;
import edu.usp.ime.revolution.tools.ToolException;

public class NumberOfFiles implements Tool, ToolThatPersists, ToolThatUsesSCM {

	private String extension;
	private Session session;
	private SCM scm;

	public NumberOfFiles(String extension) {
		this.extension = extension;
	}

	public void calculate(Commit commit, BuildResult current) throws ToolException {
		try {
			NumberOfFilesPerCommit result = new NumberOfFilesPerCommit();
			result.setCommit(commit);
			result.setQty(countFiles(scm.getPath()));
			
			session.save(result);
		}
		catch(Exception e) {
			throw new ToolException(e);
		}
	}

	public String getName() {
		return "number-of-files";
	}
	
	private int countFiles(String directory) {
		int qty = 0;
		
		File[] files = new File(directory).listFiles();
		
		for(File file : files) {
			if(file.getName().endsWith(extension)) qty++;
			if(file.isDirectory()) qty += countFiles(file.getPath());
		}
		
		return qty;
	}

	public Class<?>[] classesToPersist() {
		return new Class<?>[] { NumberOfFilesPerCommit.class };
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setSCM(SCM scm) {
		this.scm = scm;
	}
}
