package src.ui;

import java.util.Vector;

import src.core.Movie;
import src.core.MovieListEditor;

public interface MovieListEditorView {
	void setMovies(Vector<Movie> movieList);
	String getNameField();
	void setEditor(MovieListEditor anEditor);
	void setNameField(String movieName);
	void handleDuplicateMovieException(String movieName);
	void setRatingField(int rating);
	int getRatingField();
}
