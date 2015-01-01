package testUtils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import src.core.Movie;
import src.core.exceptions.UnratedMovieException;

public class MoviesAssert {
	public static void assertEqualMovieCollection(List<Movie> horrorMovies,
			List<Movie> captureMovies) throws UnratedMovieException {
		assertEquals("Sizes are different", horrorMovies.size(), captureMovies.size());
		for (int i = 0; i < horrorMovies.size(); i++) {
			assertEquals("Name failing on movie "+i,horrorMovies.get(i).getName(), captureMovies.get(i)
					.getName());
			assertEquals("Category failing on movie "+i,horrorMovies.get(i).getCategory(), captureMovies
					.get(i).getCategory());
			assertEquals("Rating failing on movie "+i,horrorMovies.get(i).getRating(), captureMovies.get(i)
					.getRating());
		}
	}
}
