package de.age.projecttester.internal.adapter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.junit.JUnitCore;

import de.age.projecttester.internal.Project;

/**
 * Adapter to wrap an eclipse-project to the needs of the plugin.
 */
public class ProjectAdapter implements Project {

	private IJavaProject project;
	
	public ProjectAdapter(IJavaProject project) {
		super();
		if (project == null) {
			throw new NullPointerException("Wrapped project must not be <null>.");
		}
		this.project = project;
	}

	@Override
	public String getName() {
		return project.getElementName();
	}

	@Override
	public String[] getTestableClassnames() {
		try {
			IType[] testTypes = JUnitCore.findTestTypes(project, null);
			String[] result = new String[testTypes.length];
			for (int i = 0; i < testTypes.length; i++) {
				result[i] = testTypes[i].getFullyQualifiedName();
			}
			return result;
		} catch (CoreException e) {
			// TODO
			return null;
		}
	}

	@Override
	public Project[] getDependantProjects() {
		// TODO Auto-generated method stub
		return null;
	}

}
