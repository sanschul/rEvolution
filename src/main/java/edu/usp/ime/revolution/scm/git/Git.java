package edu.usp.ime.revolution.scm.git;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.SCMException;
import edu.usp.ime.revolution.scm.changesets.ChangeSet;

public class Git implements SCM {

	private final String repository;
	private final GitLogParser logParser;
	private final CommandExecutor exec;

	public Git(String repository, GitLogParser logParser, CommandExecutor exec) {
		this.repository = repository;
		this.logParser = logParser;
		this.exec = exec;
	}

	public String goTo(ChangeSet cs) {
		try {
			exec.setWorkingDirectory(repository);
			exec.runCommand("git checkout master");
			exec.runCommand("git branch --no-track -f revolution " + cs.getId());
			exec.runCommand("git checkout revolution ");
		} catch (Exception e) {
			throw new SCMException(e);
		}

		return repository;
	}

	public List<ChangeSet> getChangeSets() {
		try {
			exec.setWorkingDirectory(repository);
			exec.runCommand("git log --format=medium --date=iso");

			String output = exec.getCommandOutput();
			return logParser.parse(output);
		} catch (Exception e) {
			throw new SCMException(e);
		}
	}

	public Commit detail(String id) {
		try {
			exec.setWorkingDirectory(repository);
			exec.runCommand("git show "
					+ id
					+ " --pretty=format:<Commit><id>%H</id><author>%an</author><email>%ae</email><date>%ai</date><message>%s</message></Commit>");
			XStream xs = new XStream(new DomDriver());
			xs.alias("Commit", Commit.class);

			String response = exec.getCommandOutput();
			Commit parsedCommit = (Commit) xs.fromXML(response.substring(0,
					response.indexOf("</Commit>") + 9));
			parsedCommit.setDiff(response.substring(response.indexOf("</Commit>") + 9));
			
			return parsedCommit;
		} catch (Exception e) {
			throw new SCMException(e);
		}
	}
}
