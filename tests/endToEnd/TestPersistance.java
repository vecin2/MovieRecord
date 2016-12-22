package tests.endToEnd;

import java.io.File;
import java.io.IOException;

import org.custommonkey.xmlunit.XMLTestCase;
import org.junit.Before;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.Rating;
import src.core.exceptions.DuplicateMovieException;
import src.ui.SwingMovieListEditorView;
import testUtils.ApplicationRunner;

public class TestPersistance extends XMLTestCase {

	protected String fileContent;
	protected MovieList movieList;
	protected ApplicationRunner appRunner;
	protected File outputFile;

	@Before
	public void setUp() throws DuplicateMovieException, IOException {
		SwingMovieListEditorView.start();
		fileContent = "<movielist>" + "<movie name =\"Star Wars\""
				+ " category =\"" + Category.SCIFI + "\">" + "<ratings>"
				+ "<rating value= \"3\"" + " source=\"user1\"/>" + "</ratings>"
				+ "</movie>";
		fileContent += "<movie name =\"Star Trek\"" + " category =\""
				+ Category.SCIFI + "\">" + "<ratings>" + "<rating value= \"4\""
				+ " source=\"user2\"/>" + "</ratings>" + "</movie>";
		fileContent += "<movie name =\"Stargate\"" + " category =\""
				+ Category.HORROR + "\">" + "<ratings>"
				+ "<rating value= \"5\"" + " source=\"user3\"/>" + "</ratings>"
				+ "</movie></movielist>";
		Movie starWars = new Movie("Star Wars", Category.SCIFI, new Rating(3,
				"user1"));
		Movie starTrek = new Movie("Star Trek", Category.SCIFI, new Rating(4,
				"user2"));
		Movie stargate = new Movie("Stargate", Category.HORROR, new Rating(5,
				"user3"));
		movieList = new MovieList();
		movieList.add(starWars);
		movieList.add(starTrek);
		movieList.add(stargate);

		outputFile = File.createTempFile("testSave", ".dat");
		
		appRunner = new ApplicationRunner("Movie List");
		appRunner.setup();
		
	}

}
