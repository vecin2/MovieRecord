package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import src.core.Category;
import src.core.Movie;
import src.core.exceptions.UnratedMovieException;

public class TestMovie {
	Movie movie;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Test
	public void testRename() {
		movie =new Movie("Star Trek",1);
		movie.rename("Indiana Jones");
		assertEquals("Indiana Jones", movie.getName());
	}
	@Test
	public void testMovieCantBeConstructedWithANullName(){
		thrown.expect(IllegalArgumentException.class);
		movie = new Movie(null);		 
	}
	@Test
	public void testMovieCantBeConstructedWithAEmptyName(){
		thrown.expect(IllegalArgumentException.class);
		movie = new Movie("");		 
	}
	@Test
	public void testMovieCantBeRenamedWithANullName(){
		thrown.expect(IllegalArgumentException.class);
		movie = new Movie("Start Trek");
		movie.rename(null);
	}
	@Test
	public void testMovieCantBeRenamedWithAEmptyName(){
		thrown.expect(IllegalArgumentException.class);
		movie = new Movie("Start Trek");
		movie.rename("");
	}
	/*Test 24. An unrated movie answers negative when asked if it is rated.*/
	@Test
	public void testIsRatedReturnsNoWhenMovieIsUnRated(){
		movie = new Movie("Star trek");
		assertEquals("Movie should be unrated",false, movie.isRated());
	}
	/*Test 25. A rated movie answers positive when asked if it is rated, and it can answer its rating when asked.*/
	@Test
	public void testIsRatedReturnsPositiveAndRatingWhenMovieIsRated() throws UnratedMovieException{
		movie = new Movie("Starwars",5);
		assertEquals(true, movie.isRated());
		assertEquals(5, movie.getRating());
	}
	/*Test 26. Asking an unrated movie for its rating throws an exception.*/
	@Test(expected=UnratedMovieException.class)
	public void testGetRateThrowsAnExceptionWhenMovieUnrate() throws UnratedMovieException{
		movie = new Movie("Stargate");
		movie.getRating();
	}
	@Test
	public void testGetCategoryReturnsUncategorizedIfNotSet(){
		movie =new Movie("Stargate");
		assertEquals(Category.UNCATEGORIZED, movie.getCategory());
	}
}
