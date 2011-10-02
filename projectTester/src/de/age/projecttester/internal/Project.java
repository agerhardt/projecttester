package de.age.projecttester.internal;

public interface Project {

	String getName();
	
	String[] getTestableClassnames();

	Project[] getDependantProjects();

	Class<?> loadClass(String className);

}
