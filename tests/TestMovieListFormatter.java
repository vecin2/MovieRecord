package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListFormatter;
import src.core.exceptions.DuplicateMovieException;

public class TestMovieListFormatter {
	MovieListFormatter formatter;
	private MovieList movieList;
@Before
public void setup(){
	formatter =new MovieListFormatter();
	movieList = new MovieList();
}
	@Test
	public void testEmptyListReturnsBlank() {
		assertEquals("", formatter.fileFormat(movieList));
	}
	@Test
	public void testOneMovieReturnsOneMovieFormat() throws DuplicateMovieException {
		movieList.add(new Movie("Braveheart",Category.HORROR,5));
		assertEquals("Braveheart|"+Category.HORROR+"|5\n", formatter.fileFormat(movieList));
	}
	@Test
	public void testMultipleMoviesReturnsMovieFormatPerLine() throws DuplicateMovieException {
		movieList.add(new Movie("Braveheart",Category.HORROR,5));
		movieList.add(new Movie("Starwars",Category.SCIFI,4));
		
		String expectedText = "Braveheart|"+Category.HORROR+"|5\n";
		expectedText+="Starwars|"+Category.SCIFI+"|4\n";
		assertEquals(expectedText, formatter.fileFormat(movieList));
	}
}
