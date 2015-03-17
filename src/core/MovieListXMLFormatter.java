package src.core;

import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;


public class MovieListXMLFormatter implements MovieListFileFormatter {

	
	@Override
	public String fileFormat(MovieList movieList) {
		return "<movielist></movielist>";
	}

	@Override
	public MovieList toMoviesList(String[] formattedMoviesText)
			throws NumberFormatException, DuplicateMovieException,
			InvalidFileFormatException {
		// TODO Auto-generated method stub
		return null;
	}

}
