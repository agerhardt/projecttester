package de.age.projecttester.internal.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runners.model.InitializationError;

import de.age.projecttester.internal.JUnit;
import de.age.projecttester.internal.Project;
import de.age.projecttester.internal.ProjectTestRunner;

/**
 * TODO consider joining this and {@link ProjectTestRunner}, this class is in a way reversing some of the work that {@link ProjectTestRunner} is doing.
 */
public class JUnitAdapter implements JUnit {

	private JUnitCore core;
	private Map<Project, List<Class<?>>> testClasses;

	public JUnitAdapter() {
		core = null;
		testClasses = new HashMap<Project, List<Class<?>>>();
	}

	public void startSession() {
		core = new JUnitCore();
		testClasses.clear();
	}

	@Override
	public void addTestClass(Project project, String className) {
		List<Class<?>> classList = testClasses.get(project);
		if (classList == null) {
			classList = new ArrayList<Class<?>>();
			testClasses.put(project, classList);
		}
		try {
			classList.add(Class.forName(className));
		} catch (ClassNotFoundException e) {
			// TODO meaningful exception
			throw new RuntimeException();
		}
	}

	@Override
	public Result runTestClasses() {
		if (core == null) {
			throw new IllegalStateException("Start the session before running tests.");
		}
		ProjectSuite[] projectSuites = new ProjectSuite[testClasses.size()];
		int index = 0;
		for (Map.Entry<Project, List<Class<?>>> entry : testClasses.entrySet()) {
			projectSuites[index++] = createProjectSuite(entry.getKey(), entry.getValue());
		}
		try {
			MultiProjectSuite wrapperSuite = new MultiProjectSuite(projectSuites);
			return core.run(Request.runner(wrapperSuite));
		} catch (InitializationError e) {
			// TODO meaningful exception
			throw new RuntimeException();
		}
	}

	private ProjectSuite createProjectSuite(Project project, List<Class<?>> classes) {
		try {
			if (classes == null) {
				return new ProjectSuite(project);
			} else {
				return new ProjectSuite(project, classes.toArray(new Class<?>[classes.size()]));
			}
		} catch (InitializationError e) {
			// TODO meaningful exception
			throw new RuntimeException();
		}
	}

}
