package tests.endToEnd;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import src.core.Movie;
import src.core.exceptions.UnratedMovieException;

public class TestSortMovieList extends TestSettingupView{

	@Override
@Before
public void setUp() throws Exception {
	super.setUp();

}
	@Test
	public void testWhenSortByNameListIsSorted() throws UnratedMovieException {
		Vector<Movie>  nameOrderedMovies = new Vector<Movie>();
		nameOrderedMovies.add(starTrek);
		nameOrderedMovies.add(starWars);
		nameOrderedMovies.add(stargate);
		appRunner.orderByName();
		appRunner.assertMoviesDisplayedEqualTo(nameOrderedMovies);
	}
	@Test
	public void testWhenSortByRatingListIsSorted() throws UnratedMovieException {
		Vector<Movie>  ratingOrderedMovies = new Vector<Movie>();
		ratingOrderedMovies.add(stargate);
		ratingOrderedMovies.add(starTrek);
		ratingOrderedMovies.add(starWars);
		appRunner.orderByRating();
		appRunner.assertMoviesDisplayedEqualTo(ratingOrderedMovies);
	}

}
