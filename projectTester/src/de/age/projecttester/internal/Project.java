package de.age.projecttester.internal;

public interface Project {

	String[] getTestclassNames();

	Project[] getDependantProjects();
	
}
