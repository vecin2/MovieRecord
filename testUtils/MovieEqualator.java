package testUtils;

import src.core.Movie;

public class MovieEqualator {
	Movie movie;

	public MovieEqualator(Movie movie) {
		this.movie = movie;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((movie == null) ? 0 : movie.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieEqualator other = (MovieEqualator) obj;
		if (movie == null) {
			if (other.movie != null)
				return false;
		} else if (!movie.getName().equals(other.movie)
				|| !movie.getCategory().equals(other.movie.getCategory())
				|| movie.getRawRating() != other.movie.getRawRating())
			return false;
		return true;
	}

}
