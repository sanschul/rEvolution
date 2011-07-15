package edu.usp.ime.revolution.postaction;

import edu.usp.ime.revolution.domain.Commit;

public interface PostAction {
	void notify(Commit commit);
}
