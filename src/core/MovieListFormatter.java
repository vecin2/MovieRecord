package src.core;

import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.UnratedMovieException;

public class MovieListFormatter {

	public String fileFormat(MovieList movieList) {
		String result = "";
		for (Movie movie : movieList.getMovies()) {
			result += movieToFileFormat(movie);
		}
		return result;

	}

	private String movieToFileFormat(Movie movie) {
		try {
			return movie.name + "|" + movie.getCategory() + "|"
					+ movie.getRating() + "\n";
		} catch (UnratedMovieException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public MovieList toMoviesList(String[] formattedMoviesText)
			throws NumberFormatException, DuplicateMovieException {
		MovieList movieList = new MovieList();
		for (String line : formattedMoviesText) {
			String movieArray[] = line.split("\\|");
			movieList.add(new Movie(movieArray[0], Category.make(movieArray[1]), Integer
					.parseInt(movieArray[2])));
		}
		return movieList;
	}

}
