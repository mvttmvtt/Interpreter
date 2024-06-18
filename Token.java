package Lexer;
/**
 * This file creates multiple token types
 * @author Matthew Welsh
 */
public class Token {
	
	/*
	 * This creates a variable that may be any one
	 * of a list of token types.
	 */
	public enum tokenType {
		NUMBER, IDENTIFIER, ENDOFLINE, NEWLINE,
		
		//part 2
		START, END, IF, THEN, ELSE, ELSIF, WHILE, FOR, FROM, TO, REPEATUNTIL, DEFINE, WRITE, CASE, 
		THROW, EQUALS, LESSTHAN, GREATERTHAN, VARIABLES, CONSTANTS, VAR, CONSTANT, FLOAT, REAL, STRING, INTEGER, CHAR,
		BOOLEAN, TRUE, Real, Integer, FALSE, EQUAL, PLUS, MINUS, MULTIPLY, DIVIDE, COLON, NOT, PLUSPLUS, MINUSMINUS, COMMA, SEMICOLON,
		MODULO, COMPARISON, GREATEROREQUAL, LESSOREQUAL, NOTEQUAL, LEFT, RIGHT, ASSIGNMENT, ARRAY,
		SUBSTRING, SQUAREROOT, GETRANDOM, INTEGERTOREAL, REALTOINTEGER, READ, LEFTPAREN, RIGHTPAREN,
		
		STRINGLITERAL, CHARACTERLITERAL, StrValue, CharVal,
		
		COMMENT, ParenOp, INDENT, DEDENT
	}
	
	/*
	 * Private tokeType member
	 */
	private tokenType t;
	
	/*
	 * Private String member
	 */
	private String s;
	
	/*
	 * Token constructor that initializes the s variable
	 */
	public Token (String s) {
		this.s = s;
	}
	
	/*
	 * Token constructor that initializes the t variable
	 */
	public Token (tokenType t) {
		this.t = t;
	}
	
	/*
	 * Token constructor that initializes the s & t variable
	 */
	public Token (tokenType t, String s) {
		this.t = t;
		this.s = s;
	}
	
	/*
	 * An accessor that allows the t variable to be called in
	 * another file
	 */
	public tokenType getType() {
		return t;
	}
	
	/*
	 * An accessor that allows the s variable to be called in
	 * another file
	 */
	public String getValue() {
		return s;
	}
	
	/*
	 * A mutator which sets a tokenType to another
	 */
	public void setType(tokenType type) {
		t = type;
	}
	
	/*
	 * A mutator which sets a tokenType to another
	 */
	public void setName(String name) {
		s = name;
	}
	
	/*
	 * This overrides the built-in toString method
	 * and returns specific outputs
	 */
	public String toString() {
		
		if (t == tokenType.ENDOFLINE) {
			return "ENDOFLINE\n";
		}
		
		if (t == tokenType.INDENT) {
			return "INDENT";
		}
		
		if (t == tokenType.DEDENT) {
			return "DEDENT";
		}
		
		if (t == tokenType.LEFTPAREN) {
			return "LEFTPAREN '('";
		}
		
		if (t == tokenType.RIGHTPAREN) {
			return "RIGHTPAREN ')'";
		}
		
		return t + " (" + s + ")"; 
	}
}
