package tests.endToEnd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.core.Category;
import src.core.Movie;
import src.core.Rating;
import src.core.exceptions.UnratedMovieException;
import testUtils.FileAssertor;

public class TestSaveEndToEnd extends TestSettingupView {
	File outputFile;
	String fileText = "";

	// Movie List:
	// starWars = new Movie("Star Wars", Category.SCIFI, 5);
	// starTrek = new Movie("Star Trek", Category.SCIFI, 4);
	// stargate = new Movie("Stargate", Category.HORROR, 3);

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fileText += "Star Wars|" + Category.SCIFI + "|3\n";
		fileText += "Star Trek|" + Category.SCIFI + "|4\n";
		fileText += "Stargate|" + Category.HORROR + "|5\n";
		outputFile = File.createTempFile("testSaveAs", ".dat");
		outputFile.deleteOnExit();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();

	}

	@Test
	public void testSave() throws IOException {
		appRunner.saveAs(outputFile);
		FileAssertor.assertEqualFile(fileText, outputFile);
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		ratings.add(new Rating(5,"frank"));
		appRunner.addMovie("Braveheart", Category.HORROR, ratings);
		appRunner.save();

		String extendedExpectedText = fileText + "Braveheart|Horror|5\n";
		FileAssertor.assertEqualFile(extendedExpectedText, outputFile);
	}

	@Test
	public void testLoad() throws IOException, UnratedMovieException {
		FileWriter fileWriter = new FileWriter(outputFile);
		fileWriter.write(fileText);
		fileWriter.flush();
		fileWriter.close();
		appRunner.setDisplayMovies(new Vector<Movie>());
		appRunner.open(outputFile);
		appRunner.assertMoviesDisplayedEqualTo(movies);
	}
	@Test
	public void testWhenCancellingLoadMoviesDisplayedDontChange() throws IOException, UnratedMovieException {
		FileWriter fileWriter = new FileWriter(outputFile);
		fileWriter.write(fileText);
		fileWriter.flush();
		fileWriter.close();
		appRunner.setDisplayMovies(new Vector<Movie>());
		appRunner.openAndCancel();
		appRunner.assertMoviesDisplayedEqualTo(new Vector<Movie>());
	}
	@Test
	public void testWhenLoadMoviesWithWrongFormatDisplaysAnErrorMessage() throws IOException, UnratedMovieException {
		FileWriter fileWriter = new FileWriter(outputFile);
		fileWriter.write("Braveheart|Horror|5|some Other Stuff\n");
		fileWriter.flush();
		fileWriter.close();
		appRunner.setDisplayMovies(new Vector<Movie>());
		appRunner.open(outputFile);
		appRunner.assertDisplaysDialogWithText("Wrong file format", "Wrong file format. Correct format is: Braveheart|HORROR|5. Please review the format and try again.");
		appRunner.clickOkInDialog();
		appRunner.assertMoviesSize(0);
	}

}
