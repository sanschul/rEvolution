package br.com.caelum.revolution.scm;

import java.util.List;

import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.scm.changesets.ChangeSet;


public interface SCM {
	List<ChangeSet> getChangeSets();
	String goTo(ChangeSet cs);
	Commit detail(String id);
	String sourceOf(String hash, String fileName);
	
	String getPath();
	String blameCurrent(String file, int line);
}
