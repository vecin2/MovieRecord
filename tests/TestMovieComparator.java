package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import src.core.Category;
import src.core.Movie;
import src.sorting.MovieNameComparator;
import src.sorting.MovieRatingComparator;

public class TestMovieComparator {
	
	@Test
	public void testCompareByNameReturnNegativeWhenSecondBiggerThanFirst(){
		Movie braveheart = new Movie("Braveheart", Category.HORROR, 5);
		Movie goneGirl = new Movie("Gone girl", Category.HORROR,4);
		MovieNameComparator movieNameComparator = new MovieNameComparator();
		int result = movieNameComparator.compare(braveheart, goneGirl);
		assertTrue("Expected less than 0 but was "+ result,0 > result);
	}
	@Test
	public void testCompareByRatingReturnNegativeWhenSecondBiggerThanFirst(){
		Movie goneGirl = new Movie("Gone girl", Category.HORROR,4);
		Movie braveheart = new Movie("Braveheart", Category.HORROR, 5);
		MovieRatingComparator movieRatingComparator = new MovieRatingComparator();
		int result = movieRatingComparator.compare(goneGirl, braveheart);
		assertTrue("Expected less than 0 but was "+ result,0 > result);
	}

}
