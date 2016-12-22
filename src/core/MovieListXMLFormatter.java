package src.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import src.core.exceptions.DuplicateMovieException;
import src.core.exceptions.InvalidFileFormatException;

public class MovieListXMLFormatter implements MovieListFileFormatter {
	MessageFormat movieFormat = new MessageFormat(
			"<movie name =\"{0}\" category =\"{1}\">");
	MessageFormat ratingFormat = new MessageFormat(
			"<rating value= \"{0,number,integer}\" source=\"{1}\"/>");

	@Override
	public String fileFormat(MovieList movieList) {
		String result = "<movielist>";
		if (movieList.size() > 0) {
			Iterator<Movie> iterator = movieList.iterator();
			while (iterator.hasNext()) {
				result += movieFormat(iterator.next());
			}
		}
		result += "</movielist>";
		return result;
	}

	private String movieFormat(Movie movie) {
		String movieArgs[] = { movie.getName(), movie.getCategory().toString() };
		String result = movieFormat.format(movieArgs);
		result += ratingListFormat(movie.getRatings());
		result += "</movie>";
		return result;
	}

	private String ratingListFormat(ArrayList<Rating> ratingList) {
		Iterator<Rating> iterator = ratingList.iterator();
		String result = "<ratings>";
		while (iterator.hasNext()) {
			Rating rating = iterator.next();
			result += ratingFormat(rating);
		}
		result += "</ratings>";
		return result;
	}

	private String ratingFormat(Rating rating) {
		Object ratingArgs[] = { rating.getValue(), rating.getSource() };
		return ratingFormat.format(ratingArgs);

	}

	@Override
	public MovieList toMoviesList(String formattedMoviesText)
			throws NumberFormatException, DuplicateMovieException,
			InvalidFileFormatException {
		MovieList movieList = new MovieList();
		try {
			Document document = getDOM(formattedMoviesText);
			for (int i = 0; i < getNumberOfMovies(document); i++) {
				Element movie = getMovieElement(document, i);
				Movie returnedMovie = convertFromMovieElement(movie);
				movieList.add(returnedMovie);
			}
			return movieList;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new InvalidFileFormatException();
		}

	}

	private int getNumberOfMovies(Document document) {
		return document.getDocumentElement().getChildNodes().getLength();
	}

	private Rating convertFromRatingElement(Element ratingElement) {
		return new Rating(getRatingValue(ratingElement),
				getRatingSource(ratingElement));
	}

	private int getRatingValue(Element ratingElement) {
		return Integer.parseInt(ratingElement.getAttribute("value"));
	}

	private String getRatingSource(Element ratingElement) {
		return ratingElement.getAttribute("source");
	}

	private Element getRatingElement(Element movie, int i) {
		Element ratingElement = (Element) getRatingNodes(movie).item(i);
		return ratingElement;
	}

	private Movie convertFromMovieElement(Element movie) {
		Movie toReturn = new Movie(movie.getAttribute("name"),
				Category.make(movie.getAttribute("category")));
		for (int i = 0; i < getRatingNodes(movie).getLength(); i++) {
			Element ratingElement = getRatingElement(movie, i);
			toReturn.addRating(convertFromRatingElement(ratingElement));
		}
		return toReturn;
	}

	private NodeList getRatingNodes(Element movie) {
		return movie.getFirstChild().getChildNodes();
	}

	private Element getMovieElement(Document document, int i) {
		NodeList movieNodes = document.getDocumentElement().getChildNodes();
		Element movie = (Element) movieNodes.item(i);
		return movie;
	}

	private Document getDOM(String formattedMoviesText)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document document = builder.parse(new ByteArrayInputStream(
				formattedMoviesText.getBytes()));
		return document;
	}

}
