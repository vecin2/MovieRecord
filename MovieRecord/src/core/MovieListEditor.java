package src.core;

import java.util.ArrayList;
import java.util.Vector;

import src.core.exceptions.DuplicateMovieException;
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
		Movie newMovie = new Movie(view.getMovieName());
		try {
			movieList.add(newMovie);
		} catch (DuplicateMovieException e) {
			view.handleDuplicateMovieException(view.getMovieName());
		}
		updateMovieList();

	}

	public void selectMovie(int i) {
		if (i == -1) {
			selectedMovie = null;
		} else {
			view.setMovieName(this.movieList.get(i).getName());
			selectedMovie = movieList.get(i);
		}
	}

	public void updateMovie() {
		if (selectedMovie != null) {
			try {
				movieList.rename(selectedMovie, view.getMovieName());
			} catch (DuplicateMovieException e) {
				view.handleDuplicateMovieException(view.getMovieName());
			}
			updateMovieList();
		}
	}
}