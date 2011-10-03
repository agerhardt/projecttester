package de.age.projecttester.internal.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import de.age.projecttester.internal.Project;

public class ProjectSuiteTest {
	
	@Test
	public void descriptionReturnsNameOfProject() throws InitializationError {
		String projectName = "project1";
		
		Project project = EasyMock.createMock(Project.class);
		EasyMock.expect(project.getTestableClassnames()).andStubReturn(new String[0]);
		EasyMock.expect(project.getDependantProjects()).andStubReturn(new Project[0]);
		EasyMock.expect(project.getName()).andStubReturn(projectName);
		
		EasyMock.replay(project);
		
		ProjectSuite suite = new ProjectSuite(project);
		assertThat(suite.getDescription().getDisplayName(), is(projectName));
	}
	
	@Test
	public void descriptionReturnsTestClasses() throws InitializationError, ClassNotFoundException {
		String projectName = "project1";
		String className1 = "example.ExampleJUnit4Class";
		
		Project project = EasyMock.createMock(Project.class);
		EasyMock.expect(project.getTestableClassnames()).andStubReturn(new String[] { className1 } );
		EasyMock.expect(project.getDependantProjects()).andStubReturn(new Project[0]);
		EasyMock.expect(project.getName()).andStubReturn(projectName);
		
		EasyMock.replay(project);
		
		ProjectSuite suite = new ProjectSuite(project, Class.forName(className1));
		assertThat(suite.getDescription().getChildren().size(), is(1));
	}
}
