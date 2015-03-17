package src.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;

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
		for (Movie movie : movies) {
			if (movie.getCategory().equals(category)) {
				try {
					result.add(movie);
				} catch (DuplicateMovieException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public void writeTo(File outputFile, MovieListFileFormatter movieListFormatter)
			throws IOException {
		FileWriter fileWriter = new FileWriter(outputFile);
		fileWriter.write(movieListFormatter.fileFormat(this));
		fileWriter.flush();
		fileWriter.close();
	}

	public static MovieList readFrom(File file,
			MovieListFileFormatter movieListFormatter)
			throws NumberFormatException, DuplicateMovieException, IOException,
			InvalidFileFormatException {

		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		String fileContent = "";
		while (line != null) {
			fileContent += line + "\n";
			line = br.readLine();
		}
		return movieListFormatter.toMoviesList(fileContent.split("\\n"));

	}

	public void orderBy(Comparator comparator, OrderType orderType) {
		MovieListSorter sorter = new MovieListSorter(this, orderType);
		sorter.sort(comparator);
	}

}
