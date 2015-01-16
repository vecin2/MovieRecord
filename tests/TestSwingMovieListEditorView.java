package tests;

import java.util.Vector;

import org.junit.Test;

import src.core.Category;
import src.core.Movie;
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
	public void testAdding() throws UnratedMovieException {
		Movie newMovie = new Movie("New Movie", Category.SCIFI, 1);
		movies.add(newMovie);

		appRunner.addMovie("New Movie", Category.SCIFI, 2);

		appRunner.assertMoviesDisplayedEqualTo(movies);
	}

	/*
	 * Test 15: Selecting from the list causes the name field to be filled in
	 * with the selected movie's name Test 33: Selecting a movie from the list
	 * updates the displayed rating
	 */
	@Test
	public void testWhenSelectFromOriginalListFillsMovieDetailsWithTheSelectedMovie() {
		appRunner.selectMovie(0);
		appRunner.assertMovieDetailsDisplays("Star Wars", Category.SCIFI, 6);

		appRunner.selectMovie(1);
		appRunner.assertMovieDetailsDisplays("Star Trek", Category.SCIFI, 5);

		appRunner.selectMovie(2);
		appRunner.assertMovieDetailsDisplays("Stargate", Category.HORROR, 4);
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
		appRunner.updateMovie(1, starTrekUpdated);

		appRunner.selectMovie(0);
		appRunner.selectMovie(1);

		appRunner.assertMovieDetailsDisplays(starTrekUpdated);

	}

	@Test
	public void testUpdateWithSameNameShouldNotThrowADuplicatedException()
			throws UnratedMovieException {
		appRunner.selectMovie(1);
		appRunner.selectRating(3);

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
		appRunner.addMovie(stargate.getName(), Category.HORROR, 2);
		appRunner
				.assertDisplaysDialogWithText("Adding this movie will result in a duplicate movie");
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
		appRunner
				.assertDisplaysDialogWithText("Adding this movie will result in a duplicate movie");
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
		appRunner
				.assertDisplaysDialogWithText("Adding this movie will result in a duplicate movie");
		appRunner.assertMoviesSize(2);

	}

	@Test
	public void testFilteringList() throws DuplicateMovieException, UnratedMovieException {
		appRunner.filterByCategory(Category.SCIFI);

		Vector<Movie> filteredMovies = new Vector<Movie>();
		filteredMovies.add(starWars);
		filteredMovies.add(starTrek);
		appRunner.assertMoviesDisplayedEqualTo(filteredMovies);
	}

}
