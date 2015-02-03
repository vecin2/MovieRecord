package src.sorting;

import java.util.Comparator;

import src.core.Movie;

public class MovieNameComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		Movie movie1 = (Movie)o1;
		Movie movie2 = (Movie)o2;
		return movie1.getName().compareTo(movie2.getName());
	}

}
