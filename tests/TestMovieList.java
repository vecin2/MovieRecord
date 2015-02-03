package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListFormatter;
import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;
import testUtils.FileAssertor;
import testUtils.MoviesAssert;
import static org.mockito.Mockito.*;

public class TestMovieList {

	/*
	 * Test 18. Attempting to add a duplicate movie throws an exception and
	 * leaves the list unchanged
	 */
	@Test(expected = DuplicateMovieException.class)
	public void test() throws DuplicateMovieException {
		MovieList movieList = new MovieList();
		movieList.add(new Movie("Braveheart"));
		movieList.add(new Movie("Braveheart"));
	}

	/*
	 * Test 19. Asking the movie list to rename a movie results in its name
	 * being changed.
	 */
	@Test
	public void testWhenRenameMovieNameIsChanged()
			throws DuplicateMovieException {
		MovieList movieList = MovieList.create("Titanic");
		Movie braveHeart = new Movie("Braveheart");
		movieList.add(braveHeart);
		movieList.rename(braveHeart, "Braveheart (1995)");
		assertEquals("Braveheart (1995)", movieList.get(1).getName());
	}

	@Test(expected = DuplicateMovieException.class)
	public void testWhenRenameMovieNameEndsInDuplicatedThrowsDuplicatedException()
			throws DuplicateMovieException {
		MovieList movieList = MovieList.create("Titanic");
		Movie braveHeart = new Movie("Braveheart");
		movieList.add(braveHeart);
		movieList.rename(braveHeart, "Titanic");
	}

	@Test
	public void testFilterByCategory() throws DuplicateMovieException {
		MovieList movieList;
		movieList = new MovieList();
		movieList.add(new Movie("Braveheart", Category.HORROR, 5));
		movieList.add(new Movie("Starwars", Category.SCIFI, 4));
		movieList.add(new Movie("Stargate", Category.HORROR, 5));

		MovieList filteredMovieList = movieList.filterBy(Category.HORROR);

		assertEquals(2, filteredMovieList.size());
		assertEquals("Braveheart", filteredMovieList.get(0).getName());
		assertEquals("Stargate", filteredMovieList.get(1).getName());
	}

	@Test
	public void testWriteToWritesToTheFileTheResultTheFormatterProduces()
			throws IOException {
		File output = File.createTempFile("output", ".dat");
		output.deleteOnExit();
		MovieList movieList = new MovieList();
		MovieListFormatter movieListFormatter = mock(MovieListFormatter.class);
		when(movieListFormatter.fileFormat(movieList)).thenReturn(
				"mocked file text\n");

		movieList.writeTo(output, movieListFormatter);

		FileAssertor.assertEqualFile("mocked file text\n", output);
	}

	@Test
	public void testReadFromAFileBuildsAMovieList() throws IOException,
			NumberFormatException, DuplicateMovieException,
			InvalidFileFormatException {
		File input = File.createTempFile("output", ".dat");
		input.deleteOnExit();
		FileWriter fileWriter = new FileWriter(input);
		String fileText = "Star Wars|" + Category.SCIFI + "|5\n";
		fileText += "Star Trek|" + Category.SCIFI + "|4\n";
		fileText += "Stargate|" + Category.HORROR + "|3\n";
		fileWriter.write(fileText);
		fileWriter.flush();
		fileWriter.close();
		MovieListFormatter movieListFormatter = mock(MovieListFormatter.class);
		String formattedMoviesArray[] = { "Star Wars|" + Category.SCIFI + "|5",
				"Star Trek|" + Category.SCIFI + "|4",
				"Stargate|" + Category.HORROR + "|3" };
		// return any list, the important test is to check that the formatter is
		// invoked with the correct array string
		MovieList mockedList = new MovieList();
		when(movieListFormatter.toMoviesList(formattedMoviesArray)).thenReturn(
				mockedList);

		MovieList movieList = MovieList.readFrom(input, movieListFormatter);

		verify(movieListFormatter).toMoviesList(formattedMoviesArray);
		MoviesAssert.assertEqualMovieCollection(mockedList, movieList);
	}

}
