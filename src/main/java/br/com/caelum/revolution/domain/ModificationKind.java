package br.com.caelum.revolution.domain;

public enum ModificationKind {

	NEW("new file mode"), 
	DELETED("deleted file mode"), 
	DEFAULT("nothing");
	
	private final String pattern;

	ModificationKind(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}
	
}
