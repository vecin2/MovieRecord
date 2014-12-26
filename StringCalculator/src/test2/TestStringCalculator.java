package test2;

import static org.junit.Assert.*;
import StringCalculator;

import org.junit.Test;

public class TestStringCalculator {

	@Test
	public void testWhenAddingEmptyStringReturnsZero() {
		fail("Not yet implemented");
		
		//setup
		StringCalculator stringCalculator = new StringCalculator();
		//test
		int result = stringCalculator.add("");
		//assert
		assertEquals(0, result);
	}

}
