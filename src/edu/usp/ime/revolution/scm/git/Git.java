package edu.usp.ime.revolution.scm.git;

import java.util.LinkedList;
import java.util.List;

import edu.nyu.cs.javagit.api.DotGit;
import edu.nyu.cs.javagit.api.commands.GitLogResponse.Commit;
import edu.usp.ime.revolution.changeset.ChangeSet;
import edu.usp.ime.revolution.exceptions.SCMException;
import edu.usp.ime.revolution.scm.SCM;

public class Git implements SCM {

	private final String repository;

	public Git(String repository) {
		this.repository = repository;
	}

	public ChangeSet getChangeSet(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getChangeSetList() throws SCMException {
		List<String> changeSets = new LinkedList<String>();
		try {
			DotGit git = DotGit.getInstance(repository);
			for(Commit c : git.getLog()) {
				changeSets.add(c.getSha());
			}
		} catch (Exception e) {
			throw new SCMException(e);
		}
		
		return changeSets;
	}

}
