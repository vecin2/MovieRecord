package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListSorter;
import src.core.OrderType;
import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.UnratedMovieException;
import src.sorting.MovieNameComparator;
import src.sorting.MovieRatingComparator;
import testUtils.MoviesAssert;

public class TestMovieListSorting {
	private MovieList movieList;
	private MovieList orderedByName;
	private Movie braveheart;
	private Movie mrBean;
	private Movie goneGirl;
	private MovieList orderedByRating;

	@Before
	public void setup() throws DuplicateMovieException {
		movieList = new MovieList();
		goneGirl = new Movie("Gone girl", Category.HORROR, 1);
		mrBean = new Movie("Mr Bean", Category.COMEDY, 3);
		braveheart = new Movie("Braveheart", Category.HORROR, 5);

		orderedByName = new MovieList();
		orderedByName.add(braveheart);
		orderedByName.add(goneGirl);
		orderedByName.add(mrBean);
		
		orderedByRating = new MovieList();
		orderedByRating.add(braveheart);
		orderedByRating.add(mrBean);
		orderedByRating.add(goneGirl);
	}

	@Test
	public void testOrderEmptyListDoesNothingToThelist() {
		movieList.orderBy(new MovieNameComparator(), OrderType.ASC);
		assertEquals(0, movieList.size());
	}

	@Test
	public void testOrderOneElementListDoesNothingToThelist()
			throws DuplicateMovieException {
		movieList.add(braveheart);
		movieList.orderBy(new MovieNameComparator(), OrderType.ASC);
		assertEquals(1, movieList.size());
		assertEquals(braveheart, movieList.get(0));
	}

	@Test
	public void testOrderTwoElementsByNameOrderTheList()
			throws UnratedMovieException, DuplicateMovieException {
		movieList.add(goneGirl);
		movieList.add(braveheart);
		movieList.orderBy(new MovieNameComparator(), OrderType.ASC);
		assertEquals("Wrong element 0:", braveheart, movieList.get(0));
		assertEquals(goneGirl, movieList.get(1));

	}

	@Test
	public void testOrderThreeElementsByNameOrderTheList()
			throws UnratedMovieException, DuplicateMovieException {
		movieList.add(goneGirl);
		movieList.add(mrBean);
		movieList.add(braveheart);
		movieList.orderBy(new MovieNameComparator(), OrderType.ASC);
		assertEquals("Wrong element 0:", braveheart, movieList.get(0));
		assertEquals("Wrong element 1:", goneGirl, movieList.get(1));
		assertEquals("Wrong element 2:", mrBean, movieList.get(2));
	}
	@Test
	public void testOrderThreeElementsInOrderDoesNotChangeTheList()
			throws UnratedMovieException, DuplicateMovieException {
		movieList.add(braveheart);
		movieList.add(goneGirl);
		movieList.add(mrBean);
		movieList.orderBy(new MovieNameComparator(), OrderType.ASC);
		assertEquals("Wrong element 0:", braveheart, movieList.get(0));
		assertEquals("Wrong element 1:", goneGirl, movieList.get(1));
		assertEquals("Wrong element 2:", mrBean, movieList.get(2));
	}

	@Test
	public void testOrderThreeElementsByRatingOrderTheList()
			throws UnratedMovieException, DuplicateMovieException {
		movieList.add(goneGirl);
		movieList.add(mrBean);
		movieList.add(braveheart);
		movieList.orderBy(new MovieRatingComparator(), OrderType.DESC);
		assertEquals("Wrong element 0:", braveheart, movieList.get(0));
		assertEquals("Wrong element 1:", mrBean, movieList.get(1));
		assertEquals("Wrong element 2:", goneGirl, movieList.get(2));
	}

}
