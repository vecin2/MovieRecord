package testUtils;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Vector;

import javax.swing.ListModel;

import org.netbeans.jemmy.operators.JListOperator;

import src.core.Movie;
import src.core.MovieList;
import src.core.exceptions.UnratedMovieException;

public class MoviesAssert {
	public static void assertEqualMovieCollection(List<Movie> horrorMovies,
			List<Movie> captureMovies) throws UnratedMovieException {
		assertEquals("Sizes are different", horrorMovies.size(),
				captureMovies.size());
		for (int i = 0; i < horrorMovies.size(); i++) {
			assertEquals("Name failing on movie " + i, horrorMovies.get(i)
					.getName(), captureMovies.get(i).getName());
			assertEquals("Category failing on movie " + i, horrorMovies.get(i)
					.getCategory(), captureMovies.get(i).getCategory());
			assertEquals("Rating failing on movie " + i, horrorMovies.get(i)
					.getRating(), captureMovies.get(i).getRating());
		}
	}

	public static void assertEqualListModel(Vector<Movie> movies,
			JListOperator movieListOperator) throws UnratedMovieException {
		ListModel<Movie> listModel = movieListOperator.getModel();
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
}
