package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import src.core.Movie;
import static org.mockito.Mockito.*;

public class TestMockito {

	@Test
	public void testWhenParameterPassedTwiceWithDifferentValuesReturnTimesTwoWhenCheckingBySecondValue() {
		ArrayList<Movie> mockedArray = mock(ArrayList.class);
		
		Movie movie = new Movie("Title");
		mockedArray.add(movie);
		verify(mockedArray).add(new Movie("Title"));
		movie.rename("Title 2");
		mockedArray.add(movie);
		verify(mockedArray, times(2)).add(new Movie("Title 2"));
	}

}
