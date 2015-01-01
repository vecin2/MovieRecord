package tests;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.JFrameOperator;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.ui.SwingMovieListEditorView;

public class TestSettingupView {

	protected JFrameOperator mainWindow;
	protected Vector<Movie> movies = null;
	protected Movie starWars = null;
	protected Movie starTrek = null;
	protected Movie stargate = null;
	protected MovieList movieList = null;
	private long backupWaitComponentTimeout;
	private long backupWaitDialogTimeout;

	public TestSettingupView() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		SwingMovieListEditorView.start();
		starWars = new Movie("Star Wars", Category.SCIFI, 5);
		starTrek = new Movie("Star Trek", Category.SCIFI, 4);
		stargate = new Movie("Stargate", Category.HORROR, 3);
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

}