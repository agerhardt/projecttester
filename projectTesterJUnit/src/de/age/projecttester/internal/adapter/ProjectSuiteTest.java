package de.age.projecttester.internal.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

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
		
		RunnerBuilder builder = EasyMock.createMock(RunnerBuilder.class);
		
		ProjectSuite suite = new ProjectSuite(project, builder, new Class<?>[0]);
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
		
		AllDefaultPossibilitiesBuilder runnerBuilder = new AllDefaultPossibilitiesBuilder(true);
		
		ProjectSuite suite = new ProjectSuite(project, runnerBuilder, new Class<?>[] { Class.forName(className1) });
		assertThat(suite.getDescription().getChildren().size(), is(1));
	}
}
