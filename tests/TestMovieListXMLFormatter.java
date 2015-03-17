package tests;

import static org.junit.Assert.*;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;

import src.core.MovieList;
import src.core.MovieListXMLFormatter;

public class TestMovieListXMLFormatter {
	@Before
	public void setup(){
		String parser = "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl";
		XMLUnit.setControlParser(parser);
		XMLUnit.setTestParser(parser);
	}
	@Test
	public void testEmptyListReturnEmptyMovieListXML() {
		MovieListXMLFormatter xmlFormatter = new  MovieListXMLFormatter();
		String xmlResult="<movielist></movielist>";
		assertEquals(xmlResult, xmlFormatter.fileFormat(new MovieList()));
	}

}
