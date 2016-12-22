package testUtils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ListModel;

import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.AbstractButtonOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.Rating;
import src.core.exceptions.UnratedMovieException;
import src.ui.CustomMovieListRenderer;
import src.ui.MovieListEditorView;
import src.ui.SwingMovieListEditorView;

public class ApplicationRunner {

	private JFrameOperator mainWindow;
	private JTextFieldOperator nameOperator;
	private JComboBoxOperator categoryOperator;
	private JComboBoxOperator ratingOperator;
	private JButtonOperator addBtnOperator;
	private JListOperator listOperator;
	private JTextFieldOperator newMovieField;
	private JComboBoxOperator categoryFilterOperator;
	private JButtonOperator updateBtn;
	private JDialogOperator dialogOperator;
	private long backupWaitComponentTimeout;
	private long backupWaitDialogTimeout;
	private JMenuBarOperator menuBarOperator;
	private JTextFieldOperator ratingSourceOperator;
	private JButtonOperator addRatingBtnOperator;

	public ApplicationRunner(String frameName) {
		mainWindow = new JFrameOperator(frameName);
	}

	public void addMovie(String movieName, Category category,
		ArrayList<Rating> ratings) {
		enterMovieName(movieName);
		selectCategory(category);
		addRatings(ratings);
		clickAdd();
	}

	private void addRatings(ArrayList<Rating> ratings) {
		for (Rating rating : ratings) {
			addRating(rating);
		}

	}

	public void addRating(Rating rating) {
		selectRating(rating.getValue());
		ratingSourceOperator().setText(rating.getSource());
		ratingBtnOperator().doClick();

	}

	private JButtonOperator ratingBtnOperator() {
		if (addRatingBtnOperator == null)
			addRatingBtnOperator = new JButtonOperator(mainWindow,
					new NameComponentChooser("addRatingBtn"));
		return addRatingBtnOperator;
	}

	private JTextFieldOperator ratingSourceOperator() {
		if (ratingSourceOperator == null)
			ratingSourceOperator = new JTextFieldOperator(mainWindow,
					new NameComponentChooser("ratingSource"));
		return ratingSourceOperator;
	}

	public void clickAdd() {
		addBtnOperator().push();
	}

	public void selectRating(int rating) {
		getView().setRatingField(rating);
	}

	public void selectCategory(Category category) {
		categoryOperator().setSelectedItem(category);
	}

	public void enterMovieName(String movieName) {
		movieNameField().setText(movieName);
	}

	private JButtonOperator addBtnOperator() {
		if (addBtnOperator == null)
			addBtnOperator = new JButtonOperator(mainWindow,
					new NameComponentChooser("addMovieBtn"));
		return addBtnOperator;
	}

	

	public Movie selectMovie(int movieIndex) {
		return (Movie) listOperator().clickOnItem(movieIndex, 1);
	}

	private JComboBoxOperator ratingOperator() {
		if (ratingOperator == null)
			ratingOperator = new JComboBoxOperator(mainWindow,
					new NameComponentChooser("ratingCombo"));
		return ratingOperator;
	}

	private JComboBoxOperator categoryOperator() {
		if (categoryOperator == null)
			categoryOperator = new JComboBoxOperator(mainWindow,
					new NameComponentChooser("categoryCombo"));
		return categoryOperator;
	}

	private JTextFieldOperator movieNameField() {
		if (newMovieField == null)
			newMovieField = new JTextFieldOperator(mainWindow);
		return newMovieField;
	}

	private JListOperator listOperator() {
		if (listOperator == null)
			listOperator = new JListOperator(mainWindow);
		return listOperator;
	}

	public void assertMoviesDisplayedEqualTo(Vector<Movie> movies)
			throws UnratedMovieException {
		JListOperator movieList = new JListOperator(mainWindow);
		ListModel<Movie> listModel = movieList.getModel();
		MoviesAssert.assertEqualListModel(movies, movieList);
	}

	public void assertMovieDetailsDisplays(Movie movie)
			throws UnratedMovieException {
	
		assertEquals("wrong text from selection", movie.getName(), movieNameField()
				.getText());
		assertEquals("Wrong category from selection", movie.getCategory(),
				categoryOperator().getSelectedItem());
		assertEquals("wrong rating from selection",
				CustomMovieListRenderer.iconForRating(movie.getRating()+1),
				ratingOperator().getSelectedItem());	
		assertEquals("Wrong list of ratings displayed", movie.getRatings(), getView().getRatings());
	}


	public void filterByCategory(Category category) {
		categoryFilterOperator().setSelectedItem(category);
	}

