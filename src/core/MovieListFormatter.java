package src.core;

import src.core.exceptions.UnratedMovieException;

public class MovieListFormatter {


	public String fileFormat(MovieList movieList) {
		String result ="";
		for(Movie movie: movieList.getMovies()){
			result+= movieToFileFormat(movie);
		}
		return result;
		
	}

	private String movieToFileFormat(Movie movie){
		try {
			return movie.name+"|"+movie.getCategory()+"|"+ movie.getRating()+"\n";
		} catch (UnratedMovieException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
