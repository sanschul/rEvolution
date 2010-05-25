package edu.usp.ime.revolution.scm.git;

import java.util.List;

public interface GitLogParser {
	List<String> parse(String log);
}
