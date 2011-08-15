package br.com.caelum.revolution.tools;

public class ToolNotFoundException extends RuntimeException {

	public ToolNotFoundException(String tool) {
		super(tool);
	}

	private static final long serialVersionUID = 1L;

}
