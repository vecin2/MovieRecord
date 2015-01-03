package src.core;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.Vector;

import org.mockito.Mockito;

import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.UnratedMovieException;
import src.ui.MovieListEditorView;

public class MovieListEditor {
	MovieListEditorView view;
	private MovieList movieList;
	private MovieList filteredMovieList;
	private Movie selectedMovie;

	public MovieListEditor(MovieList movieList, MovieListEditorView view) {
		this.movieList = movieList;
		this.view = view;
		filter();
		this.view.setEditor(this);
	}

	private void updateMovieList() {
		this.view.setMovies(new Vector<Movie>(this.filteredMovieList.getMovies()));
	}

	public void addMovie() {
		Movie newMovie = new Movie(view.getNameField(),
				view.getCategoryField(), view.getRatingField());
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

	public Movie getMovie(int i) {
		return movieList.get(i);
	}

	public void filter() {
		if (view.getCategoryFilter().equals(Category.ALL)) {
			filteredMovieList =movieList;
		} else {
			filteredMovieList = movieList.filterBy(view.getCategoryFilter());
		}
		updateMovieList();
	}

	public void saveAs() throws IOException {
		movieList.writeTo(view.getFile(),new MovieListFormatter());
		
	}
}
