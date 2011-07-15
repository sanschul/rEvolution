package edu.usp.ime.revolution.scm.git;

import java.util.List;

import edu.usp.ime.revolution.scm.ChangeSet;

public interface GitLogParser {
	List<ChangeSet> parse(String log) throws Exception;
}