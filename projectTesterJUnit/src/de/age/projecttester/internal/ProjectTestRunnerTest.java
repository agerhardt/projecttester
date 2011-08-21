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
		JUnit junitMock = EasyMock.createMock(JUnit.class);
	}

}
