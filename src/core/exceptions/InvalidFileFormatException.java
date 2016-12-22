package src.core.exceptions;

public class InvalidFileFormatException extends Exception {

	public InvalidFileFormatException(String message) {
		super(message);
	}

	public InvalidFileFormatException(int fileLine, String line) {
		super(
				"The file format is not valid please make sure each movie is in a line and each attribute is separated by '|': movie name | category | rating");
	}

	public InvalidFileFormatException() {
		super(
				"The file format is not valid please make sure each movie is in a line and each attribute is separated by '|': movie name | category | rating");
	}
}
