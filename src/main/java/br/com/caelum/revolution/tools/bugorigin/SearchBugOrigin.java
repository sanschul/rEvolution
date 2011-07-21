package br.com.caelum.revolution.tools.bugorigin;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.persistence.ToolThatPersists;
import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.scm.ToolThatUsesSCM;
import br.com.caelum.revolution.tools.Tool;
import br.com.caelum.revolution.tools.ToolException;


public class SearchBugOrigin implements Tool, ToolThatPersists, ToolThatUsesSCM {

	private static Logger log = LoggerFactory.getLogger(SearchBugOrigin.class);
	private SCM scm;
	private Session session;
	private Set<String> commitsAlreadyAdded;
	private final String[] keywords;

	public SearchBugOrigin(String[] keywords) {
		this.keywords = keywords;
	}

	public void setSCM(SCM scm) {
		this.scm = scm;
	}

	public Class<?>[] classesToPersist() {
		return new Class<?>[] { BugOrigin.class };
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void calculate(Commit commit, BuildResult current)
			throws ToolException {
		commitsAlreadyAdded = new HashSet<String>();
		
		if(!noKeywordsIn(commit)) return;
		
		
		for (Artifact artifact : commit.getArtifacts()) {
			log.info("Looking for bugs in artifact "+ artifact.getName()  + "(commit " + commit.getCommitId()+")");
			String[] lines = artifact.getDiff().replace("\r", "").split("\n");

			int currentLine = 0;
			for (int i = 0; i < lines.length; i++) {
				currentLine++;
				if (itRepresentsALineNumber(lines[i])) {
					currentLine = Integer.parseInt(lines[i].substring(4, lines[i].indexOf(",")));
				}
				else if (itRepresentsCodeThatWasRemoved(lines[i])) {
					String hash = scm.blameCurrent(artifact.getName(), currentLine);
					log.info("Bugged hash for line "+ currentLine + ": " + hash);
					
					if (!commitsAlreadyAdded.contains(hash)) {
						
						Commit buggedCommit = (Commit) session
								.createCriteria(Commit.class)
								.add(Restrictions.eq("commitId", hash))
								.uniqueResult();

						BugOrigin origin = new BugOrigin();
						origin.setBuggedCommit(buggedCommit);
						origin.setFixedArtifact(artifact);
						session.save(origin);

						commitsAlreadyAdded.add(hash);
					}
				}
			}
		}
	}

	private boolean itRepresentsALineNumber(String line) {
		return line.startsWith("@@");
	}

	private boolean noKeywordsIn(Commit commit) {
		for (String keyword : keywords) {
			if(commit.getMessage().contains(keyword)) return true;
		}
		return false;
	}

	private boolean itRepresentsCodeThatWasRemoved(String line) {
		return line.startsWith("-");
	}

	public String getName() {
		return "search-bug-origin";
	}

}
