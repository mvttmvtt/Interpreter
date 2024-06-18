package Lexer;

/**
 * An exception that is thrown when an unknown character is encountered
 * @author Matthew Welsh
 */
@SuppressWarnings("serial")
public class SyntaxErrorException extends Exception {

	/**
	 * Constructs an exception with a specified message. 
	 * @param message - The specified message of this exception
	 */
	public SyntaxErrorException (String message) {
		super(message);
	}
}
