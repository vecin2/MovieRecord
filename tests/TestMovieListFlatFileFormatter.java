package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListFileFormatter;
import src.core.MovieListFlatFileFormatter;
import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;
import testUtils.MoviesAssert;

public class TestMovieListFlatFileFormatter {
	MovieListFileFormatter formatter;
	private MovieList movieList;

	@Before
	public void setup() {
		formatter = new MovieListFlatFileFormatter();

	}

	@Test
	public void testEmptyListReturnsBlank() {
		movieList = new MovieList();
		assertEquals("", formatter.fileFormat(movieList));
	}

	@Test
	public void testOneMovieReturnsOneMovieFormat()
			throws DuplicateMovieException {
		movieList = new MovieList();
		movieList.add(new Movie("Braveheart", Category.HORROR, 5));
		assertEquals("Braveheart|" + Category.HORROR + "|5\n",
				formatter.fileFormat(movieList));
	}

	@Test
	public void testMultipleMoviesReturnsMovieFormatPerLine()
			throws DuplicateMovieException {
		movieList = new MovieList();
		movieList.add(new Movie("Braveheart", Category.HORROR, 5));
		movieList.add(new Movie("Starwars", Category.SCIFI, 4));

		String expectedText = "Braveheart|" + Category.HORROR + "|5\n";
		expectedText += "Starwars|" + Category.SCIFI + "|4\n";
		assertEquals(expectedText, formatter.fileFormat(movieList));
	}

	@Test
	public void testReadEmptyReturnsNewMovieList()
			throws DuplicateMovieException, NumberFormatException,
			InvalidFileFormatException {
		MovieList expectedMovies = new MovieList();
		String formattedMoviesText[] = {};

		movieList = formatter.toMoviesList(formattedMoviesText);

		MoviesAssert.assertEqualMovieCollection(expectedMovies, movieList);
	}

	@Test
	public void testReadMovieLinesArrayReturnsMovieList()
			throws DuplicateMovieException, NumberFormatException,
			InvalidFileFormatException {
		MovieList expectedMovies = new MovieList();
		expectedMovies.add(new Movie("Braveheart", Category.HORROR, 5));
		expectedMovies.add(new Movie("Starwars", Category.SCIFI, 4));
		String formattedMoviesText[] = {
				"Braveheart|" + Category.HORROR + "|5",
				"Starwars|" + Category.SCIFI + "|4" };

		movieList = formatter.toMoviesList(formattedMoviesText);

		MoviesAssert.assertEqualMovieCollection(expectedMovies, movieList);
	}

	@Test(expected = InvalidFileFormatException.class)
	public void testInvalidNumberOfAttributesThrowsInvalidFileFormatException()
			throws DuplicateMovieException, NumberFormatException,
			InvalidFileFormatException {
		String formattedMoviesText[] = { "Braveheart|" + Category.HORROR };
		movieList = formatter.toMoviesList(formattedMoviesText);
	}

	@Test(expected = NumberFormatException.class)
	public void testNoRatingNumberThrowsNumberFormatException()
			throws DuplicateMovieException, NumberFormatException,
			InvalidFileFormatException {
		String formattedMoviesText[] = { "Braveheart|" + Category.HORROR + "|"
				+ "a" };
		movieList = formatter.toMoviesList(formattedMoviesText);
	}

	@Test(expected = DuplicateMovieException.class)
	public void testDuplicateMovieThrowsDuplicateMovieException()
			throws DuplicateMovieException, NumberFormatException,
			InvalidFileFormatException {
		String formattedMoviesText[] = {
				"Braveheart|" + Category.HORROR + "|5",
				"Braveheart|" + Category.SCIFI + "|1" };
		movieList = formatter.toMoviesList(formattedMoviesText);
	}

	@Test
	public void testSplitLineSeparatesMovieFields() {
		String formattedMovieText = "Braveheart|" + Category.HORROR + "|5";
		String movieArray[] = formattedMovieText.split("\\|");
		assertEquals(3, movieArray.length);
		assertEquals("Braveheart", movieArray[0]);
		assertEquals(Category.HORROR.toString(), movieArray[1]);
		assertEquals("5", movieArray[2]);
	}

}
