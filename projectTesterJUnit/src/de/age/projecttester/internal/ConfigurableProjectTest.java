package de.age.projecttester.internal;

import org.junit.Test;

public class ConfigurableProjectTest {
	
	@Test(expected=NullPointerException.class)
	public void constructorNullArgumentThrowsException() {
		new ConfigurableProject(null);
	}
}
