package tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import src.core.Category;
import src.core.Movie;

public class TestReadFromXMLDocument {

	@Test
	public void testReadingFromDoc() {
		String oneMovieXML = "<movielist>" + "<movie name =\"Star Trek\""
				+ " category =\"Horror\">" + "<ratings>"
				+ "<rating value =\"4\"" + " source=\"NY Times\"/>"
				+ "</ratings>" + "</movie>" + "</movielist>";
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new ByteArrayInputStream(oneMovieXML
					.getBytes()));
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			Element element = (Element) nodeList.item(0);
			assertEquals("Star Trek", element.getAttribute("name"));
			assertEquals(Category.HORROR.toString(), element.getAttribute("category"));
			Element rating = (Element) element.getFirstChild().getChildNodes().item(0);
			assertEquals("4", rating.getAttribute("value"));
			Movie movie = new Movie(element.getAttribute("name"), Category.make(element.getAttribute("category")));
			//movie.addRating(new Rating(element.))
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
