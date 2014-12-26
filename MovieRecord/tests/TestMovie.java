package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import src.core.Movie;

public class TestMovie {
	Movie movie;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Test
	public void testRename() {
		movie =new Movie("Star Trek");
		movie.rename("Indiana Jones");
		assertEquals("Indiana Jones", movie.toString());
	}
	@Test
	public void testMovieCantBeConstructedWithANullName(){
		thrown.expect(IllegalArgumentException.class);
		movie = new Movie(null);		 
	}
	@Test
	public void testMovieCantBeConstructedWithAEmptyName(){
		thrown.expect(IllegalArgumentException.class);
		movie = new Movie("");		 
	}
	@Test
	public void testMovieCantBeRenamedWithANullName(){
		thrown.expect(IllegalArgumentException.class);
		movie = new Movie("Start Trek");
		movie.rename(null);
	}
	@Test
	public void testMovieCantBeRenamedWithAEmptyName(){
		thrown.expect(IllegalArgumentException.class);
		movie = new Movie("Start Trek");
		movie.rename("");
	}
}
