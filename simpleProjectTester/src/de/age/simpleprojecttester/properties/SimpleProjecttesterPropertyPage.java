package de.age.simpleprojecttester.properties;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.dialogs.FilteredTypesSelectionDialog;
import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

@SuppressWarnings("restriction")
public class SimpleProjecttesterPropertyPage extends PropertyPage {

	private static final String TEST_CLASS_TITLE = "Testclass";
	private static final String BROWSE_CLASS_TITLE = "Browse";
	private static final String TEST_CLASS_PROPERTY = "simpleprojecttester.testclass";
	private static final int TEXT_FIELD_WIDTH = 50;

	private Text testClassText;
	private Button browseClassButton;

	private void addSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		Label testClassLabel = new Label(composite, SWT.NONE);
		testClassLabel.setText(TEST_CLASS_TITLE);

		testClassText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		gd.horizontalAlignment = GridData.FILL;
		testClassText.setLayoutData(gd);
		browseClassButton = new Button(composite, SWT.NONE);
		browseClassButton.setText(BROWSE_CLASS_TITLE);
		gd = new GridData();
		browseClassButton.setLayoutData(gd);
		browseClassButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FilteredTypesSelectionDialog searchTypes = new FilteredTypesSelectionDialog(getShell(), false, null,
						createTestCaseSearchScope(), IJavaSearchConstants.CLASS_AND_ENUM);
				searchTypes.setBlockOnOpen(true);
				int result = searchTypes.open();
				if (result == FilteredTypesSelectionDialog.OK) {
					IType firstResult = (IType) searchTypes.getFirstResult();
					testClassText.setText(firstResult.getFullyQualifiedName());
				}
			}

		});

		try {
			String owner = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", TEST_CLASS_PROPERTY));
			testClassText.setText((owner != null) ? owner : "");
		} catch (CoreException e) {
			testClassText.setText("");
		}
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addSection(composite);
		return composite;
	}

	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	protected void performDefaults() {
		super.performDefaults();
		testClassText.setText("");
	}

	public boolean performOk() {
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName("", TEST_CLASS_PROPERTY),
					testClassText.getText());
		} catch (CoreException e) {
			return false;
		}
		return true;
	}
	
	private IJavaSearchScope createTestCaseSearchScope() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IJavaModel javaModel = JavaCore.create(workspace.getRoot());
		try {
			IJavaProject[] javaProjects = javaModel.getJavaProjects();
			ArrayList<IType> testTypes = new ArrayList<IType>();
			for (IJavaProject project : javaProjects) {
				IType[] projectTestTypes = JUnitCore.findTestTypes(project, null);
				Collections.addAll(testTypes, projectTestTypes);
			}
			return SearchEngine.createJavaSearchScope(testTypes.toArray(new IType[testTypes.size()]));
		} catch (OperationCanceledException e) {
			// TODO
			throw new RuntimeException();
		} catch (CoreException e) {
			// TODO
			throw new RuntimeException();
		}
	}

}