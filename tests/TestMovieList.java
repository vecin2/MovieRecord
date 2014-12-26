package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import src.core.Movie;
import src.core.MovieList;
import src.core.exceptions.DuplicateMovieException;

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

}
