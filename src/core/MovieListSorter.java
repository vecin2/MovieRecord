package src.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class MovieListSorter {
	public ArrayList<Movie> moviesToSort;
	private ArrayList<Movie> result;
	private OrderType orderType;

	
	

	public MovieListSorter(MovieList movieList, OrderType orderType) {
		this.moviesToSort =movieList.getMovies();
		this.orderType = orderType;
		}

	public void sort(Comparator<Movie> comparator) {
		Iterator<Movie> moviesToSortIterator = moviesToSort.iterator();
		result = new ArrayList<Movie>();
		while (moviesToSortIterator.hasNext()) {
			Movie currentMovie = moviesToSortIterator.next();
			result.add(computeIndexToInsert(currentMovie, comparator), currentMovie);
		}
		moviesToSort.clear();
		moviesToSort.addAll(result);
	}

	public int computeIndexToInsert(Movie currentMovie, Comparator<Movie> comparator) {
		for (int i = 0; i < result.size(); i++) {
			
			if (comparator.compare(currentMovie, result.get(i)) < 0 && orderType== OrderType.ASC)
				return i;
			else if(comparator.compare(currentMovie, result.get(i)) >= 0 && orderType== OrderType.DESC)
				return i;
		}
		return result.size();
	}
}