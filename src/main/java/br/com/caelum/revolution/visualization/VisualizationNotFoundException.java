package br.com.caelum.revolution.visualization;

public class VisualizationNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public VisualizationNotFoundException(Exception e) {
		super(e);
	}

}
