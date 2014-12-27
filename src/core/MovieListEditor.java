package src.core;

import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.Vector;

import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.UnratedMovieException;
import src.ui.MovieListEditorView;

public class MovieListEditor {
	MovieListEditorView view;
	private MovieList movieList;
	private Movie selectedMovie;

	public MovieListEditor(MovieList movieList, MovieListEditorView view) {
		this.movieList = movieList;
		this.view = view;
		updateMovieList();
		this.view.setEditor(this);
	}

	private void updateMovieList() {
		this.view.setMovies(new Vector<Movie>(this.movieList.getMovies()));
	}

	public void addMovie() {
		Movie newMovie = new Movie(view.getNameField(),view.getCategoryField(), view.getRatingField());
		try {
			movieList.add(newMovie);
		} catch (DuplicateMovieException e) {
			view.handleDuplicateMovieException(view.getNameField());
		}
		updateMovieList();

	}

	public void selectMovie(int i) {
		if (i == -1) {
			selectedMovie = null;
		} else {
			view.setNameField(this.movieList.get(i).getName());
			try {
				view.setRatingField(this.movieList.get(i).getRating());
			} catch (UnratedMovieException e) {
				e.printStackTrace();
			}
			selectedMovie = movieList.get(i);
		}
	}

	public void updateMovie() {
		if (selectedMovie != null) {
			selectedMovie.setRating(view.getRatingField());
			if (!view.getNameField().equals(selectedMovie.getName())) {
				try {
					movieList.rename(selectedMovie, view.getNameField());
				} catch (DuplicateMovieException e) {
					view.handleDuplicateMovieException(view.getNameField());
				}
			}
			updateMovieList();
		}
	}

	public Movie getMovie(int i) {
		return movieList.get(i);
	}
}
