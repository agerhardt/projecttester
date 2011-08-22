package de.age.projecttester.internal;

import org.easymock.EasyMock;
import org.junit.Test;

public class ProjectTestRunnerTest {
	
	@Test(expected=NullPointerException.class)
	public void constructorNullRunner() {
		new ProjectTestRunner(null);
	}
	
	@Test
	public void testProjectRunsTests() {
		String className1 = "className1";
		String className2 = "className2";
		
		Project project = EasyMock.createMock(Project.class);
		EasyMock.expect(project.getTestclassNames()).andReturn(new String[] { className1, className2} );
		EasyMock.expect(project.getDependantProjects()).andReturn(new Project[0]);
		
		JUnit junitMock = EasyMock.createMock(JUnit.class);
		junitMock.runClass(className1);
		junitMock.runClass(className2);
		EasyMock.replay(junitMock, project);
		
		ProjectTestRunner runner = new ProjectTestRunner(junitMock);
		runner.testProject(project);
		
		EasyMock.verify(junitMock);
	}
	
	@Test
	public void testProjectRunsTestsInDependantProject() {
		String className1 = "className1";
		String dependantClassName = "depClassName2";
		
		Project mainProject = EasyMock.createMock(Project.class);
		Project depProject = EasyMock.createMock(Project.class);
		
		EasyMock.expect(depProject.getTestclassNames()).andReturn(new String[] { dependantClassName });
		EasyMock.expect(depProject.getDependantProjects()).andReturn(new Project[0]);
		EasyMock.expect(mainProject.getTestclassNames()).andReturn(new String[] { className1 });
		EasyMock.expect(mainProject.getDependantProjects()).andReturn(new Project[] { depProject });
		
		JUnit junitMock = EasyMock.createMock(JUnit.class);
		junitMock.runClass(className1);
		junitMock.runClass(dependantClassName);
		EasyMock.replay(junitMock, mainProject, depProject);
		
		ProjectTestRunner runner = new ProjectTestRunner(junitMock);
		runner.testProject(mainProject);
		
		EasyMock.verify(junitMock);
	}

}
