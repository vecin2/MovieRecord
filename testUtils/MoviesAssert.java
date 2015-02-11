package testUtils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ListModel;

import org.netbeans.jemmy.operators.JListOperator;

import src.core.Movie;
import src.core.MovieList;
import src.core.Rating;
import src.core.exceptions.UnratedMovieException;

public class MoviesAssert {
	public static void assertEqualMovieCollection(Collection<Movie> expected,
			Collection<Movie> actual) throws UnratedMovieException {
		assertEquals("Sizes are different", expected.size(),
				actual.size());
		Iterator<Movie> expectedIterator = expected.iterator();
		Iterator<Movie> actualIterator = actual.iterator();
		int i=0;
		while(expectedIterator.hasNext()){
			Movie actualMovie = actualIterator.next();
			Movie expectedMovie = expectedIterator.next();
			assertEquals("Name failing on movie " + i, expectedMovie
					.getName(), actualMovie.getName());
			assertEquals("Category failing on movie " + i, expectedMovie
					.getCategory(), actualMovie.getCategory());
			assertEquals("Rating failing on movie " + i, expectedMovie
					.getRating(), actualMovie.getRating());
			i++;
			
		}
	}

	public static void assertEqualListModel(Vector<Movie> movies,
			JListOperator movieListOperator) throws UnratedMovieException {
		ListModel<Movie> listModel = movieListOperator.getModel();
		assertEquals("Number of movies displayed is different from",movies.size(), listModel.getSize());
		for (int i = 0; i < movies.size(); i++) {
			assertEquals("Movie list contains bad movie at index " + i,
					movies.get(i), listModel.getElementAt(i));
			assertEquals("Category failing at element " + i, movies.get(i)
					.getCategory(), listModel.getElementAt(i).getCategory());
			assertEquals("Rating failing at element " + i, movies.get(i)
					.getRating(), listModel.getElementAt(i).getRating());
		}
	}

	public static void assertEqualMovieCollection(MovieList expectedMovies,
			MovieList actualMovies) {
		try {
			assertEqualMovieCollection(expectedMovies.getMovies(),
					actualMovies.getMovies());
		} catch (UnratedMovieException e) {
			e.printStackTrace();
		}
	}

	public static void assertRatingsEqualListModel(ArrayList<Rating> ratings,
			ListModel<Rating> listModel) {
		for (int i = 0; i < ratings.size(); i++) {
			assertEquals("Fail at element "+i,ratings.get(i),listModel.getElementAt(i));
		}
		
	}
}
