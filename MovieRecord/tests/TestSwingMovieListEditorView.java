package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ListModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListEditor;
import src.ui.MovieListEditorView;
import src.ui.SwingMovieListEditorView;

public class TestSwingMovieListEditorView {
	private JFrameOperator mainWindow;
	private Vector<Movie> movies = null;
	private Movie starWars = null;
	private Movie starTrek = null;
	private Movie stargate = null;
	private MovieList movieList = null;
	private MovieListEditorView mockEditor;
	private long backupWaitComponentTimeout;
	private long backupWaitDialogTimeout;

	@Before
	public void setUp() throws Exception {
		SwingMovieListEditorView.start();
		starWars = new Movie("Star Wars");
		starTrek = new Movie("Star Trek");
		stargate = new Movie("Stargate");
		movies = new Vector<Movie>();
		movies.add(starWars);
		movies.add(starTrek);
		movies.add(stargate);
		movieList = new MovieList();
		movieList.add(starWars);
		movieList.add(starTrek);
		movieList.add(stargate);
		backupWaitComponentTimeout = JemmyProperties
				.getCurrentTimeout("ComponentOperator.WaitComponentTimeout");
		JemmyProperties.setCurrentTimeout(
				"ComponentOperator.WaitComponentTimeout", 2000L);
		backupWaitDialogTimeout = JemmyProperties
				.getCurrentTimeout("DialogWaiter.WaitDialogTimeout");
		JemmyProperties.setCurrentTimeout("DialogWaiter.WaitDialogTimeout",
				4000L);
	}

	@After
	public void tearDown() throws Exception {
		mainWindow.dispose();
		JemmyProperties.setCurrentTimeout(
				"ComponentOperator.WaitComponentTimeout",
				backupWaitComponentTimeout);
		JemmyProperties.setCurrentTimeout("DialogWaiter.WaitDialogTimeout",
				backupWaitDialogTimeout);
	}

	@Test
	public void testListContents() {
		mainWindow = new JFrameOperator("Movie List");
		MovieListEditor editor = new MovieListEditor(movieList,
				(SwingMovieListEditorView) mainWindow.getWindow());
		JListOperator movieList = new JListOperator(mainWindow);
		ListModel<Movie> listModel = movieList.getModel();
		assertEquals("Movie list is the wrong size", movies.size(),
				listModel.getSize());
		for (int i = 0; i < movies.size(); i++) {
			assertEquals("Movie list contains bad movie at index " + i,
					movies.get(i), listModel.getElementAt(i));
		}
	}

	// Test 8. The GUI should have a field for the movie name and an add button.
	// It should answer the contents of
	// the name field when asked, and request that the logical layer add a movie
	// when the add button is pushed.
	@Test
	public void testAdding() {
		Movie newMovie = new Movie("New Movie");
		movies.add(newMovie);
		mainWindow = new JFrameOperator("Movie List");
		MovieListEditor editor = new MovieListEditor(movieList,
				(SwingMovieListEditorView) mainWindow.getWindow());
		JTextFieldOperator movieField = new JTextFieldOperator(mainWindow);
		movieField.enterText("New Movie");
		JButtonOperator addButton = new JButtonOperator(mainWindow, "Add");
		addButton.doClick();

		JListOperator movieList = new JListOperator(mainWindow);
		ListModel<Movie> listModel = movieList.getModel();
		for (int i = 0; i < movies.size(); i++) {
			assertEquals("Movie list contains bad movie at index " + i,
					movies.get(i), listModel.getElementAt(i));
		}
	}

	/*
	 * Test 15: Selecting from the list causes the name field to be filled in
	 * with the selected movie's name
	 */
	@Test
	public void testWhenSelectFromTheListFillsTheNameFieldWithTheSelectedMovie() {
		mainWindow = new JFrameOperator("Movie List");
		MovieListEditor editor = new MovieListEditor(movieList,
				(SwingMovieListEditorView) mainWindow.getWindow());
		JListOperator listOperator = new JListOperator(mainWindow);
		JTextFieldOperator newMovieField = new JTextFieldOperator(mainWindow);
		listOperator.clickOnItem(0, 1);
		assertEquals("wrong text from selection", "Star Wars",
				newMovieField.getText());
		listOperator.clickOnItem(1, 1);
		assertEquals("wrong text from selection", "Star Trek",
				newMovieField.getText());
		listOperator.clickOnItem(2, 1);
		assertEquals("wrong text from selection", "Stargate",
				newMovieField.getText());
	}

