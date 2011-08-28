package de.age.projecttester.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NameRegexFilterTest {
	
	@Test(expected = NullPointerException.class)
	public void constructorNullArgumentThrowsException() {
		new NameRegexFilter(null);
	}
	
	@Test
	public void acceptExactMatch() {
		NameRegexFilter filter = new NameRegexFilter("abcde");
		assertThat(filter.accept("abcde"), equalTo(true));
		assertThat(filter.accept("bcd"), equalTo(false));
	}
	
	@Test
	public void acceptWildcard() {
		NameRegexFilter filter = new NameRegexFilter(".*Test");
		assertThat(filter.accept("SomeTest"), equalTo(true));
		assertThat(filter.accept("SomeTestOrOther"), equalTo(false));
	}

}
