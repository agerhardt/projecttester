package de.age.projecttester.internal.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.junit.BeforeClass;
import org.junit.Test;

import de.age.projecttester.util.TestWorkspaceHelper;

public class ProjectAdapterTest {

	@BeforeClass
	public static void initializeWorkspace() throws CoreException {
		TestWorkspaceHelper.prepareWorkspace();
	}
	
	@Test(expected=NullPointerException.class)
	public void wrappedProjectMustNotBeNull() {
		new ProjectAdapter(null);
	}
	
	@Test
	public void constructorAcceptsNonNullProject() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(TestWorkspaceHelper.JAVA_EXAMPLE_PROJECT);
		IJavaProject javaProject = JavaCore.create(project);
		new ProjectAdapter(javaProject);
	}
	
	@Test
	public void adapterFindsSomeTestableClassesInExampleProject() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(TestWorkspaceHelper.JAVA_EXAMPLE_PROJECT);
		IJavaProject javaProject = JavaCore.create(project);
		ProjectAdapter projectAdapter = new ProjectAdapter(javaProject);
		assertThat(projectAdapter.getTestableClassnames().length, is(1));
	}

}
