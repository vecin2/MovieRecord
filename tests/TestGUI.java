package tests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListEditor;
import src.core.Rating;
import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;
import src.core.exceptions.UnratedMovieException;
import src.ui.MovieListEditorView;
import testUtils.FileAssertor;
import testUtils.MoviesAssert;

public class TestGUI {
	private MovieListEditorView mockView = null;
	private Vector<Movie> movies = null;
	private Movie starWars = null;
	private Movie starTrek = null;
	private Movie stargate = null;
	private MovieList movieList;
	private MovieListEditor editor;

	@Before
	public void setUp() throws DuplicateMovieException {
		starWars = new Movie("Star Wars", Category.SCIFI, 5);
		starTrek = new Movie("Star Trek", Category.HORROR, 1);
		stargate = new Movie("Stargate", Category.HORROR, 0);
		movies = new Vector<Movie>();
		movies.add(starWars);
		movies.add(starTrek);
		movies.add(stargate);
		movieList = new MovieList();
		movieList.add(starWars);
		movieList.add(starTrek);
		movieList.add(stargate);
	}

	@Test
	public void testConstructorCallsViewWithListToDisplay() {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		editor = new MovieListEditor(movieList, mockView);
		verify(mockView).setMovies(movies);
	}

	/*
	 * Test 7. When the logical layer is asked to add a movie, it should request
	 * the required data from the view and update the movie list to include a
	 * new movie based on the data provided.
	 */
	@Test
	public void testAdd() throws UnratedMovieException {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		editor = new MovieListEditor(movieList, mockView);
		when(mockView.getNameField()).thenReturn("New Movie");
		when(mockView.getRatingField()).thenReturn(2);
		when(mockView.getCategoryField()).thenReturn(Category.HORROR);
		verify(mockView).setMovies(movies);

		editor.addMovie();

		movies.add(new Movie("New Movie", Category.HORROR, 2));
		ArgumentCaptor<Vector> movieListCaptor = ArgumentCaptor
				.forClass(Vector.class);
		verify(mockView, times(2)).setMovies(movieListCaptor.capture());
		Vector<Movie> captureMovies = movieListCaptor.getValue();

		MoviesAssert.assertEqualMovieCollection(movies, captureMovies);
	}

	@Test
	public void testAddRating() throws UnratedMovieException {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		editor = new MovieListEditor(movieList, mockView);
		when(mockView.getRatingField()).thenReturn(2);
		when(mockView.getRatingSource()).thenReturn("Spielberg");
		verify(mockView).setMovies(movies);

		editor.addRating();
		Vector<Rating> ratings = new Vector<Rating>();
		ratings.add(new Rating(2, "Spielberg"));
		verify(mockView).setRatings(ratings);

	}

	/* Test 31. Selecting a movie updates the rating in the GUI. */
	@Test
	public void testSelecting() throws UnratedMovieException {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		editor = new MovieListEditor(movieList, mockView);

		editor.selectMovie(1);
		verify(mockView).setNameField(starTrek.getName());
		verify(mockView).setCategoryField(starTrek.getCategory());
		verify(mockView).setRatingField(starTrek.getRating());
	}

	/*
	 * Test 14. Indicating, to the logical layer, that a selection is made from
	 * the list causes the view to be given a value for the name field, that is,
	 * the selected movie's name.
	 */
	/* Rewrite to: */
	@Test
	public void testWhenMovieIsSelectedCallsSetMovieNameOnViewWithTheNameOfSelectedMovie() {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		editor = new MovieListEditor(movieList, mockView);
		editor.selectMovie(1);
		verify(mockView).setNameField("Star Trek");
	}

	/*
	 * Test 16: When an update is requested, the selected movie is renamed to
	 * whatever is answered by the view as the new name Test 32: Updating a
	 * movie changes its rating if a different rating was selected for it
	 */
	@Test
	public void testUpdate() throws UnratedMovieException {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		editor = new MovieListEditor(movieList, mockView);

		when(mockView.getNameField()).thenReturn("New Movie");
		when(mockView.getRatingField()).thenReturn(2);
		when(mockView.getCategoryField()).thenReturn(Category.SCIFI);
		verify(mockView).setMovies(movies);

		Vector<Movie> newMovies = new Vector<Movie>();
		newMovies.add(starWars);
		Movie newMovie = new Movie("New Movie", 2);
		newMovies.add(newMovie);
		newMovies.add(stargate);

		editor.selectMovie(1);
		editor.updateMovie();
		ArgumentCaptor<Vector> argumentCaptor = ArgumentCaptor
				.forClass(Vector.class);
		verify(mockView, times(2)).setMovies(argumentCaptor.capture());
		Vector<Movie> argument = (Vector<Movie>) argumentCaptor.getValue();
		assertEquals("New Movie", argument.get(1).getName());
		assertEquals("Wrong Category", Category.SCIFI, argument.get(1)
				.getCategory());
		assertEquals(2, argument.get(1).getRating());
	}

