package src.core;

public enum Category {
	ALL("All"), UNCATEGORIZED("Uncategorized"), SCIFI("Science Fiction"), HORROR(
			"Horror");
	private String name;

	Category(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static Category make(String categoryName) {
		if (categoryName.equals(ALL.toString()))
			return ALL;
		else if (categoryName.equals(UNCATEGORIZED.toString()))
			return UNCATEGORIZED;
		else if (categoryName.equals(SCIFI.toString()))
			return SCIFI;
		else
			return HORROR;

	}

}
