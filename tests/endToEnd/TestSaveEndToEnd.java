package tests.endToEnd;

import java.io.File;
import java.io.IOException;

import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import src.core.Category;
import src.core.MovieList;
import src.core.MovieListEditor;
import src.ui.SwingMovieListEditorView;
import testUtils.ApplicationRunner;
import testUtils.FileAssertor;
import tests.TestSettingupView;

public class TestSaveEndToEnd extends TestSettingupView {
	File outputFile;
	String expectedText = "";

	// Movie List:
	// starWars = new Movie("Star Wars", Category.SCIFI, 5);
	// starTrek = new Movie("Star Trek", Category.SCIFI, 4);
	// stargate = new Movie("Stargate", Category.HORROR, 3);

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		expectedText += "Star Wars|" + Category.SCIFI + "|5\n";
		expectedText += "Star Trek|" + Category.SCIFI + "|4\n";
		expectedText += "Stargate|" + Category.HORROR + "|3\n";
		outputFile = File.createTempFile("testSaveAs", ".dat");
		outputFile.deleteOnExit();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();

	}

	@Test
	public void testSave() throws IOException {
		SwingMovieListEditorView.start();
		ApplicationRunner appRunner = new ApplicationRunner("Movie List");
		
		MovieListEditor editor = new MovieListEditor(movieList,
				(SwingMovieListEditorView) appRunner.getView());

		appRunner.saveAs(outputFile);
		FileAssertor.assertEqualFile(expectedText, outputFile);
		
		appRunner.addMovie("Braveheart", Category.HORROR, 6);
		appRunner.save();

		String extendedExpectedText = expectedText + "Braveheart|Horror|5\n";
		FileAssertor.assertEqualFile(extendedExpectedText, outputFile);
	}
	@Test
	public void testLoad() throws IOException {
		//output
		SwingMovieListEditorView.start();
		ApplicationRunner appRunner = new ApplicationRunner("Movie List");
		
		MovieListEditor editor = new MovieListEditor(movieList,
				(SwingMovieListEditorView) appRunner.getView());

		appRunner.saveAs(outputFile);
		FileAssertor.assertEqualFile(expectedText, outputFile);
		
		appRunner.addMovie("Braveheart", Category.HORROR, 6);
		appRunner.save();

		String extendedExpectedText = expectedText + "Braveheart|Horror|5\n";
		FileAssertor.assertEqualFile(extendedExpectedText, outputFile);
	}

}
