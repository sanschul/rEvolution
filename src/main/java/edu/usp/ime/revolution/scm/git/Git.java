package edu.usp.ime.revolution.scm.git;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.domain.Artifact;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.SCMException;
import edu.usp.ime.revolution.scm.changesets.ChangeSet;

public class Git implements SCM {

	private final String repository;
	private final GitLogParser logParser;
	private final CommandExecutor exec;
	private final GitDiffParser diffParser;

	public Git(String repository, GitLogParser logParser, GitDiffParser diffParser, CommandExecutor exec) {
		this.repository = repository;
		this.logParser = logParser;
		this.diffParser = diffParser;
		this.exec = exec;
	}

	public String goTo(ChangeSet cs) {
		try {
			exec.execute("git checkout master", repository);
			exec.execute("git branch --no-track -f revolution " + cs.getId(), repository);
			exec.execute("git checkout revolution ", repository);
		} catch (Exception e) {
			throw new SCMException(e);
		}

		return repository;
	}

	public List<ChangeSet> getChangeSets() {
		try {
			String output = exec.execute("git log --format=medium --date=iso", repository);

			return logParser.parse(output);
		} catch (Exception e) {
			throw new SCMException(e);
		}
	}

	public Commit detail(String id) {
		try {
			String response = exec.execute("git show "
					+ id
					+ " --pretty=format:<Commit><commitId>%H</commitId><author>%an</author><email>%ae</email><date>%ai</date><message>%s</message></Commit>", repository);
			XStream xs = new XStream(new DomDriver());
			xs.alias("Commit", Commit.class);

			Commit parsedCommit = (Commit) xs.fromXML(response.substring(0,
					response.indexOf("</Commit>") + 9));
			parsedCommit.setDiff(response.substring(response.indexOf("</Commit>") + 9));
			
			for(Artifact artifact : diffParser.parse(parsedCommit.getDiff())) {
				parsedCommit.addArtifact(artifact);
			}
			
			return parsedCommit;
		} catch (Exception e) {
			throw new SCMException(e);
		}
	}
}
