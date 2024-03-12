package clueGame;

/**
 * BadConfigFormatException Class - This class handles exceptions. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/8/2024
 * 
 */

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
