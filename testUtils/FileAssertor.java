package testUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.custommonkey.xmlunit.XMLTestCase;

public class FileAssertor extends XMLTestCase {

	public static void assertEqualFile(String expectedText, File outputFile) {

		assertEquals(expectedText, getFileContent(outputFile));

	}

	public static String getFileContent(File outputFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(outputFile));
			String line = br.readLine();
			StringBuilder result = new StringBuilder();
			while (line != null) {
				result.append(line);
				result.append(System.getProperty("line.separator"));
				line = br.readLine();
			}
			return result.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
