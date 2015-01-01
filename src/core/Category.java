package src.core;

public enum Category {
	ALL("All"), UNCATEGORIZED("Uncategorized"), SCIFI("Science Fiction"), HORROR("Horror");
	private String name;

	Category(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
