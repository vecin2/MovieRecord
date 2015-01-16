package testUtils;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileAssertor {

	public static void assertEqualFile(String expectedText, File outputFile) {
		try {
			BufferedReader br =new BufferedReader(new FileReader(outputFile));
			String line= br.readLine();
			StringBuilder result=new StringBuilder();
			while(line!=null){
				result.append(line);
				result.append(System.getProperty("line.separator"));
				line = br.readLine();
			}
			assertEquals(expectedText, result.toString());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
