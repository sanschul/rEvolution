package edu.usp.ime.revolution.scm.git;

import java.util.List;

import edu.usp.ime.revolution.domain.Artifact;

public interface GitDiffParser {
	List<Artifact> parse(String diff);
}
