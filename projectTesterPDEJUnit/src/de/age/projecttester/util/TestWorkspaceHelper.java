package de.age.projecttester.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class TestWorkspaceHelper {

	public static final String JAVA_EXAMPLE_PROJECT = "exampleProject";

	public static final void prepareWorkspace() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(JAVA_EXAMPLE_PROJECT);
		BlockingProgressMonitor monitor = new BlockingProgressMonitor();
		if (!project.exists()) {
			project.create(monitor);
			monitor.blockUntilDone();
		}
		assertThat(project.exists(), is(true));
		if (!project.isOpen()) {
			project.open(monitor);
			monitor.blockUntilDone();
		}
		workspace.build(IncrementalProjectBuilder.FULL_BUILD , monitor);
	}
}
