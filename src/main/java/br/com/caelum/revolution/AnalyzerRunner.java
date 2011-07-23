package br.com.caelum.revolution;

import java.util.List;


public interface AnalyzerRunner {

	public abstract void start();

	public abstract List<Error> getErrors();

}