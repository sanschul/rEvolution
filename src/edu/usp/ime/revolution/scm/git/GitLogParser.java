package edu.usp.ime.revolution.scm.git;

import java.util.List;

import edu.usp.ime.revolution.scm.ChangeSetInfo;

public interface GitLogParser {
	List<ChangeSetInfo> parse(String log) throws Exception;
}
