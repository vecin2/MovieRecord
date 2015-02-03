package src.sorting;

import java.util.Comparator;

import src.core.Movie;
import src.core.exceptions.UnratedMovieException;

public class MovieRatingComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		Movie movie1 = (Movie) o1;
		Movie movie2 = (Movie) o2;
		Integer rating1;
		Integer rating2;
		try {
			rating1 = new Integer(movie1.getRating());
		} catch (UnratedMovieException e) {
			return -1;
		}
		try {
			rating2 = new Integer(movie2.getRating());
		} catch (UnratedMovieException e) {
			return 1;
		}
		
		return rating1.compareTo(rating2);
	}

}