	/*
	 * Test 17: When the update button is pushed, the selected movie is renamed
	 * to whatever is in the name field
	 */
	@Test
	public void testUpdate() {
		mainWindow = new JFrameOperator("Movie List");
		MovieListEditor editor = new MovieListEditor(movieList,
				(SwingMovieListEditorView) mainWindow.getWindow());
		JListOperator movieListOperator = new JListOperator(mainWindow);
		movieListOperator.clickOnItem(1, 1);
		JTextFieldOperator movieNameTxtField = new JTextFieldOperator(
				mainWindow);
		movieNameTxtField.setText("Star Trek (updated)");
		JButtonOperator updateBtn = new JButtonOperator(mainWindow, "Update");
		updateBtn.doClick();

		movieListOperator.clickOnItem(0, 1);
		assertEquals("Star Wars", movieNameTxtField.getText());
		movieListOperator.clickOnItem(2, 1);
		assertEquals("Stargate", movieNameTxtField.getText());
		movieListOperator.clickOnItem(1, 1);
		assertEquals("Star Trek (updated)", movieNameTxtField.getText());
	}

	/*
	 * Test 22. Trying to add a movie that is the same as one in the list
	 * results in the display of a "Duplicate Movie" error dialog.
	 */
	@Test
	public void testAddingADuplicateMovieDisplaysErrorDialog() {
		mainWindow = new JFrameOperator("Movie List");
		MovieListEditor editor = new MovieListEditor(movieList,
				(SwingMovieListEditorView) mainWindow.getWindow());

		JTextFieldOperator movieNameTextField = new JTextFieldOperator(
				mainWindow);
		movieNameTextField.setText("Stargate");
		JButtonOperator addBtn = new JButtonOperator(mainWindow, "Add");
		addBtn.pushNoBlock();

		JDialogOperator dialogOperator = new JDialogOperator("Duplicate Movie");
		JLabelOperator labelOperator = new JLabelOperator(dialogOperator);

		assertEquals("Duplicated error dialog contains wrong label",
				"Adding this movie will result in a duplicate movie",
				labelOperator.getText());

		JButtonOperator okBtn = new JButtonOperator(dialogOperator);
		okBtn.doClick();
		JListOperator listOperator = new JListOperator(mainWindow);

		assertEquals(movies.size(), listOperator.getModel().getSize());
	}
	/*
	 * Test 23: Trying to rename a movie to the name of one in the list results
	 * in the display of a "Duplicate Movie" error dialog
	 */
	@Test
	public void testUpdatingDuplicateMovieDisplaysErroDialog(){
		mainWindow = new JFrameOperator("Movie List");
		MovieListEditor editor = new MovieListEditor(movieList,
				(SwingMovieListEditorView) mainWindow.getWindow());

		JListOperator listOperator = new JListOperator(mainWindow);
		listOperator.clickOnItem(1,1);
		JTextFieldOperator movieNameTextField = new JTextFieldOperator(
				mainWindow);
		movieNameTextField.setText(stargate.getName());
		JButtonOperator updateBtn = new JButtonOperator(mainWindow, "Update");
		updateBtn.pushNoBlock();

		JDialogOperator dialogOperator = new JDialogOperator("Duplicate Movie");
		JLabelOperator labelOperator = new JLabelOperator(dialogOperator);

		assertEquals("Duplicated error dialog contains wrong label",
				"Adding this movie will result in a duplicate movie",
				labelOperator.getText());

		JButtonOperator okBtn = new JButtonOperator(dialogOperator);
		okBtn.doClick();

		assertEquals(movies.size(), listOperator.getModel().getSize());
	}

}