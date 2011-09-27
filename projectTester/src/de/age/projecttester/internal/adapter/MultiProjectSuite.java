package de.age.projecttester.internal.adapter;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class MultiProjectSuite extends Suite {

	public MultiProjectSuite(ProjectSuite... projectSuites) throws InitializationError {
		super((Class<?>) null, asRunnerList(projectSuites));
	}

	private static List<Runner> asRunnerList(ProjectSuite... projectSuites) {
		List<Runner> runners = new ArrayList<Runner>();
		if (projectSuites != null) {
			for (ProjectSuite suite : projectSuites) {
				runners.add(suite);
			}
		}
		return runners;
	}

}
