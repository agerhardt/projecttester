package de.age.projecttester.matcher;

import org.hamcrest.Matcher;

public class AssertMatchers {
	
	public static <T extends Comparable<T>>  Matcher<T> greaterThan(T expectedValue) {
		return new GreaterThan<T>(expectedValue);
	}

}
