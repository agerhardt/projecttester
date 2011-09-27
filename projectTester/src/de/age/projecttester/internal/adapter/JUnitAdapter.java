package de.age.projecttester.internal.adapter;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runners.model.InitializationError;

import de.age.projecttester.internal.JUnit;
import de.age.projecttester.internal.Project;

public class JUnitAdapter implements JUnit {

	private JUnitCore core;
	private Project project;
	private String className;
	
	public JUnitAdapter() {
		core = null;
	}
	
	public void startSession() {
		core = new JUnitCore();
	}
	
	@Override
	public void addTestClass(Project project, String className) {
		this.project = project;
		this.className = className;
	}

	@Override
	public Result runTestClasses() {
		if (core == null) {
			throw new IllegalStateException("Start the session before running tests.");
		}
		try {
			if (className != null) {
				Class<?> clazz = Class.forName(className);
				ProjectSuite suite = new ProjectSuite(project, clazz);
				return core.run(Request.runner(suite));
			} else {
				return core.run();
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException();
		} catch (InitializationError e) {
			throw new RuntimeException();
		}
	}
	
}
