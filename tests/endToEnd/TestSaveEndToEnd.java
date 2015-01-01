package tests.endToEnd;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

import src.core.Category;
import src.core.Movie;
import src.core.MovieListEditor;
import src.ui.SwingMovieListEditorView;
import testUtils.FileAssertor;
import tests.TestSettingupView;

public class TestSaveEndToEnd extends TestSettingupView{
	File outputFile;
	String expectedText;
	//starWars = new Movie("Star Wars", Category.SCIFI, 5);
	//starTrek = new Movie("Star Trek", Category.SCIFI, 4);
//	stargate = new Movie("Stargate", Category.HORROR, 3);
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		outputFile = new File("");
		expectedText = "";//write expected output
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		//remove file
		
	}

	@Test
	public void test() {
		mainWindow = new JFrameOperator("Movie List");
		MovieListEditor editor = new MovieListEditor(movieList,
				(SwingMovieListEditorView) mainWindow.getWindow());
		JMenuBarOperator menuBarOperator = new JMenuBarOperator(mainWindow);
		JMenuOperator fileMenuOperator = new JMenuOperator(menuBarOperator, "File");
		fileMenuOperator.push();
		JMenuItemOperator saveAsOperator = new JMenuItemOperator(fileMenuOperator, "SaveAs");
		saveAsOperator.doClick();
		
		JFileChooserOperator fileChooserOperator = new JFileChooserOperator();
		fileChooserOperator.accept(outputFile);
		
		FileAssertor.assertEqualFile(expectedText, outputFile);
	}

}
