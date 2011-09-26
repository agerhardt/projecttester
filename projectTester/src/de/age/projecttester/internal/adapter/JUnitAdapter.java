package de.age.projecttester.internal.adapter;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import de.age.projecttester.internal.JUnit;
import de.age.projecttester.internal.Project;

public class JUnitAdapter implements JUnit {

	private JUnitCore core;
	
	public JUnitAdapter() {
		core = null;
	}
	
	public void startSession() {
		core = new JUnitCore();
	}
	
	@Override
	public void addTestClass(Project project, String className) {
	}

	@Override
	public Result runTestClasses() {
		return null;
	}
	
}
