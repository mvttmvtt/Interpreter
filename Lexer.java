package Lexer;
import java.util.ArrayList;
import java.util.HashMap;

import Lexer.SyntaxErrorException;
import Lexer.Token;
import Lexer.Token.tokenType;

/**
 * This file contains a state machine and method that 
 * accepts a single string and returns a list of tokens
 * @author Matthew Welsh
 * @param <tokenType>
 */
public class Lexer {
	
	static int previous = 0;
	
	/*
	 * This creates a variable that may be any one
	 * of a list of states in the state machine.
	 */
	public enum states {
		BeginState,      //state 1
		IdentifierState, //state 2
		DecimalState,    //state 3
		NumberState,     //state 4
		OperatorState,	 //state 5
		
		//Operator SubStates
		PLUSPLUS,
		MINUSMINUS,
		NOTEQUAL,
		GREATEROREQUAL,
		LESSOREQUAL,
		ASSIGNMENT,
		COLON,
		COMMA,
		
		STRINGLITERAL, CHARACTERLITERAL,
		
		COMMENT, ARRAY, SEMICOLON, LEFTPAREN, RIGHTPAREN
	}
	
	/**
	 * This method accepts a string variable and returns a specific output
	 * @param s - String variable
	 * @throws SyntaxErrorException
	 */
	public static ArrayList<Token> Lex (String s) throws SyntaxErrorException {
		
		/** 
		 * The Hashmap - used to store and locate specific string values
		 */
		HashMap<String, tokenType> knownWords = new HashMap<String, tokenType>();
		
			knownWords.put("Start", tokenType.START);
			knownWords.put("End", tokenType.END);
			knownWords.put("if", tokenType.IF);
			knownWords.put("then", tokenType.THEN);
			knownWords.put("else", tokenType.ELSE);
			knownWords.put("elsif", tokenType.ELSIF);
			knownWords.put("while", tokenType.WHILE);
			knownWords.put("for", tokenType.FOR);
			knownWords.put("from", tokenType.FROM);
			knownWords.put("to", tokenType.TO);
			knownWords.put("repeat until", tokenType.REPEATUNTIL);
			knownWords.put("define", tokenType.DEFINE);
			knownWords.put("read", tokenType.READ);
			knownWords.put("Left", tokenType.LEFT);
			knownWords.put("Right", tokenType.RIGHT);
			knownWords.put("Substring", tokenType.SUBSTRING);
			knownWords.put("SquareRoot", tokenType.SQUAREROOT);
			knownWords.put("GetRandom", tokenType.GETRANDOM);
			knownWords.put("IntegerToReal", tokenType.INTEGERTOREAL);
			knownWords.put("RealToInteger", tokenType.REALTOINTEGER);
			knownWords.put("write", tokenType.WRITE);
			knownWords.put("case", tokenType.CASE);
			knownWords.put("throw", tokenType.THROW);
			knownWords.put("mod", tokenType.MODULO);
			knownWords.put("variables", tokenType.VARIABLES);
			knownWords.put("constants", tokenType.CONSTANTS);
			knownWords.put("var", tokenType.VAR);
			knownWords.put("constant", tokenType.CONSTANT);
			knownWords.put("string", tokenType.STRING);
			knownWords.put("integer", tokenType.INTEGER);
			knownWords.put("real", tokenType.REAL);
			knownWords.put("char", tokenType.CHAR);
			knownWords.put("boolean", tokenType.BOOLEAN);
			knownWords.put("true", tokenType.TRUE);
			knownWords.put("false", tokenType.FALSE);

		ArrayList<Token> tokenlist= new ArrayList<Token>();
		
		states currentstate = states.BeginState;
		Token result = null;
		String acc = ""; //accumulator

		int currentIndent = getIndent(s);
		
		/*
		 * Handles Indentation and Dedentation
		 */
		int currentCopy = currentIndent;
		
		if(currentIndent > previous)
		{
			while (currentIndent > previous) {
				result = new Token(tokenType.INDENT, acc);
				tokenlist.add(result);
				System.out.println(result.toString());
				currentIndent--;
			}
		}
		else if(currentIndent < previous)
		{
			while (currentIndent < previous) {
					result = new Token(tokenType.DEDENT, acc);
					tokenlist.add(result);
					System.out.println(result.toString());
					previous--;
			}
		}
		
		previous = currentCopy;
	
		for (int i=0; i < s.length(); i++) {
			
			/*
			 * State Machine
			 */
			switch (currentstate) {
			
			//State 1
			case BeginState: 
				if (Character.isLetter(s.charAt(i))) {
					acc += s.charAt(i);
					currentstate = states.IdentifierState;	
				}
				else if (s.charAt(i) == '.') {
					acc += s.charAt(i);
					currentstate = states.DecimalState;
				}
				else if (Character.isDigit(s.charAt(i))) {	
					acc += s.charAt(i);
					currentstate = states.NumberState;
				}
				else if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == ',' 
						|| s.charAt(i) == '/' || s.charAt(i) == '=' || s.charAt(i) == ':' || s.charAt(i) == ';'
						|| s.charAt(i) == '!' || s.charAt(i) == '<' || s.charAt(i) == '>') {
					acc += s.charAt(i);
					currentstate = states.OperatorState;
					i--;
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '\t') {}
				
				else if (s.charAt(i) == '"') {
					while (s.charAt(i+1) != '"') {
						acc += s.charAt(i+1);
						i++;
					}
					currentstate = states.STRINGLITERAL;
				}
				else if (s.substring(i).contains("'")) {
					if (s.substring(i).length() <= 3) {
						acc += s.substring(i+1);
					} else {
						throw new SyntaxErrorException("Error: Character length cannot exceed 1!"); 
					}
					currentstate = states.CHARACTERLITERAL;
				}
				else if (s.charAt(i) == '{') {
					while (s.charAt(i+1) != '}') {
						acc += s.charAt(i+1);
						i++;
					}
					currentstate = states.COMMENT;
				}
				else if (s.charAt(i) == '[') {
					while (s.charAt(i+1) != ']') {
						acc += s.charAt(i+1);
						i++;
					}
					currentstate = states.ARRAY;
				}
				else if (s.charAt(i) == '(') {
					acc += s.charAt(i);
					currentstate = states.LEFTPAREN;
					i--;
				}
				else if (s.charAt(i) == ')') {
					acc += s.charAt(i);
					currentstate = states.RIGHTPAREN;
					i--;
				}
				else {
					throw new SyntaxErrorException("Unexpected character: " + s.charAt(i)); 
				}
				break;
				
			//State 2
			case IdentifierState:
				if (Character.isLetterOrDigit(s.charAt(i))) {
					acc += s.charAt(i);
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '(' || s.charAt(i) == ')' || s.charAt(i) == '['
						|| s.charAt(i) == ':' || s.charAt(i) == ';' || s.charAt(i) == ',' || s.charAt(i) == '{') {
					if (knownWords.containsKey(acc)) {
						result = new Token(knownWords.get(acc), acc);
						tokenlist.add(result);
						acc = "";
						System.out.println(result.toString());
						currentstate = states.BeginState;
					}
					else {
						result = new Token(tokenType.IDENTIFIER, acc);
						tokenlist.add(result);
						acc = "";
						System.out.println(result.toString());
						currentstate = states.BeginState;
					}
					if (s.charAt(i) == '[') {
						while (s.charAt(i+1) != ']') {
							acc += s.charAt(i+1);
							i++;
						}
						currentstate = states.ARRAY;
					}
					if (s.charAt(i) == '{') {
						while (s.charAt(i+1) != '}') {
							acc += s.charAt(i+1);
							i++;
						}
						currentstate = states.COMMENT;
					}
					if (s.charAt(i) == '(') {
						acc += s.charAt(i);
						currentstate = states.LEFTPAREN;
						i--;
					}
					if (s.charAt(i) == ')') {
						acc += s.charAt(i);
						currentstate = states.RIGHTPAREN;
						i--;
					}
					if (s.charAt(i) == ';') {
						acc += s.charAt(i);
						currentstate = states.OperatorState;
						i--;
					}
					if (s.charAt(i) == ':') {
						acc += s.charAt(i);
						currentstate = states.OperatorState;
						i--;
					}
					if (s.charAt(i) == ',') {
						acc += s.charAt(i);
						currentstate = states.OperatorState;
						i--;
					}
				}
				else if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' 
						|| s.charAt(i) == '/' || s.charAt(i) == '=' || s.charAt(i) == '!' 
						|| s.charAt(i) == '<' || s.charAt(i) == '>') {
					acc += s.charAt(i);
					currentstate = states.OperatorState;
					i--;
				}
				else {
					throw new SyntaxErrorException("Unexpected character: " + s.charAt(i));
				}
				break;
			
			//State 3
			case DecimalState:
				if (s.charAt(i) == '.' || Character.isDigit(s.charAt(i))) {
					acc += s.charAt(i);
					result = new Token(tokenType.NUMBER, acc);
					tokenlist.add(result);
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else if (s.charAt(i) == ' ') {
					acc = "";
					currentstate = states.NumberState;
				}
				else {
					throw new SyntaxErrorException("Unexpected character: " + s.charAt(i));
				}
				break;
			
			//State 4
			case NumberState: 
				if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.') {
					acc += s.charAt(i);
				}
				else if (s.charAt(i) == ' ' || s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' 
						|| s.charAt(i) == '/' || s.charAt(i) == '=' || s.charAt(i) == ':' || s.charAt(i) == ','
						|| s.charAt(i) == '!' || s.charAt(i) == '<' || s.charAt(i) == '>' || s.charAt(i) == ';') {
					
					result = new Token(tokenType.NUMBER, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
					i--;
					
					if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == ','
							|| s.charAt(i) == '/' || s.charAt(i) == '=' || s.charAt(i) == ':' 
							|| s.charAt(i) == '!' || s.charAt(i) == '<' || s.charAt(i) == '>' || s.charAt(i) == ';') {
						acc += s.charAt(i);
						currentstate = states.OperatorState;
						i--;
					} //
				}
				else if (s.charAt(i) == '.') {
					currentstate = states.DecimalState;
				}
				else {
					throw new SyntaxErrorException("Unexpected character: " + s.charAt(i));
				}
				break;

			//state 5
			case OperatorState:
				switch (s.charAt(i)) {
				case '+': 
					currentstate = states.PLUSPLUS;
					break;

				case '-': 
					currentstate = states.MINUSMINUS;
					break;
						
				case '*': 
					result = new Token(tokenType.MULTIPLY, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
					break;
						
				case '/': 
					result = new Token(tokenType.DIVIDE, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
					break;
						
				case '=': 
					result = new Token(tokenType.EQUAL, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
					break;
						
				case ':': 
					currentstate = states.ASSIGNMENT;
					break;
				
				case ';':
					currentstate = states.SEMICOLON;
					break;
				
				case ',':
					currentstate = states.COMMA;
					break;
						
				case '<': 
					currentstate = states.LESSOREQUAL;
					break;
						
				case '>':
					currentstate = states.GREATEROREQUAL;
					break;
							
				case '!': 
					result = new Token(tokenType.NOT, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
					break;
				}
					
				break;
			
			//PLUSPLUS
			case PLUSPLUS:
				if (s.charAt(i) == '+') {
					acc += s.charAt(i);
					result = new Token(tokenType.PLUSPLUS, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else {
					result = new Token(tokenType.PLUS, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					i--;
					currentstate = states.BeginState;
				}
				break;
			
			//MINUSMINUS
			case MINUSMINUS:
				if (s.charAt(i) == '-') {
					acc += s.charAt(i);
					result = new Token(tokenType.MINUSMINUS, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else {
					result = new Token(tokenType.MINUS, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					i--;
					currentstate = states.BeginState;
				}
				break;
			
			//ASSIGNMENT
			case ASSIGNMENT:
				if (s.charAt(i) == '=') {
					acc += s.charAt(i);
					result = new Token(tokenType.ASSIGNMENT, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else {
					result = new Token(tokenType.COLON, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					i--;
					currentstate = states.BeginState;
				}
				break;
			
			//GREATEROREQUAL
			case GREATEROREQUAL:
				if (s.charAt(i) == '=') {
					acc += s.charAt(i);
					result = new Token(tokenType.GREATEROREQUAL, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else {
					result = new Token(tokenType.GREATERTHAN, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					i--;
					currentstate = states.BeginState;
				}
				break;
			
			//LESSOREQUAL
			case LESSOREQUAL:
				if (s.charAt(i) == '=') {
					acc += s.charAt(i);
					result = new Token(tokenType.LESSOREQUAL, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				
			//NOT EQUAL
			else if (s.charAt(i) == '>') {
					acc += s.charAt(i);
					result = new Token(tokenType.NOTEQUAL, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else {
					result = new Token(tokenType.LESSTHAN, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					i--;
					currentstate = states.BeginState;
				}
				break;
			
			//STRINGLITERAL
			case STRINGLITERAL:
				if (s.charAt(i) == '"') {
					result = new Token(tokenType.STRINGLITERAL, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else {
					acc = "";
					currentstate = states.STRINGLITERAL;
				}
				break;
				
			//CHARACTERLITERAL
			case CHARACTERLITERAL:
				if (s.substring(i).contains("'")) {
					acc = s.substring(i).replace("'", "");
					result = new Token(tokenType.CHARACTERLITERAL, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else {
					acc = "";
					currentstate = states.CHARACTERLITERAL;
				}
				break;
			
			//COMMENT
			case COMMENT:
				if (s.charAt(i) == '}') {
					result = new Token(tokenType.COMMENT, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else {
					acc = "";
					currentstate = states.COMMENT;
				}
				break;
			
			//ARRAY
			case ARRAY:
				if (s.charAt(i) == ']') {
					result = new Token(tokenType.ARRAY, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else {
					acc = "";
					currentstate = states.ARRAY;
				}
				break;
				
			//LEFTPAREN
			case LEFTPAREN:
				result = new Token(tokenType.LEFTPAREN, acc);
				tokenlist.add(result);
				acc = "";
				System.out.println(result.toString());
				currentstate = states.BeginState;
				break;
			
			//RIGHTPAREN
			case RIGHTPAREN:
				result = new Token(tokenType.RIGHTPAREN, acc);
				tokenlist.add(result);
				acc = "";
				System.out.println(result.toString());
				currentstate = states.BeginState;
				break;
			
			//SEMICOLON
			case SEMICOLON:
				result = new Token(tokenType.SEMICOLON, acc);
				tokenlist.add(result);
				acc = "";
				System.out.println(result.toString());
				i--;
				currentstate = states.BeginState;
				break;
				
			//COMMA
			case COMMA:
				result = new Token(tokenType.COMMA, acc);
				tokenlist.add(result);
				acc = "";
				System.out.println(result.toString());
				i--;
				currentstate = states.BeginState;
				break;
				
			//Default case
			default:
				break;
			}
		}
		
		/**
		 * End of line Handing
		 */
		if (acc != "") {
			
			switch (currentstate) {
			
			case NumberState:
				result = new Token(tokenType.NUMBER, acc);
				tokenlist.add(result);
				System.out.println(result.toString());
				acc = "";
				currentstate = states.BeginState;
				break;
				
			case IdentifierState:
				if (knownWords.containsKey(acc)) {
					result = new Token(knownWords.get(acc), acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				else {
					result = new Token(tokenType.IDENTIFIER, acc);
					tokenlist.add(result);
					acc = "";
					System.out.println(result.toString());
					currentstate = states.BeginState;
				}
				break;
			
			case ASSIGNMENT:
				result = new Token(tokenType.COLON, acc);
				tokenlist.add(result);
				acc = "";
				System.out.println(result.toString());
				currentstate = states.BeginState;
				break;
				
			case LESSOREQUAL:
				result = new Token(tokenType.LESSTHAN, acc);
				tokenlist.add(result);
				acc = "";
				System.out.println(result.toString());
				currentstate = states.BeginState;
				break;
				
			case GREATEROREQUAL:
				result = new Token(tokenType.GREATERTHAN, acc);
				tokenlist.add(result);
				acc = "";
				System.out.println(result.toString());
				currentstate = states.BeginState;
				break;
				
			default:
				break;
			}
		}
		
		result = new Token(tokenType.ENDOFLINE, acc);
		tokenlist.add(result);
		System.out.println(result);
		
		return tokenlist;
	}
	
	/**
	 * Counts the number of spaces and tabs until
	 * a non-space character is reached.
	 * @param s - character array (string)
	 */
	public static int getIndent (String s) {
		int i = 0;
		for (char c: s.toCharArray()) {
			if (c == ' ') {
				i++;
			}
			else if (c == '\t') {
				i += 4;
			}
			else 
				return i/4;
		}
		return i/4;
	}
}