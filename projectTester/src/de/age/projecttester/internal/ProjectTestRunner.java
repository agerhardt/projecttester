package de.age.projecttester.internal;

import java.util.HashSet;
import java.util.Set;

public class ProjectTestRunner {

	private JUnit junit;

	public ProjectTestRunner(JUnit junit) {
		if (junit == null) {
			throw new NullPointerException("JUnit-interface must not be null");
		}
		this.junit = junit;
	}

	public void testProject(Project project) {
		Set<Project> testProjects = new HashSet<Project>();

		Set<Project> uncheckedProjects = new HashSet<Project>();
		uncheckedProjects.add(project);

		while (!uncheckedProjects.isEmpty()) {
			Project nextProject = uncheckedProjects.iterator().next();
			uncheckedProjects.remove(nextProject);
			testProjects.add(nextProject);
			Project[] depProjects = nextProject.getDependantProjects();
			if (depProjects != null) {
				for (Project p : depProjects) {
					if (!testProjects.contains(p)) {
						uncheckedProjects.add(p);
					}
				}
			}
		}

		for (Project p : testProjects) {
			String[] testclassNames = p.getTestclassNames();
			if (testclassNames != null) {
				for (String className : testclassNames) {
					junit.runClass(className);
				}
			}
		}
	}
}
