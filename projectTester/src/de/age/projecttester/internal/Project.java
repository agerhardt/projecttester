package de.age.projecttester.internal;

public interface Project {

	String[] getTestableClassnames();

	Project[] getDependantProjects();
	
}
