package src.core;

import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;

public interface MovieListFileFormatter {

	public abstract String fileFormat(MovieList movieList);

	public abstract MovieList toMoviesList(String formattedMoviesText)
			throws NumberFormatException, DuplicateMovieException,
			InvalidFileFormatException;

}