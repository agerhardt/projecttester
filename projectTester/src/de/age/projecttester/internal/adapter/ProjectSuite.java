package de.age.projecttester.internal.adapter;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import de.age.projecttester.internal.Project;

public class ProjectSuite extends Suite {

	private Project project;

	public ProjectSuite(Project project, Class<?>... classes) throws InitializationError {
		super((Class<?>) null, classes);
		this.project = project;
	}

	@Override
	public Description getDescription() {
		Description description = Description.createSuiteDescription(project.getName());
		if (getChildren() != null) {
			for (Runner child : getChildren()) {
				description.addChild(describeChild(child));
			}
		}
		return description;
	}

}
