package tests.endToEnd;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import src.core.MovieListEditor;
import src.core.exceptions.DuplicateMovieException;
import testUtils.FileAssertor;

public class TestSaveMovieList extends TestPersistance {
	@Override
	@Before
	public void setUp() throws DuplicateMovieException, IOException{
		super.setUp();
	}

	@Test
	public void testSaveAsSavesWithExpectedContent() throws DuplicateMovieException, IOException,
			SAXException {
		
		MovieListEditor editor = new MovieListEditor(movieList,
				appRunner.getView());
		
		
		appRunner.saveAs(outputFile);
		assertXMLEqual(fileContent, FileAssertor.getFileContent(outputFile));
	}
}
