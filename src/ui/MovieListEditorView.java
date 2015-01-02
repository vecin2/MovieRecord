package src.ui;

import java.io.File;
import java.util.Vector;

import src.core.Category;
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
	Category getCategoryField();
	Category getCategoryFilter();
	void setCategoryField(Category category);
	File getFile();
}
