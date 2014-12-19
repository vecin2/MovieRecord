package src.core;

import java.util.ArrayList;
import java.util.Vector;

import src.ui.MovieListEditorView;

public class MovieListEditor {
	MovieListEditorView view;
	ArrayList<Movie> movieList;

	public MovieListEditor(ArrayList<Movie> movieList, MovieListEditorView view) {
		this.movieList = movieList;
		this.view = view;
		this.view.setMovies(new Vector<Movie>(this.movieList));
		this.view.setEditor(this);
	}

	public void addMovie() {
		Movie newMovie = new Movie(view.getMovieName());
		movieList.add(newMovie);
		this.view.setMovies(new Vector<Movie>(movieList));
		
		
	}



}
