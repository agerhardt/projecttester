package de.age.projecttester.internal.adapter;

import org.junit.runner.Computer;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class WrappingComputer extends Computer {

	@Override
	public Runner getSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
		return super.getSuite(builder, classes);
	}

	@Override
	protected Runner getRunner(RunnerBuilder builder, Class<?> testClass) throws Throwable {
		return new Suite(builder, wrap(testClass));
	}
	
	private static Class<?>[] wrap(Class<?> ... testClasses) {
		return testClasses;
	}

}
