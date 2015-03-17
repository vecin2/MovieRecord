package tests.endToEnd;

import java.util.ArrayList;
import java.util.Vector;

import org.junit.Test;

import src.core.Category;
import src.core.Movie;
import src.core.Rating;
import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.UnratedMovieException;

public class TestSwingMovieListEditorView extends TestSettingupView {
	@Test
	public void testListContents() throws UnratedMovieException {
		appRunner.assertMoviesDisplayedEqualTo(movies);

	}

	// Test 8. The GUI should have a field for the movie name and an add button.
	// It should answer the contents of
	// the name field when asked, and request that the logical layer add a movie
	// when the add button is pushed.
	@Test
	public void testAddingMovie() throws UnratedMovieException {
		Movie newMovie = new Movie("New Movie", Category.SCIFI);
		newMovie.addRating(new Rating(2,"Dark Water"));
		newMovie.addRating(new Rating(4, "Dark Water"));
		movies.add(newMovie);
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		ratings.add(new Rating(2,"Dark Water"));
		ratings.add(new Rating(4, "Dark Water"));
		appRunner.addMovie("New Movie", Category.SCIFI,ratings );

		appRunner.assertMoviesDisplayedEqualTo(movies);
	}

	@Test
	public void testAddingRating() throws UnratedMovieException {
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		ratings.add(new Rating(2, "guru1"));
		ratings.add(new Rating(3, "Spielberg"));
		appRunner.addRating(ratings.get(0));
		appRunner.addRating(ratings.get(1));
		
		appRunner.assertRatingDisplayedEqualTo(ratings);
	}

	/*
	 * Test 15: Selecting from the list causes the name field to be filled in
	 * with the selected movie's name Test 33: Selecting a movie from the list
	 * updates the displayed rating
	 */
	@Test
	public void testWhenSelectFromOriginalListFillsMovieDetailsWithTheSelectedMovie() throws UnratedMovieException {
		appRunner.selectMovie(0);
		appRunner.assertMovieDetailsDisplays(starWars);

		appRunner.selectMovie(1);
		appRunner.assertMovieDetailsDisplays(starTrek);

		appRunner.selectMovie(2);
		appRunner.assertMovieDetailsDisplays(stargate);
	}

	@Test
	public void testWhenSelectFromFilterListFillsMovieDetailsWithTheSelectedMovie()
			throws UnratedMovieException {
		appRunner.filterByCategory(Category.HORROR);
		appRunner.selectMovie(0);
		appRunner.assertMovieDetailsDisplays(stargate);
	}

	/*
	 * Test 17: When the update button is pushed, the selected movie is renamed
	 * to whatever is in the name field
	 */
	@Test
	public void testUpdate() throws UnratedMovieException {
		Movie starTrekUpdated = new Movie("Star Trek (updated)",
				Category.HORROR, 2);
		ArrayList<Rating>ratings = new ArrayList<Rating>();
		ratings.add(new Rating(5,"dav"));
		ratings.add(new Rating(3,"raf"));
		appRunner.updateMovie(1, starTrekUpdated);

		appRunner.selectMovie(0);
		appRunner.selectMovie(1);

		appRunner.assertMovieDetailsDisplays(starTrekUpdated);

	}

	@Test
	public void testUpdateWithSameNameShouldNotThrowADuplicatedException()
			throws UnratedMovieException {
		appRunner.selectMovie(1);
		appRunner.selectRating(2);

		appRunner.clickUpdate();

		appRunner.selectMovie(0);
		appRunner.selectMovie(1);

		Movie updatedStarTrek = new Movie("Star Trek", Category.SCIFI, 2);
		appRunner.assertMovieDetailsDisplays(updatedStarTrek);

	}

	/*
	 * Test 22. Trying to add a movie that is the same as one in the list
	 * results in the display of a "Duplicate Movie" error dialog.
	 */
	@Test
	public void testAddingADuplicateMovieDisplaysErrorDialog() {
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		ratings.add(new Rating(2,"goum"));
		appRunner.addMovie(stargate.getName(), Category.HORROR, ratings);
		appRunner.assertDisplaysDialogWithText("Duplicate Movie",
				"Adding this movie will result in a duplicate movie");
		appRunner.clickOkInDialog();
		appRunner.assertMoviesSize(movies.size());
	}

	/*
	 * Test 23: Trying to rename a movie to the name of one in the list results
	 * in the display of a "Duplicate Movie" error dialog
	 */
	@Test
	public void testUpdatingDuplicateMovieDisplaysErroDialog()
			throws UnratedMovieException {
		appRunner.updateMovie(1, stargate);
		appRunner.assertDisplaysDialogWithText("Duplicate Movie",
				"Adding this movie will result in a duplicate movie");
		appRunner.clickOkInDialog();

		appRunner.assertMoviesSize(movies.size());
	}

	/*
	 * Test 47: A rename performed in the context of the logical layer is done
	 * in the underlying full list; that is, potential duplicates in the full
	 * list are detected
	 */
	@Test
	public void testUpdateFilteredListToDuplicateInAllListDisplaysErrorDialog()
			throws UnratedMovieException {
		appRunner.selectMovie(1);
		appRunner.filterByCategory(Category.SCIFI);
		appRunner.selectMovie(1);
		appRunner.enterMovieName(stargate.getName());
		appRunner.clickUpdate();
		appRunner.assertDisplaysDialogWithText("Duplicate Movie",
				"Adding this movie will result in a duplicate movie");
		appRunner.assertMoviesSize(2);

	}

	@Test
	public void testFilteringList() throws DuplicateMovieException,
			UnratedMovieException {
		appRunner.filterByCategory(Category.SCIFI);

		Vector<Movie> filteredMovies = new Vector<Movie>();
		filteredMovies.add(starWars);
		filteredMovies.add(starTrek);
		appRunner.assertMoviesDisplayedEqualTo(filteredMovies);
	}

}
