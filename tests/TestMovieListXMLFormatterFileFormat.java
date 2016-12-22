package tests;

import java.io.IOException;

import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import src.core.Category;
import src.core.Movie;
import src.core.MovieList;
import src.core.MovieListXMLFormatter;
import src.core.Rating;
import src.core.exceptions.DuplicateMovieException;

public class TestMovieListXMLFormatterFileFormat extends XMLTestCase {

	@Before
	public void setup() {
		String parser = "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl";
		XMLUnit.setControlParser(parser);
		XMLUnit.setTestParser(parser);
	}

	// Test 90: Writing an empty list outputs an empty <movielist> pair
	@Test
	public void testEmptyListReturnEmptyMovieListXML() throws SAXException,
			IOException {
		MovieListXMLFormatter xmlFormatter = new MovieListXMLFormatter();
		String xmlResult = "<movielist></movielist>";
		assertXMLEqual(xmlResult, xmlFormatter.fileFormat(new MovieList()));
	}

	// Test 91: Writing a list containing one movie outputs in the adopted XML
	// format
	@Test
	public void testListContainingOneMovieReturnFormattedXML()
			throws SAXException, IOException, DuplicateMovieException {
		MovieListXMLFormatter xmlFormatter = new MovieListXMLFormatter();
		MovieList movieList = new MovieList();
		movieList.add(new Movie("Star Trek", Category.HORROR, new Rating(4,
				"NY Times")));
		String xmlResult = "<movielist>" + "<movie name =\"Star Trek\""
				+ " category =\"Horror\">" + "<ratings>"
				+ "<rating value =\"4\"" + " source=\"NY Times\"/>"
				+ "</ratings>" + "</movie>" + "</movielist>";
		assertXMLEqual("Wrong output for writing a single movie list",
				xmlResult, xmlFormatter.fileFormat(movieList));
	}
	// Test 92: Writing a list containing one movie with multiple ratings
	// outputs in the adopted XML format
	@Test
	public void testListContainingOneMovieWithMultipleRatingsFormattedXml() throws DuplicateMovieException, SAXException, IOException{
		MovieListXMLFormatter xmlFormatter = new MovieListXMLFormatter();
		MovieList movieList = new MovieList();
		movieList.add(new Movie("Star Trek", Category.HORROR, new Rating(4,
				"NY Times")));
		movieList.get(0).addRating(new Rating(3, "El Pais"));
		String xmlResult = "<movielist>" + "<movie name =\"Star Trek\""
				+ " category =\"Horror\">" + "<ratings>"
				+ "<rating value =\"4\"" + " source=\"NY Times\"/>"
				+ "<rating value =\"3\"" + " source=\"El Pais\"/>"
				+ "</ratings>" + "</movie>" + "</movielist>";
		assertXMLEqual("Wrong output for writing a single movie list",
				xmlResult, xmlFormatter.fileFormat(movieList));
	}
	//Test 93: Writing a list containing multiple movies outputs in the adopted XML	format
	@Test
	public void testListContainingMultipleMovies() throws DuplicateMovieException, SAXException, IOException{
		MovieListXMLFormatter xmlFormatter = new MovieListXMLFormatter();
		MovieList movieList = new MovieList();
		movieList.add(new Movie("Star Trek", Category.HORROR, new Rating(4,
				"NY Times")));
		movieList.add(new Movie("Star Wars", Category.HORROR, new Rating(5,
				"El Mundo")));
		String xmlResult = "<movielist>" + "<movie name =\"Star Trek\""
				+ " category =\"Horror\">" + "<ratings>"
				+ "<rating value =\"4\"" + " source=\"NY Times\"/>"
				+ "</ratings>" + "</movie>";
		xmlResult+= "<movie name =\"Star Wars\""
				+ " category =\"Horror\">" + "<ratings>"
				+ "<rating value =\"5\"" + " source=\"El Mundo\"/>"
				+ "</ratings>" + "</movie>" + "</movielist>";
		assertXMLEqual("Wrong output for writing a single movie list",
				xmlResult, xmlFormatter.fileFormat(movieList));
	}
}