	private JComboBoxOperator categoryFilterOperator() {
		if (categoryFilterOperator == null)
			categoryFilterOperator = new JComboBoxOperator(mainWindow,
					new NameComponentChooser("categoryFilterCombo"));
		return categoryFilterOperator;
	}

	public void clickUpdate() {
		updateBtnOperator().push();

	}

	private JButtonOperator updateBtnOperator() {
		if (updateBtn == null)
			updateBtn = new JButtonOperator(mainWindow, "Update");
		return updateBtn;
	}

	public void assertDisplaysDialogWithText(String title, String string) {
		dialogOperator = new JDialogOperator(title);
		JLabelOperator labelOperator = new JLabelOperator(dialogOperator);

		assertEquals("Error dialog contains wrong label", string,
				labelOperator.getText());
	}

	public void clickOkInDialog() {
		JButtonOperator okBtn = new JButtonOperator(dialogOperator);
		okBtn.doClick();
	}

	public void assertMoviesSize(int size) {
		JListOperator listOperator = new JListOperator(mainWindow);

		assertEquals(size, listOperator.getModel().getSize());
	}

	public void updateMovie(int movieIndex, Movie movie)
			throws UnratedMovieException {
		selectMovie(movieIndex);
		enterMovieName(movie.getName());
		selectCategory(movie.getCategory());
		selectRating(movie.getRating());
		clickUpdate();
	}

	public void tearDown() {
		mainWindow.dispose();
		JemmyProperties.setCurrentTimeout(
				"ComponentOperator.WaitComponentTimeout",
				backupWaitComponentTimeout);
		JemmyProperties.setCurrentTimeout("DialogWaiter.WaitDialogTimeout",
				backupWaitDialogTimeout);
	}

	public void setup() {
		backupWaitComponentTimeout = JemmyProperties
				.getCurrentTimeout("ComponentOperator.WaitComponentTimeout");
		JemmyProperties.setCurrentTimeout(
				"ComponentOperator.WaitComponentTimeout", 2000L);
		backupWaitDialogTimeout = JemmyProperties
				.getCurrentTimeout("DialogWaiter.WaitDialogTimeout");
		JemmyProperties.setCurrentTimeout("DialogWaiter.WaitDialogTimeout",
				4000L);
	}

	public MovieListEditorView getView() {
		return (SwingMovieListEditorView) mainWindow.getWindow();
	}

	public void saveAs(File outputFile) {
		pushSaveAsNoBlock();

		chooseFile(outputFile, "Save");

	}

	private void chooseFile(File outputFile, String btnAction) {
		JFileChooserOperator fileChooserOperator = new JFileChooserOperator();
		fileChooserOperator.setSelectedFile(outputFile);
		JButtonOperator saveBtnOperator = new JButtonOperator(
				fileChooserOperator, btnAction);
		saveBtnOperator.push();
	}

	private JMenuBarOperator menuBarOperator() {
		if (menuBarOperator == null)
			menuBarOperator = new JMenuBarOperator(mainWindow);
		return menuBarOperator;
	}

	public void save() {
		menuBarOperator().pushMenu("File|Save", "|");
	}

	public void pushSaveAs() {
		menuBarOperator().pushMenu("File|SaveAs", "|");
	}

	public void pushSaveAsNoBlock() {
		menuBarOperator().pushMenuNoBlock("File|SaveAs", "|");
	}

	public void open(File outputFile) {
		menuBarOperator().pushMenuNoBlock("File|Open", "|");
		chooseFile(outputFile, "Open");

	}

	public void openAndCancel() {
		menuBarOperator().pushMenuNoBlock("File|Open", "|");
		chooseFile(null, "Cancel");

	}

	public void setDisplayMovies(Vector<Movie> movies) {
		getView().setMovies(movies);

	}

	public void orderByName() {
		menuBarOperator().pushMenu("View|Sort by name", "|");

	}

	public void orderByRating() {
		menuBarOperator().pushMenu("View|Sort by rating", "|");
	}

	public void assertRatingDisplayedEqualTo(ArrayList<Rating> ratings) {
		JListOperator ratingList = new JListOperator(mainWindow,
				new NameComponentChooser("ratingList"));
		ListModel<Rating> listModel = ratingList.getModel();
		MoviesAssert.assertRatingsEqualListModel(ratings, listModel);
	}

	public void assertMoviesDisplayedEqualTo(MovieList movieList) throws UnratedMovieException {
		Vector<Movie> movies = new Vector<>();
		for(Movie movie: movieList.getMovies()){
			movies.add(movie);
		}
		assertMoviesDisplayedEqualTo(movies);
		
	}

}
