package br.com.caelum.revolution.scm.git;

import java.util.List;

import br.com.caelum.revolution.executor.CommandExecutor;
import br.com.caelum.revolution.scm.CommitData;
import br.com.caelum.revolution.scm.DiffData;
import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.scm.SCMException;
import br.com.caelum.revolution.scm.ThreadableSCM;
import br.com.caelum.revolution.scm.changesets.ChangeSet;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class Git implements SCM, ThreadableSCM {

	private final String repository;
	private final GitLogParser logParser;
	private final CommandExecutor exec;
	private final GitDiffParser diffParser;
	private final GitBlameParser blameParser;
	private String repositoryNumber;

	public Git(String repository, GitLogParser logParser, GitDiffParser diffParser, GitBlameParser blameParser, CommandExecutor exec) {
		this.repository = repository;
		this.logParser = logParser;
		this.diffParser = diffParser;
		this.blameParser = blameParser;
		this.exec = exec;
		repositoryNumber = "";
	}

	public String goTo(String id) {
		try {
			exec.execute("git checkout master -f", getRepoPath());
			exec.execute("git branch --no-track -f revolution " + id, getRepoPath());
			exec.execute("git checkout revolution ", getRepoPath());
		} catch (Exception e) {
			throw new SCMException(e);
		}

		return repository;
	}

	private String getRepoPath() {
		return repository + repositoryNumber;
	}

	public List<ChangeSet> getChangeSets() {
		try {
			exec.execute("git checkout master -f", getRepoPath());
			String output = exec.execute("git log --format=medium --date=iso --reverse", getRepoPath());
			return logParser.parse(output);
		} catch (Exception e) {
			throw new SCMException(e);
		}
	}
	
	public String sourceOf(String id, String fileName) {
		return exec.execute("git show " + id + ":" + fileName, repository);
	}

	public CommitData detail(String id) {
		try {
			String response = exec.execute("git show "
					+ id
					+ " --pretty=format:<Commit><commitId>%H</commitId><author><![CDATA[%an]]</author><email>%ae</email><date>%ai</date><message><![CDATA[%s]]></message></Commit>", getRepoPath());
			XStream xs = new XStream(new DomDriver());
			xs.alias("Commit", CommitData.class);

			CommitData parsedCommit = (CommitData) xs.fromXML(response.substring(0,
					response.indexOf("</Commit>") + 9));
			parsedCommit.setDiff(response.substring(response.indexOf("</Commit>") + 9));
			
			String priorCommit = exec.execute("git log " + id + "^1 --pretty=format:%H -n 1", getRepoPath());
			parsedCommit.setPriorCommit(priorCommit.trim());
			
			for(DiffData diffData : diffParser.parse(parsedCommit.getDiff())) {
				parsedCommit.addDiff(diffData);
			}
			
			return parsedCommit;
		} catch (Exception e) {
			throw new SCMException(e);
		}
	}
	
	public String blameCurrent(String file, int line) {
		String response = exec.execute("git blame " + file + " -L " + line + "," + line + " -l", getRepoPath());
		return blameParser.getHash(response);
	}

	public String getPath() {
		return repository;
	}

	public void setRepositoryNumber(int number) {
		this.repositoryNumber = number + "/";
	}
}
