package de.age.projecttester.internal;

import java.util.regex.Pattern;

public class NameRegexFilter implements TestClassFilter {

	private Pattern pattern;
	
	public NameRegexFilter(String regex) {
		if (regex == null) {
			throw new NullPointerException("Regex must not be null");
		}
		pattern = Pattern.compile(regex);
	}

	@Override
	public boolean accept(String testclassName) {
		return pattern.matcher(testclassName).matches();
	}
	
}
