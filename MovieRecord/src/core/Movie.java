package src.core;

public class Movie {
	String name;

	public Movie(String name) {
		if(name==null || name=="")
			throw new IllegalArgumentException();
		this.name = name;
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
		if(name ==null|| name.isEmpty())
			throw new IllegalArgumentException();
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
