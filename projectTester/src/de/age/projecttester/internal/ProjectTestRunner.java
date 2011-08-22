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
		String[] testclassNames = project.getTestclassNames();
		if (testclassNames != null) {
			for (String className : testclassNames) {
				junit.runClass(className);
			}
		}
		Project[] dependantProjects = project.getDependantProjects();
		if (dependantProjects != null) {
			for (Project depProject : dependantProjects) {
				testProject(depProject);
			}
		}
	}
}
