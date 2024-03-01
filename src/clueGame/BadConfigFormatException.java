package clueGame;

public class BadConfigFormatException extends Exception{
	// constructor that accepts msg
	public BadConfigFormatException(String message) {
        super(message);
    }
	public BadConfigFormatException(String message, Throwable cause) {
        super(message, cause);
    }
	// additional as needed
}