	/*
	 * Test 49. Changing the category of a movie in a filtered list causes that
	 * movie to be removed from the list. If that category is filtered on, that
	 * movie will be in the list.
	 */
	@Test
	public void testChangingAMovieCategoryOnFilterListCausesTheMovieToDisappearFromTheList() {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL).thenReturn(
				Category.HORROR);
		when(mockView.getNameField()).thenReturn(stargate.getName());
		when(mockView.getCategoryField()).thenReturn(Category.SCIFI);
		editor = new MovieListEditor(movieList, mockView);
		editor.filter();
		editor.selectMovie(1);
		editor.updateMovie();
		ArgumentCaptor<Vector> movieListCaptor = ArgumentCaptor
				.forClass(Vector.class);

		verify(mockView, times(3)).setMovies(movieListCaptor.capture());
		assertEquals(1, movieListCaptor.getValue().size());
		assertEquals(starTrek, movieListCaptor.getValue().get(0));
	}

	/*
	 * Test 20: Asking the logical layer to add a duplicate movie causes it to
	 * inform the presentation layer that the operation would result in a
	 * duplicate
	 */
	@Test
	public void testWhenAddDuplicateMovieItInformsTheView() {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		when(mockView.getNameField()).thenReturn("Star Wars");

		editor = new MovieListEditor(movieList, mockView);
		editor.addMovie();

		verify(mockView).handleDuplicateMovieException("Star Wars");
	}

	/*
	 * Test 21: Asking the logical layer to update a movie that would result in
	 * a duplicate causes it to inform the presentation layer that the operation
	 * would result in a duplicate
	 */
	@Test
	public void testWhenUpdateDuplicateMovieItInformsTheView() {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		when(mockView.getNameField()).thenReturn("Stargate");

		editor = new MovieListEditor(movieList, mockView);
		editor.selectMovie(1);
		editor.updateMovie();

		verify(mockView).handleDuplicateMovieException("Stargate");
	}

	@Test
	public void testWhenSelectHorrorFilterItDisplayHorrorMoviesOnly()
			throws DuplicateMovieException, UnratedMovieException {

		Vector<Movie> horrorMovies = new Vector<Movie>();
		horrorMovies.add(starTrek);
		horrorMovies.add(stargate);
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.HORROR);
		editor = new MovieListEditor(movieList, mockView);

		editor.filter();

		verify(mockView, times(2)).setMovies(horrorMovies);
	}

	@Test
	public void testWhenSelectAllCategoriesDisplaysAllMovies()
			throws DuplicateMovieException, UnratedMovieException {

		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		editor = new MovieListEditor(movieList, mockView);

		when(mockView.getCategoryFilter()).thenReturn(Category.HORROR);
		editor.filter();
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		editor.filter();

		verify(mockView, times(2)).setMovies(movies);
	}

	@Test
	public void testWhenOpenSetMoviesWithConvertedFileContent()
			throws IOException, NumberFormatException, DuplicateMovieException,
			InvalidFileFormatException, UnratedMovieException {
		mockView = mock(MovieListEditorView.class);
		File inputFile = File.createTempFile("movies", ".dat");
		inputFile.deleteOnExit();
		FileWriter fileWriter = new FileWriter(inputFile);
		String fileText = "Star Wars|" + Category.SCIFI + "|5\n";
		fileText += "Star Trek|" + Category.HORROR + "|1\n";
		fileText += "Stargate|" + Category.HORROR + "|0\n";
		fileWriter.write(fileText);
		fileWriter.flush();
		fileWriter.close();
		when(mockView.getFileToOpen()).thenReturn(inputFile);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		editor = new MovieListEditor(new MovieList(), mockView);

		assertTrue("Opening a file should return true when succesful",
				editor.open());

		ArgumentCaptor<Vector> captor = ArgumentCaptor.forClass(Vector.class);
		verify(mockView, times(2)).setMovies(captor.capture());
		MoviesAssert.assertEqualMovieCollection(new Vector<Movie>(), captor
				.getAllValues().get(0));
		MoviesAssert.assertEqualMovieCollection(movies, captor.getAllValues()
				.get(1));
	}

	@Test
	public void testWhenOrderByNameCallsTheViewWithThelistOrdered() {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getCategoryFilter()).thenReturn(Category.ALL);
		editor = new MovieListEditor(movieList, mockView);
		Vector<Movie> orderedMovies = new Vector<Movie>();
		orderedMovies.add(starTrek);
		orderedMovies.add(starWars);
		orderedMovies.add(stargate);
		editor.sortByName();
		verify(mockView).setMovies(movies);
	}

}
