package src.core;

import java.util.ArrayList;

import src.core.exceptions.DuplicateMovieException;

public class MovieList {

	ArrayList<Movie> movies = new ArrayList<Movie>();

	public boolean add(Movie movie) throws DuplicateMovieException {
		if (this.contains(movie))
			throw new DuplicateMovieException("Trying to add a duplicate movie");
		return movies.add(movie);
	}

	private boolean contains(Movie movie) {
		return movies.contains(movie);
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public Movie get(int i) {
		return movies.get(i);
	}

	public static MovieList create(String movieName) {
		MovieList movieList = new MovieList();
		// when creating a list it can not throw a duplication movie Exception
		try {
			movieList.add(new Movie(movieName));
		} catch (DuplicateMovieException e) {
			e.printStackTrace();
		}
		return movieList;
	}

	public boolean add(String movieName) throws DuplicateMovieException {
		return add(new Movie(movieName));
	}

	public void rename(Movie movieToReplace, String replacerTitle)
			throws DuplicateMovieException {
		if (getByName(replacerTitle) != null)
			throw new DuplicateMovieException(
					"Renaming resulting in a duplicate movie");
		getByName(movieToReplace.getName()).rename(replacerTitle);
	}

	private Movie getByName(String movieName) {
		for (Movie movie : movies)
			if (movie.getName().endsWith(movieName))
				return movie;
		return null;
	}

	public int size() {
		return movies.size();
	}

	public void addAll(MovieList movieList) {
		movies.addAll(movieList.movies);
	}

	public MovieList filterBy(Category category) {
		MovieList result = new MovieList();
		for(Movie movie: movies){
			if(movie.getCategory().equals(category)){
				try {
					result.add(movie);
				} catch (DuplicateMovieException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
