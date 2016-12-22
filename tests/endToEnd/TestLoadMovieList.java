package tests.endToEnd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListEditor;
import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.UnratedMovieException;

public class TestLoadMovieList extends TestPersistance {
	private FileWriter fileWriter;

	@Override
	@Before
	public void setUp() throws IOException, DuplicateMovieException {
		super.setUp();
		fileWriter = new FileWriter(outputFile);
		fileWriter.write(fileContent);
		fileWriter.flush();
		fileWriter.close();
		appRunner.setDisplayMovies(new Vector<Movie>());
	}

	@Test
	public void testLoad() throws UnratedMovieException {
		MovieListEditor editor = new MovieListEditor(new MovieList(),
				appRunner.getView());
		appRunner.open(outputFile);
		appRunner.assertMoviesDisplayedEqualTo(movieList);
	}

	@Test
	public void testWhenCancellingLoadMoviesDisplayedDontChange()
			throws IOException, UnratedMovieException {
		MovieListEditor editor = new MovieListEditor(new MovieList(),
				appRunner.getView());
		appRunner.openAndCancel();
		appRunner.assertMoviesDisplayedEqualTo(new MovieList());
	}

	@Test
	public void testWhenLoadMoviesWithWrongFormatDisplaysAnErrorMessage()
			throws IOException, UnratedMovieException {
		FileWriter fileWriter = new FileWriter(outputFile);
		fileWriter.write("Braveheart|Horror|5|some Other Stuff\n");
		fileWriter.flush();
		fileWriter.close();
		MovieListEditor editor = new MovieListEditor(new MovieList(),
				appRunner.getView());
		appRunner.setDisplayMovies(new Vector<Movie>());
		appRunner.open(outputFile);
		appRunner
				.assertDisplaysDialogWithText(
						"Wrong file format",
						"Wrong file format. Please review the format and try again.");
		appRunner.clickOkInDialog();
		appRunner.assertMoviesSize(0);
	}

}
