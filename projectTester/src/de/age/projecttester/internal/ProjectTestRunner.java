package de.age.projecttester.internal;

public class ProjectTestRunner {

	private JUnit junit;
	
	public ProjectTestRunner(JUnit junit) {
		if (junit == null) {
			throw new NullPointerException("JUnit-interface must not be null");
		}
		this.junit = junit;
	}

	public void testProject(Project project) {
		for (String className : project.getTestclassNames()) {
			junit.runClass(className);
		}
		for (Project depProject : project.getDependantProjects()) {
			testProject(depProject);
		}
	}
}
