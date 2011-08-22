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
		
		JUnit junitMock = EasyMock.createMock(JUnit.class);
		junitMock.runClass(className1);
		junitMock.runClass(className2);
		EasyMock.replay(junitMock, project);
		
		ProjectTestRunner runner = new ProjectTestRunner(junitMock);
		runner.testProject(project);
		
		EasyMock.verify(junitMock);
	}

}
