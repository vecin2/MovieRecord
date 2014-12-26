package src.ui;

import java.util.Vector;

import src.core.Movie;
import src.core.MovieListEditor;

public interface MovieListEditorView {
	void setMovies(Vector<Movie> movieList);
	String getMovieName();
	void setEditor(MovieListEditor anEditor);
	void setMovieName(String movieName);
	void handleDuplicateMovieException(String movieName);
}