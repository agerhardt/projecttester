package de.age.projecttester.internal.adapter;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.junit.JUnitCore;

import de.age.projecttester.internal.Project;

/**
 * Adapter to wrap an eclipse-project to the needs of the plugin.
 */
public class ProjectAdapter implements Project {

	private IJavaProject project;
	private ClassLoader binClassLoader;
	
	public ProjectAdapter(IJavaProject project) {
		super();
		if (project == null) {
			throw new NullPointerException("Wrapped project must not be <null>.");
		}
		this.project = project;
		IPath outputLocation;
		try {
			outputLocation = project.getOutputLocation();
		} catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
		URI output = outputLocation.toFile().toURI();
		try {
			binClassLoader = new URLClassLoader(new URL[] { output.toURL() } );
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
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

	@Override
	public Class<?> loadClass(String className) {
		try {
			return binClassLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
