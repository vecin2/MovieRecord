package src.core;

import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;
import src.core.exceptions.UnratedMovieException;

public class MovieListFlatFileFormatter implements MovieListFileFormatter {

	/* (non-Javadoc)
	 * @see src.core.MovieListFileFormatter#fileFormat(src.core.MovieList)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see src.core.MovieListFileFormatter#toMoviesList(java.lang.String[])
	 */
	@Override
	public MovieList toMoviesList(String[] formattedMoviesText)
			throws NumberFormatException, DuplicateMovieException, InvalidFileFormatException {
		MovieList movieList = new MovieList();
		for (int i=0; i < formattedMoviesText.length; i++) {
			String line = formattedMoviesText[i];
			String movieArray[] = line.split("\\|");
			if(movieArray.length!=3)
				throw new InvalidFileFormatException(i, line);
			movieList.add(new Movie(movieArray[0], Category.make(movieArray[1]), Integer
					.parseInt(movieArray[2])));
		}
		return movieList;
	}

}
