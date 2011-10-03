package de.age.projecttester.popup.actions;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.age.projecttester.internal.Project;
import de.age.projecttester.internal.ProjectTestRunner;
import de.age.projecttester.internal.adapter.JUnitAdapter;
import de.age.projecttester.internal.adapter.ProjectAdapter;

public class TestProjectAction implements IObjectActionDelegate {

	private Shell shell;
	private Project selectedProject = null;
	
	/**
	 * Constructor for Action1.
	 */
	public TestProjectAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (selectedProject != null) {
			ProjectTestRunner runner = new ProjectTestRunner(new JUnitAdapter());
			runner.testProject(selectedProject);
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		selectedProject = null;
		if (selection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement != null && firstElement instanceof IJavaProject) {
				selectedProject = new ProjectAdapter((IJavaProject) firstElement);
			}
		}
	}

}
