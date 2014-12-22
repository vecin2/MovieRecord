package src.core;

import java.util.ArrayList;
import java.util.Vector;

import src.ui.MovieListEditorView;

public class MovieListEditor {
	MovieListEditorView view;
	private ArrayList<Movie> movieList;
	private Movie selectedMovie;

	public MovieListEditor(ArrayList<Movie> movieList, MovieListEditorView view) {
		this.movieList = movieList;
		this.view = view;
		updateMovieList();
		this.view.setEditor(this);
	}

	private void updateMovieList() {
		this.view.setMovies(new Vector<Movie>(this.movieList));
	}

	public void addMovie() {
		Movie newMovie = new Movie(view.getMovieName());
		movieList.add(newMovie);
		this.view.setMovies(new Vector<Movie>(movieList));

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
			selectedMovie.rename(view.getMovieName());
			view.setMovies(new Vector<Movie>(movieList));
		}
	}

}
