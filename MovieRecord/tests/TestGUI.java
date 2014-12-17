package tests;

import java.util.ArrayList;
import java.util.Vector;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import src.core.Movie;
import src.core.MovieListEditor;
import src.ui.MovieListEditorView;

public class TestGUI {
	private MovieListEditorView mockView = null;
	private Vector<Movie> movies = null;
	private Movie starWars = null;
	private Movie starTrek = null;
	private Movie stargate = null;
	private ArrayList<Movie> movieList;

	@Before
	public void setUp() {
		starWars = new Movie("Star Wars");
		starTrek = new Movie("Star Trek");
		stargate = new Movie("Stargate");
		movies = new Vector<Movie>();
		movies.add(starWars);
		movies.add(starTrek);
		movies.add(stargate);
		movieList = new ArrayList<Movie>();
		movieList.add(starWars);
		movieList.add(starTrek);
		movieList.add(stargate);
	}

	@Test
	public void testConstructorCallsViewWithListToDisplay() {
		mockView = mock(MovieListEditorView.class);
		MovieListEditor editor = new MovieListEditor(movieList, mockView);
		verify(mockView).setMovies(movies);
	}
	/*Test 7. When the logical layer is asked to add a movie, it should request the required data from the view and
	update the movie list to include a new movie based on the data provided.*/
	@Test
	public void testAdd(){
		mockView = mock(MovieListEditorView.class);
	    when(mockView.getMovieName()).thenReturn("New Movie");
		MovieListEditor editor = new MovieListEditor(movieList, mockView);
		verify(mockView).setMovies(movies);

		editor.addMovie();
	    
		movies.add(new Movie("New Movie"));
	    verify(mockView).setMovies(movies);
	}
}
