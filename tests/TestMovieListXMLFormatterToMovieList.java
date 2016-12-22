package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListXMLFormatter;
import src.core.Rating;
import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;
import testUtils.MoviesAssert;

public class TestMovieListXMLFormatterToMovieList {
	private String starTrekXML;
	private String starWarsXML;
	private Movie starTrekExpectedMovie;
	private Movie starWarsExpectedMovie;

	@Before
	public void setup() {
		starTrekXML = "<movie name =\"Star Trek\"" + " category =\"Horror\">"
				+ "<ratings>" + "<rating value =\"4\""
				+ " source=\"NY Times\"/>" + "<rating value =\"2\""
				+ " source=\"El Pais\"/>" + "</ratings>" + "</movie>";
		starWarsXML = "<movie name =\"Star Wars\"" + " category =\"Horror\">"
				+ "<ratings>" + "<rating value =\"5\""
				+ " source=\"El Mundo\"/>" + "</ratings>" + "</movie>";
		starTrekExpectedMovie = new Movie("Star Trek", Category.HORROR,
				new Rating(4, "NY Times"));
		starTrekExpectedMovie.addRating(new Rating(2, "El Pais"));
		starWarsExpectedMovie = new Movie("Star Wars", Category.HORROR,
				new Rating(5, "El Mundo"));
	}

	@Test
	public void testReadingEmptyList() throws NumberFormatException,
			DuplicateMovieException, InvalidFileFormatException {
		String emptyListXML = "<movieList></movielist>";
		MovieListXMLFormatter formatter = new MovieListXMLFormatter();
		MovieList movieList = formatter.toMoviesList(emptyListXML);
		assertTrue("Lista should be empty", movieList.size() == 0);
	}

	// Test 95: Reading an appropriate XML stream containing a single movie
	// definition
	// results in a list containing the single movie defined in the stream
	@Test
	public void testReadingOneMovieList() throws NumberFormatException,
			DuplicateMovieException, InvalidFileFormatException {
		MovieList expectedMovieList = new MovieList();
		expectedMovieList.add(new Movie("Star Trek", Category.HORROR,
				new Rating(4, "NY Times")));

		MovieListXMLFormatter formatter = new MovieListXMLFormatter();
		MovieList movieList = formatter.toMoviesList("<movieList>"
				+ starTrekXML + "</movieList>");
		assertTrue("List should contain one movie", movieList.size() == 1);
		MoviesAssert.assertEqualMovieCollection(expectedMovieList, movieList);
	}

	@Test
	public void testReadingOneMovieListWithMultipleRatings()
			throws NumberFormatException, DuplicateMovieException,
			InvalidFileFormatException {
		MovieList expectedMovieList = new MovieList();
		expectedMovieList.add(starTrekExpectedMovie);

		MovieListXMLFormatter formatter = new MovieListXMLFormatter();
		MovieList movieList = formatter.toMoviesList("<movielist>"
				+ starTrekXML +"</movielist>");
		assertTrue("List should contain one movie", movieList.size() == 1);
		MoviesAssert.assertEqualMovieCollection(expectedMovieList, movieList);
	}

	@Test
	public void testReadingMultipleMovieList() throws NumberFormatException,
			DuplicateMovieException, InvalidFileFormatException {
		
		MovieList expectedMovieList = new MovieList();
		expectedMovieList.add(starTrekExpectedMovie);
		expectedMovieList.add(starWarsExpectedMovie);
		

		MovieListXMLFormatter formatter = new MovieListXMLFormatter();
		MovieList movieList = formatter.toMoviesList("<movielist>"
				+ starTrekXML + starWarsXML + "</movielist>");
		assertTrue("List should contain one movie", movieList.size() == 2);
		MoviesAssert.assertEqualMovieCollection(expectedMovieList, movieList);

	}
}
