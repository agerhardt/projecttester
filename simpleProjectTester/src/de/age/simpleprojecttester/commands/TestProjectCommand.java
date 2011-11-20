package de.age.simpleprojecttester.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class TestProjectCommand extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IProject selectedProject = getSelectedProject();
		try {
			ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
			ILaunchConfigurationType launchConfig = launchManager.getLaunchConfigurationType("org.eclipse.jdt.junit.launchconfig");
			ILaunchConfigurationWorkingCopy workingCopy = launchConfig.newInstance(null, "Test " + selectedProject.getName());
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, selectedProject.getName());
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "testclassname");
			workingCopy.setAttribute("org.eclipse.jdt.junit.TEST_KIND", "org.eclipse.jdt.junit.loader.junit4");
			ILaunchConfiguration config = workingCopy.doSave();
			config.launch(ILaunchManager.RUN_MODE, null);
		} catch (CoreException exc) {
			throw new ExecutionException("Error executing test", exc);
		}
		return null;
	}
	
	private IProject getSelectedProject() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return null;
		} else {
			ISelectionService selectionService = activeWorkbenchWindow.getSelectionService();
			ISelection selection = selectionService.getSelection();
			if (selection instanceof TextSelection) {
				// must be an editor
				IEditorPart activeEditor = activeWorkbenchWindow.getActivePage().getActiveEditor();
				IEditorInput editorInput = activeEditor.getEditorInput();
				IPathEditorInput pathInput = (IPathEditorInput) editorInput;
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IFile editorFile = workspace.getRoot().getFile(pathInput.getPath());
				return editorFile.getProject();
			} else if (selection instanceof StructuredSelection) {
			}
			return null;
		}
	}

}
