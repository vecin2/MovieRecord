package src.core;

public class Rating {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + value;
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
		Rating other = (Rating) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	private int value;
	private String source;

	public Rating(int value, String source) {
		this.value = value;
		this.source = source;
	}

	@Override
	public String toString() {
		return "Rating [value=" + value + ", source=" + source + "]";
	}

	public Rating(int rating) {
		this(rating, "");
	}

	public int getValue() {
		return value;
	}

	public String getSource() {
		return source;
	}

}
