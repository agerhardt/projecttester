package de.age.projecttester.internal.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;

import de.age.projecttester.internal.Project;
import example.ExampleJUnit4Class;
import example.OnlySuccessfulTests;

public class JUnitAdapterTest {

	private JUnitAdapter adapter;

	@Before
	public void setUp() {
		adapter = new JUnitAdapter();
	}

	@After
	public void tearDown() {
		adapter = null;
	}

	@Test(expected = IllegalStateException.class)
	public void runningWithoutSessionThrowsException() {
		adapter.runTestClasses();
	}

	@Test
	public void runningNoTestsProducesEmptyResult() {
		adapter.startSession();
		Result result = adapter.runTestClasses();
		assertThat(result, is(notNullValue()));
		assertThat(result.wasSuccessful(), is(true));
	}

	@Test
	public void oneClassSuccessfulTestsOnly() {
		adapter.startSession();
		Project project = EasyMock.createMock(Project.class);
		EasyMock.expect(project.getName()).andStubReturn("exampleProject");
		EasyMock.replay(project);
		adapter.addTestClass(project, OnlySuccessfulTests.class.getName());
		Result result = adapter.runTestClasses();
		assertThat(result, is(notNullValue()));
		assertThat(result.wasSuccessful(), is(true));
	}

	@Test
	public void oneClassFailure() {
		adapter.startSession();
		Project project = EasyMock.createMock(Project.class);
		EasyMock.expect(project.getName()).andStubReturn("exampleProject");
		EasyMock.replay(project);
		adapter.addTestClass(project, ExampleJUnit4Class.class.getName());
		Result result = adapter.runTestClasses();
		assertThat(result, is(notNullValue()));
		assertThat(result.wasSuccessful(), is(false));
	}

	@Test
	public void multipleClassFailure() {
		adapter.startSession();
		Project project = EasyMock.createMock(Project.class);
		EasyMock.expect(project.getName()).andStubReturn("exampleProject");
		EasyMock.replay(project);
		adapter.addTestClass(project, ExampleJUnit4Class.class.getName());
		adapter.addTestClass(project, OnlySuccessfulTests.class.getName());
		Result result = adapter.runTestClasses();
		assertThat(result, is(notNullValue()));
		assertThat(result.wasSuccessful(), is(false));
	}

}
