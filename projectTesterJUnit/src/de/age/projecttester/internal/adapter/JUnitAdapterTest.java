package de.age.projecttester.internal.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;

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

}
