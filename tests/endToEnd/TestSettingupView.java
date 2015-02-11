package tests.endToEnd;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListEditor;
import src.core.Rating;
import src.ui.SwingMovieListEditorView;
import testUtils.ApplicationRunner;

public abstract class TestSettingupView {

	protected Vector<Movie> movies = null;
	protected Movie starWars = null;
	protected Movie starTrek = null;
	protected Movie stargate = null;
	protected MovieList movieList = null;
	
	protected ApplicationRunner appRunner;



	@Before
	public void setUp() throws Exception {
		SwingMovieListEditorView.start();
		starWars = new Movie("Star Wars", Category.SCIFI, new Rating(3,"user1"));
		starTrek = new Movie("Star Trek", Category.SCIFI, new Rating(4,"user2"));
		stargate = new Movie("Stargate", Category.HORROR, new Rating(5,"user3"));
		movies = new Vector<Movie>();
		movies.add(starWars);
		movies.add(starTrek);
		movies.add(stargate);
		movieList = new MovieList();
		movieList.add(starWars);
		movieList.add(starTrek);
		movieList.add(stargate);
		
		appRunner = new ApplicationRunner("Movie List");
		appRunner.setup();
		MovieListEditor editor = new MovieListEditor(movieList,
				appRunner.getView());
	}

	@After
	public void tearDown() throws Exception {
		appRunner.tearDown();
	
	}

}