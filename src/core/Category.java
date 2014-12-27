package src.core;

public enum Category {
	UNCATEGORIZED("Uncategorized"), SCIFI("Science Fiction"), HORROR("Horror");
	private String name;

	Category(String name) {
		this.name = name;
	}
}
