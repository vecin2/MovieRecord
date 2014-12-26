package tests;

import static org.junit.Assert.*;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JList;

import org.junit.Before;
import org.junit.Test;

import src.core.Movie;
import src.core.MovieList;
import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.UnratedMovieException;
import src.ui.CustomMovieListRenderer;

public class TestCustomListRenderer {
	private Movie stargate;
	private Movie starwars;
	private Vector<Movie> movieList;
	private CustomMovieListRenderer renderer;
	private JList list;

	@Before
	public void setup() throws DuplicateMovieException {
		stargate = new Movie("Stargate", 1);
		starwars = new Movie("Starwars", 5);
		movieList = new Vector<Movie>();
		movieList.add(stargate);
		movieList.add(starwars);
		list = new JList<>(movieList);
		renderer = new CustomMovieListRenderer();
	}

	/*
	 * Test 27. When asked for the renderer component, the renderer returns
	 * itself.
	 */
	@Test
	public void testListCellRendererComponentReturnItself() {
		Component result = renderer.getListCellRendererComponent(list,
				starwars, 1, false, false);
		assertSame("getListCellRendererComponent should return self.",
				renderer, result);
	}

	/*
	 * Test 28: When given a movie to render, the resulting test and rating
	 * image corresponds to the movie being rendered
	 */
	@Test
	public void testRenderingAMovieWithNameAndRate() {
		assertOnListValue(stargate, 0, 2);
		assertOnListValue(starwars, 1, 6);
	}

	private void assertOnListValue(Movie movie, int index,
			int iconForRatingIndex) {
		renderer.getListCellRendererComponent(list, movie, index, false, false);
		assertEquals(movie.getName(), renderer.getText());
		assertEquals(CustomMovieListRenderer.iconForRating(iconForRatingIndex),
				renderer.getIcon());
	}
}
