package de.age.projecttester.internal.adapter;

import org.eclipse.jdt.core.IJavaProject;

import de.age.projecttester.internal.Project;

/**
 * Adapter to wrap an eclipse-project to the needs of the plugin.
 */
public class ProjectAdapter implements Project {

	private IJavaProject project;
	
	public ProjectAdapter(IJavaProject project) {
		super();
		this.project = project;
	}

	@Override
	public String[] getTestableClassnames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project[] getDependantProjects() {
		// TODO Auto-generated method stub
		return null;
	}

}
