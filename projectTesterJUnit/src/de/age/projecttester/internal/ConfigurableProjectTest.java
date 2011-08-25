package de.age.projecttester.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;

public class ConfigurableProjectTest {
	
	@Test(expected=NullPointerException.class)
	public void constructorNullProjectArgumentThrowsException() {
		Configuration config = EasyMock.createMock(Configuration.class);
		new ConfigurableProject(null, config);
	}
	
	@Test(expected=NullPointerException.class)
	public void constructorNullConfigArgumentThrowsException() {
		Project project = EasyMock.createMock(Project.class);
		new ConfigurableProject(project, null);
	}
	
	@Test
	public void appliesFilterToClassNames() {
		TestClassFilter filter = new NameRegexFilter(".*Test");
		Configuration config = EasyMock.createMock(Configuration.class);
		Project project = EasyMock.createMock(Project.class);
		EasyMock.expect(config.getTestClassFilter()).andStubReturn(filter);
		EasyMock.expect(project.getTestableClassnames()).andStubReturn(new String[] { "SomeTest", "SomeNonTestClass" } );
		EasyMock.expect(project.getDependantProjects()).andStubReturn(new Project[0]);
		
		EasyMock.replay(config, project);
		
		ConfigurableProject configProject = new ConfigurableProject(project, config);
		assertThat(configProject.getTestableClassnames(), is(equalTo(new String[] { "SomeTest" })));
	}

}
