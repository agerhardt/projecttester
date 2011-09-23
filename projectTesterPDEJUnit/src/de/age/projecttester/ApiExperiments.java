package de.age.projecttester;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApiExperiments {

	@BeforeClass
	public static void initializeWorkspace() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject("exampleProject");
		BlockingProgressMonitor monitor = new BlockingProgressMonitor();
		if (!project.exists()) {
			project.create(monitor);
			monitor.blockUntilDone();
		}
		if (!project.isOpen()) {
			project.open(monitor);
			monitor.blockUntilDone();
		}
		workspace.build(IncrementalProjectBuilder.FULL_BUILD , monitor);
	}
	
	@Test
	public void howToGetWorkspace() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		assertThat(workspace, is(notNullValue()));
	}

	@Test
	public void howToGetProjects() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final ArrayList<IProject> projects = new ArrayList<IProject>();
		workspace.getRoot().accept(new IResourceVisitor() {

			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IProject) {
					projects.add((IProject) resource);
					return false;
				} else {
					return true;
				}
			}
		});
		assertThat(projects.isEmpty(), is(false));
	}

	@Test
	public void howToGetOnlyJavaProjects() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final ArrayList<IProject> projects = new ArrayList<IProject>();
		workspace.getRoot().accept(new IResourceVisitor() {

			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IProject) {
					if (((IProject) resource).hasNature(JavaCore.NATURE_ID))
						projects.add((IProject) resource);
					return false;
				} else {
					return true;
				}
			}
		});
		assertThat(projects.size(), is(1));
	}

	@Test
	public void howToGetClasses() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final ArrayList<IJavaElement> javaElements = new ArrayList<IJavaElement>();
		workspace.getRoot().accept(new IResourceVisitor() {

			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IProject) {
					if (((IProject) resource).hasNature(JavaCore.NATURE_ID)) {
						return true;
					} else {
						return false;
					}
				} else if (resource instanceof IFile) {
					IJavaProject project = JavaCore.create((IProject) resource.getProject());
					javaElements.add(project.findElement(resource.getProjectRelativePath()));
					return false;
				} else {
					return true;
				}
			}
		});
		assertThat(javaElements.isEmpty(), is(false));
	}

	@Test
	public void howToFindTestCases() throws OperationCanceledException, CoreException {
		IJavaProject javaProject = getFirstJavaProject();
		IType[] testTypes = org.eclipse.jdt.junit.JUnitCore.findTestTypes(javaProject, null);
		assertThat(testTypes.length > 0, is(true));
	}

	private IJavaProject getFirstJavaProject() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final ArrayList<IProject> projects = new ArrayList<IProject>();
		workspace.getRoot().accept(new IResourceVisitor() {

			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IProject) {
					if (((IProject) resource).hasNature(JavaCore.NATURE_ID))
						projects.add((IProject) resource);
					return false;
				} else {
					return true;
				}
			}
		});
		IJavaProject javaProject = JavaCore.create(projects.get(0));
		return javaProject;
	}

	@Test
	public void howToRunTestCases() throws CoreException, ClassNotFoundException {
		TrackingTestRunListener listener = new TrackingTestRunListener();
		JUnitCore.addTestRunListener(listener);
		IJavaProject javaProject = getFirstJavaProject();
		IType[] testTypes = org.eclipse.jdt.junit.JUnitCore.findTestTypes(javaProject, null);
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType launchConfig = launchManager
				.getLaunchConfigurationType("org.eclipse.jdt.junit.launchconfig");
		ILaunchConfigurationWorkingCopy workingCopy = launchConfig.newInstance(null, "name");
		workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, javaProject.getElementName());
		workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME,
				testTypes[0].getFullyQualifiedName());
		workingCopy.setAttribute("org.eclipse.jdt.junit.TEST_KIND", "org.eclipse.jdt.junit.loader.junit4");
		ILaunchConfiguration config = workingCopy.doSave();
		BlockingProgressMonitor monitor = new BlockingProgressMonitor();
		config.launch(ILaunchManager.RUN_MODE, monitor);
		monitor.blockUntilDone();

		// hack: monitor gets called before the listeners
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		
		assertThat(listener.numberOfTestsRun(), is(1));
	}

	private final class TrackingTestRunListener extends TestRunListener {

		private List<ITestCaseElement> startedCases = new ArrayList<ITestCaseElement>();

		@Override
		public void testCaseStarted(ITestCaseElement testCaseElement) {
			System.out.println(testCaseElement.toString());
			startedCases.add(testCaseElement);
		}

		public int numberOfTestsRun() {
			return startedCases.size();
		}
	}

}
