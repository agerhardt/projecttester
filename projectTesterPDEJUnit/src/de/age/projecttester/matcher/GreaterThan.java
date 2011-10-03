package de.age.projecttester.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class GreaterThan<T extends Comparable<T>> extends BaseMatcher<T> {

	private final T expectedValue;
	
	protected GreaterThan(T expectedValue) {
		if (expectedValue == null) {
			throw new NullPointerException();
		}
		this.expectedValue = expectedValue;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("greater than ");
		description.appendValue(expectedValue);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean matches(Object item) {
		if (expectedValue.getClass().isInstance(item)) {
			return expectedValue.compareTo((T) item) < 0;
		}
		return false;
	}

}
