package de.age.projecttester.internal;

/**
 * Decorates an underlying project to filter the classes. 
 */
public class ConfigurableProject implements Project {

	public ConfigurableProject(Project project) {
		if (project == null) {
			throw new NullPointerException("Project must not be null");
		}
	}

	@Override
	public String[] getTestableClassnames() {
		return null;
	}

	@Override
	public Project[] getDependantProjects() {
		return null;
	}

}
