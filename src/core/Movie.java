package src.core;

import java.util.ArrayList;

import src.core.exceptions.UnratedMovieException;

public class Movie {
	String name;
	ArrayList<Rating> ratings = new ArrayList<Rating>();
	private Category category;

	public Movie(String name) {
		this(name, -1);
	}

	public Movie(String name, int rate) {
		this(name, Category.UNCATEGORIZED, rate);
	}

	public Movie(String name, Category category, int rating) {
		if (name == null || name.equals(""))
			throw new IllegalArgumentException();
		this.name = name;
		if (rating != -1)
			this.ratings.add(new Rating(rating));
		this.category = category;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Movie other = (Movie) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void rename(String name) {
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isRated() {
		return ratings.size() > 0;
	}

	public int getRating() throws UnratedMovieException {
		if (!isRated())
			throw new UnratedMovieException("This movie has not been rated");
		int ratingSum=0;
		for(Rating rating: ratings){
			ratingSum+= rating.getValue();
		}
		return ratingSum/ratings.size();
	}
	public int getRawRating() {
		if(!isRated())
			return -1;
		else
			try {
				return getRating();
			} catch (UnratedMovieException e) {
				e.printStackTrace();
			}
		return 0;
					
	}

	public void setRating(int rating) {
		this.ratings.clear();
		this.ratings.add(new Rating(rating));
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void addRating(Rating rating) {
		this.ratings.add(rating);
		
	}
}
