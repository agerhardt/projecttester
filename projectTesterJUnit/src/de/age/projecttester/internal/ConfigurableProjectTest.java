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
		String testedClassname = "SomeTest";
		EasyMock.expect(project.getTestableClassnames()).andStubReturn(new String[] { testedClassname, "SomeNonTestClass" } );
		
		EasyMock.replay(config, project);
		
		ConfigurableProject configProject = new ConfigurableProject(project, config);
		assertThat(configProject.getTestableClassnames(), is(equalTo(new String[] { testedClassname })));
	}
	
	@Test
	public void unfilteredProjectsAndClassnames() {
		TestClassFilter filter = new TestClassFilter() {
			@Override
			public boolean accept(String testclassName) {
				return true;
			}
		};
		
		Configuration config = EasyMock.createMock(Configuration.class);
		Project project = EasyMock.createMock(Project.class);
		Project depProject1 = EasyMock.createMock(Project.class);
		Project depProject2 = EasyMock.createMock(Project.class);
		EasyMock.expect(config.getTestClassFilter()).andStubReturn(filter);
		String[] testableClassnames = new String[] { "SomeTest", "SomeNonTestClass" };
		EasyMock.expect(project.getTestableClassnames()).andStubReturn(testableClassnames);
		Project[] dependantProjects = new Project[] { depProject1, depProject2 };
		EasyMock.expect(project.getDependantProjects()).andStubReturn(dependantProjects);
		
		EasyMock.replay(config, project);
		
		ConfigurableProject configProject = new ConfigurableProject(project, config);
		assertThat(configProject.getTestableClassnames(), is(equalTo(testableClassnames)));
		assertThat(configProject.getDependantProjects(), is(equalTo(dependantProjects)));
	}

}
