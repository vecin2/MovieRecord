package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListFormatter;
import src.core.exceptions.DuplicateMovieException;
import testUtils.FileAssertor;
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

	@Test(expected=DuplicateMovieException.class)
	public void testWhenRenameMovieNameEndsInDuplicatedThrowsDuplicatedException()
			throws DuplicateMovieException {
		MovieList movieList = MovieList.create("Titanic");
		Movie braveHeart = new Movie("Braveheart");
		movieList.add(braveHeart);
		movieList.rename(braveHeart, "Titanic");
	}
	@Test
	public void testFilterByCategory() throws DuplicateMovieException{
		MovieList movieList = new MovieList();
		movieList.add(new Movie("Braveheart",Category.HORROR,5));
		movieList.add(new Movie("Starwars",Category.SCIFI,4));
		movieList.add(new Movie("Stargate",Category.HORROR,5));
		
		MovieList filteredMovieList = movieList.filterBy(Category.HORROR);
		
		assertEquals(2, filteredMovieList.size());
		assertEquals("Braveheart", filteredMovieList.get(0).getName());
		assertEquals("Stargate", filteredMovieList.get(1).getName());
	}
	@Test
	public void testWriteToWritesToTheFileTheResultTheFormatterProduces() throws IOException{
		File output = File.createTempFile("output", ".dat");
		output.deleteOnExit();
		MovieList movieList = new MovieList();
		MovieListFormatter movieListFormatter = mock(MovieListFormatter.class);
		when(movieListFormatter.fileFormat(movieList)).thenReturn("mocked file text\n");
		
		movieList.writeTo(output, movieListFormatter);
		
		FileAssertor.assertEqualFile("mocked file text", output);
	}
	

}
