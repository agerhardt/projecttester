package de.age.projecttester.internal;

import org.easymock.EasyMock;
import org.junit.Test;

public class ConfigurableProjectTest {
	
	@Test(expected=NullPointerException.class)
	public void constructorNullProjectArgumentThrowsException() {
		Configuration config = EasyMock.createMock(Configuration.class);
		new ConfigurableProject(null, config);
	}
	
	public void constructorNullConfigArgumentThrowsException() {
		Project project = EasyMock.createMock(Project.class);
		new ConfigurableProject(project, null);
	}
	
}
