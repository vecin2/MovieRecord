package src.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.Vector;

import org.mockito.Mockito;

import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;
import src.core.exceptions.UnratedMovieException;
import src.sorting.MovieNameComparator;
import src.sorting.MovieRatingComparator;
import src.ui.MovieListEditorView;

public class MovieListEditor {
	MovieListEditorView view;
	private MovieList movieList;
	private MovieList filteredMovieList;
	private Movie selectedMovie;
	private File outputFile;
	private ArrayList<Rating> ratingList;

	public MovieListEditor(MovieList movieList, MovieListEditorView view) {
		this.movieList = movieList;
		this.view = view;
		filter();
		this.view.setEditor(this);
		this.ratingList = new ArrayList<Rating>();
	}

	private void updateMovieList() {
		this.view.setMovies(new Vector<Movie>(this.filteredMovieList
				.getMovies()));
	}

	public void addMovie() {
		Movie newMovie = new Movie(view.getNameField(),
				view.getCategoryField());

		newMovie.addRatings(view.getRatings());
		try {
			movieList.add(newMovie);
		} catch (DuplicateMovieException e) {
			view.handleDuplicateMovieException(view.getNameField());
		}
		filter();

	}

	public void selectMovie(int i) {
		if (i == -1) {
			selectedMovie = null;
		} else {
			selectedMovie = filteredMovieList.get(i);
			view.setNameField(selectedMovie.getName());
			view.setCategoryField(selectedMovie.getCategory());
			try {
				view.setRatingField(selectedMovie.getRating());
				view.setRatings(new Vector<Rating>(selectedMovie.getRatings()));
			} catch (UnratedMovieException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateMovie() {
		if (selectedMovie != null) {
			selectedMovie.setRating(view.getRatingField());
			selectedMovie.setCategory(view.getCategoryField());
			if (!view.getNameField().equals(selectedMovie.getName())) {
				try {
					movieList.rename(selectedMovie, view.getNameField());
				} catch (DuplicateMovieException e) {
					view.handleDuplicateMovieException(view.getNameField());
				}
			}
			filter();
		}
	}

	public void filter() {
		if (view.getCategoryFilter().equals(Category.ALL)) {
			filteredMovieList = movieList;
		} else {
			filteredMovieList = movieList.filterBy(view.getCategoryFilter());
		}
		updateMovieList();
	}

	public boolean saveAs() throws IOException {
		outputFile = view.getFile();
		if (outputFile != null) {
			movieList.writeTo(outputFile, new MovieListXMLFormatter());
			return true;
		} else {
			return false;
		}

	}

	public boolean save() throws IOException {
		if (outputFile != null) {
			movieList.writeTo(outputFile, new MovieListXMLFormatter());
			return true;
		} else {
			return false;
		}
	}

	public boolean open() throws NumberFormatException,
			DuplicateMovieException, IOException, InvalidFileFormatException {
		outputFile = view.getFileToOpen();
		if (outputFile != null) {
			filteredMovieList = MovieList.readFrom(outputFile,
					new MovieListXMLFormatter());
			updateMovieList();
			return true;
		}

		return false;
	}

	public void sortByName() {
		filteredMovieList.orderBy(new MovieNameComparator(), OrderType.ASC);
		updateMovieList();
	}

	public void sortByRating() {
		filteredMovieList.orderBy(new MovieRatingComparator(), OrderType.DESC);
		updateMovieList();
		
	}

	public void addRating() {
		ratingList.add(new Rating(view.getRatingField(), view.getRatingSource()));
		view.setRatings(new Vector<Rating>(ratingList));
	}
}
