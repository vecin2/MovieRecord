package src.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import src.core.Category;
import src.core.Movie;
import src.core.MovieListEditor;
import src.core.Rating;

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
	File getFileToOpen();
	String getRatingSource();
	void setRatings(Vector<Rating> vector);
	ArrayList<Rating> getRatings();
}
