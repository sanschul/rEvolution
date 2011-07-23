package br.com.caelum.revolution.scm;

import java.util.List;

import br.com.caelum.revolution.scm.changesets.ChangeSet;
import br.com.caelum.revolution.scm.changesets.ChangeSetCollection;

public class FixedListChangeSet implements ChangeSetCollection {

	private final List<ChangeSet> cs;

	public FixedListChangeSet(List<ChangeSet> cs) {
		this.cs = cs;
	}
	
	public List<ChangeSet> get() {
		return cs;
	}

}
