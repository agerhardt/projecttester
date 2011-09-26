package de.age.projecttester.internal;

import org.junit.runner.Result;

public interface JUnit {

	public void addTestClass(Project project, String className);
	
	public Result runTestClasses();
	
}
