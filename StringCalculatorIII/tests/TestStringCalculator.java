package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import src.StringCalculator;


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
