package de.age.projecttester.internal;

import java.util.ArrayList;

/**
 * Decorates an underlying project to filter the classes. 
 */
public class ConfigurableProject implements Project {

	private Project wrappedProject;
	private Configuration configuration;
	
	public ConfigurableProject(Project project, Configuration config) {
		if (project == null) {
			throw new NullPointerException("Project must not be null");
		}
		if (config == null) {
			throw new NullPointerException("Configuration must not be null");
		}
		this.wrappedProject = project;
		this.configuration = config;
	}

	@Override
	public String[] getTestableClassnames() {
		ArrayList<String> filteredClassnameList = new ArrayList<String>();
		for (String classname : wrappedProject.getTestableClassnames()) {
			if (configuration.getTestClassFilter().accept(classname)) {
				filteredClassnameList.add(classname);
			}
		}
		return filteredClassnameList.toArray(new String[filteredClassnameList.size()]);
	}

	@Override
	public Project[] getDependantProjects() {
		return null;
	}

}
