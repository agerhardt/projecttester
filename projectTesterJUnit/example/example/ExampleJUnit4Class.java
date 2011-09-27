package example;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * DO NOT USE, JUST FOR TESTING PURPOSES.
 */
public class ExampleJUnit4Class {

	@Test
	public void successful() {
	}
	
	@Test
	public void failing() {
		fail();
	}
	
	@Test
	public void exception() {
		throw new RuntimeException();
	}
}
