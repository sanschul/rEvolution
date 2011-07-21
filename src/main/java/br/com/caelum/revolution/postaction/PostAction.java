package br.com.caelum.revolution.postaction;

import br.com.caelum.revolution.domain.Commit;

public interface PostAction {
	void notify(Commit commit);
}
