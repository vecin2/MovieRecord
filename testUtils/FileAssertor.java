package testUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class FileAssertor {

	public static void assertEqualFile(String expectedText, File outputFile) {
		try {
			Scanner scanner = new Scanner(outputFile);
			BufferedReader br =new BufferedReader(new FileReader(outputFile));
			String result= br.readLine();
			assertEquals(expectedText, result);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
