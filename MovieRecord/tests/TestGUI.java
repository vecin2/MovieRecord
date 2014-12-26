package tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListEditor;
import src.core.exceptions.DuplicateMovieException;
import src.ui.MovieListEditorView;

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
		starWars = new Movie("Star Wars");
		starTrek = new Movie("Star Trek");
		stargate = new Movie("Stargate");
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
		editor = new MovieListEditor(movieList, mockView);
		verify(mockView).setMovies(movies);
	}

	/*
	 * Test 7. When the logical layer is asked to add a movie, it should request
	 * the required data from the view and update the movie list to include a
	 * new movie based on the data provided.
	 */
	@Test
	public void testAdd() {
		mockView = mock(MovieListEditorView.class);
		editor = new MovieListEditor(movieList, mockView);
		when(mockView.getMovieName()).thenReturn("New Movie");
		verify(mockView).setMovies(movies);

		editor.addMovie();

		movies.add(new Movie("New Movie"));
		verify(mockView).setMovies(movies);
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
		editor = new MovieListEditor(movieList, mockView);
		editor.selectMovie(1);
		verify(mockView).setMovieName("Star Trek");
	}

	/*
	 * Test 16: When an update is requested, the selected movie is renamed to
	 * whatever is answered by the view as the new name
	 */
	@Test
	public void testUpdate() {
		mockView = mock(MovieListEditorView.class);
		editor = new MovieListEditor(movieList, mockView);

		when(mockView.getMovieName()).thenReturn("New Movie");
		verify(mockView).setMovies(movies);

		Vector<Movie> newMovies = new Vector<Movie>();
		newMovies.add(starWars);
		Movie newMovie = new Movie("New Movie");
		newMovies.add(newMovie);
		newMovies.add(stargate);

		editor.selectMovie(1);
		editor.updateMovie();

		verify(mockView, times(2)).setMovies(newMovies);
	}

	/*
	 * Test 20: Asking the logical layer to add a duplicate movie causes it to
	 * inform the presentation layer that the operation would result in a
	 * duplicate
	 */
	@Test
	public void testWhenAddDuplicateMovieItInformsTheView() {
		mockView = mock(MovieListEditorView.class);
		when(mockView.getMovieName()).thenReturn("Star Wars");

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
	public void testWhenUpdateDuplicateMovieItInformsTheView(){
		mockView = mock(MovieListEditorView.class);
		when(mockView.getMovieName()).thenReturn("Stargate");

		editor = new MovieListEditor(movieList, mockView);
		editor.selectMovie(1);
		editor.updateMovie();

		verify(mockView).handleDuplicateMovieException("Stargate");
	}
}