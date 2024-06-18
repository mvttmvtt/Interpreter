package Parser;
import java.util.ArrayList;

import Lexer.SyntaxErrorException;
import Lexer.Token;
import Lexer.Token.tokenType;
import Parser.BooleanCompareNode.compType;
import Parser.MathOpNode.operations;

/**
 * Parses through a list of tokens and
 * puts them in order
 * @author Matthew Welsh 
 */
public class Parser {
	
	static int i;
	public ProgramNode program = null;
	
	/**
	 * An array list of tokens
	 */
	public ArrayList<Token> tokens;
	
	/**
	 * An array list of statement nodes
	 */
	public ArrayList<StatementNode> statements = new ArrayList<StatementNode>();
	
	/**
	 * An array list of variables
	 */
	public ArrayList<VariableNode> variables = new ArrayList<VariableNode>();
	
	/**
	 * An array list of constants
	 */
	public ArrayList<VariableNode> constants = new ArrayList<VariableNode>();
	
	/**
	 * An array list of Parameter nodes
	 */
	public ArrayList<ParameterNode> parameters = new ArrayList<ParameterNode>();
	
	/**
	 * An array list of variable reference nodes
	 */
	public ArrayList<VariableReferenceNode> VariableReferenceNodes = new ArrayList<VariableReferenceNode>();
	
	/**
	 * An array list of Assignment nodes
	 */
	public ArrayList<AssignmentNode> assignments = new ArrayList<AssignmentNode>();
	
	/**
	 * An array list of function nodes
	 */
	public ArrayList<FunctionNode> functions = new ArrayList<FunctionNode>();
	
	/**
	 * Accessor used to call the program in another class
	 * @return program
	 */
	public ProgramNode getProgram() {
		return program;
	}
	
	/**
	 * A constructor which initializes the arraylist of tokens
	 * @param token
	 */
	public Parser(ArrayList<Token> token) {
		tokens = token;
	}
	
	/**
	 * Accepts a token type. Looks at the next token in the collection:
	 * 		If the passed in token type matches the next token’s type, remove that token and return it.
	 * 		If the passed in token type DOES NOT match the next token’s type (or there are no more tokens) 
	 * 		return null.
	 * @param t
	 * @return temp - If the passed in token type matches the next token’s type, 
	 * remove that token and return it
	 * @return null - If the passed in token type DOES NOT match the next token’s type 
	 * (or there are no more tokens)
	 */
	public Token matchAndRemove(Token.tokenType t) {
		if (tokens.get(0).getType() == t) {
			Token temp = tokens.get(0);
			tokens.remove(0);
			return temp;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Uses matchAndRemove() method to match and discard one 
	 * or more ENDOFLINE tokens. 
	 * @throws SyntaxErrorException - if no ENDOFLINE was found
	 */
	public void expectEndsOfLine() throws SyntaxErrorException {
		if (matchAndRemove(tokenType.ENDOFLINE) == null) {
			throw new SyntaxErrorException("Error: No 'ENDOFLINE' was found."); 
		}
	}
	
	/**
	 * Accepts an integer and looks ahead that many tokens and returns that token
	 * @param i
	 * @return null - if here aren’t enough tokens to fulfill the request
	 */
	public Token peek(int i) {
		if (tokens.get(i).getType() == null) {
			return null;
		}
		else {
			return tokens.get(i);
		}
	}
	
	/**
	 * Searches for IF tokens
	 * @return tokenType.IF
	 */
	public tokenType seekIf() {
		for (int i=0; i < tokens.size(); i++) {
			if (peek(i).getType() == tokenType.IF) {
				return tokenType.IF;
			}
		}
		return null;
	}
	
	/**
	 * Searches for FOR tokens
	 * @return tokenType.FOR
	 */
	public tokenType seekFor() {
		for (int i=0; i < tokens.size(); i++) {
			if (peek(i).getType() == tokenType.FOR) {
				return tokenType.FOR;
			}
		}
		return null;
	}
	
	/**
	 * Searches for WHILE tokens
	 * @return tokenType.WHILE
	 */
	public tokenType seekWhile() {
		for (int i=0; i < tokens.size(); i++) {
			if (peek(i).getType() == tokenType.WHILE) {
				return tokenType.WHILE;
			}
		}
		return null;
	}
	
	/**
	 * Processes a function by expecting a define token then an identifier token, then
	 * a left paren and so on
	 * Constants can be created both inside and outside of a function
	 * @return var - a variable node
	 * @return function - a function node
	 * @throws SyntaxErrorException 
	 */
	public FunctionNode function() throws SyntaxErrorException {
		
		matchAndRemove(tokenType.DEFINE);
			
		Token temp = matchAndRemove(tokenType.IDENTIFIER);
		String functionName = temp.getValue();
			
		if (temp != null) {
				
			matchAndRemove(tokenType.LEFTPAREN);
			parameterDeclarations(); //Calls the parameter Declarations method
			matchAndRemove(tokenType.RIGHTPAREN);
			matchAndRemove(tokenType.IDENTIFIER);
			expectEndsOfLine();
			
			if (matchAndRemove(tokenType.VARIABLES) != null) {
				processVariables(); //Processes Variables
				expectEndsOfLine();
				matchAndRemove(tokenType.CONSTANTS);
				processConstants(); //Processes Constants
				expectEndsOfLine();
				statements();
			}
			else if (matchAndRemove(tokenType.CONSTANTS) != null) {
				processConstants(); //Processes Constants
				expectEndsOfLine();
				matchAndRemove(tokenType.VARIABLES);
				processVariables(); //Processes Variables
				expectEndsOfLine();
				statements();
			} else {
				//expectEndsOfLine();
				statements();
			}
		}
		parseFunctionCalls();
		FunctionNode function = new FunctionNode(functionName, variables, constants, statements);
		functions.add(function);
		System.out.println(function);
		return function;
	}
	
	/**
	 * This method process arguments for the function method
	 * @return nodes - an Array list of variable nodes
	 * @throws SyntaxErrorException
	 */
	private ArrayList<VariableNode> parameterDeclarations() throws SyntaxErrorException {
		
		ArrayList<VariableNode> Vnodes = new ArrayList<VariableNode>();
		
		do {
			
		Token tmp = matchAndRemove(tokenType.VAR);
		if (tmp == null) {
			tmp = matchAndRemove(tokenType.IDENTIFIER);

			String argName = tmp.getValue();
			
			if (matchAndRemove(tokenType.COLON) != null) {
			/*
			 * Handles various variables types
			 */
				if(matchAndRemove(tokenType.REAL) != null) {
					VariableNode real = new VariableNode(argName, true, tokenType.REAL);
					Vnodes.add(real);
					VariableReferenceNode var = new VariableReferenceNode(argName);
					VariableReferenceNodes.add(var);
					ParameterNode parameter = new ParameterNode(var, tokenType.REAL);
					parameters.add(parameter);
					
				} else if (matchAndRemove(tokenType.STRING) != null) {
					VariableNode String = new VariableNode(argName, true, tokenType.STRING);
					Vnodes.add(String);
					VariableReferenceNode var = new VariableReferenceNode(argName);
					ParameterNode parameter = new ParameterNode(var, tokenType.STRING);
					VariableReferenceNodes.add(var);
					parameters.add(parameter);
				
				} else if (matchAndRemove(tokenType.INTEGER) != null) {
					VariableNode Int = new VariableNode(argName, true, tokenType.INTEGER);
					Vnodes.add(Int);
					VariableReferenceNode var = new VariableReferenceNode(argName);
					ParameterNode parameter = new ParameterNode(var, tokenType.INTEGER);
					VariableReferenceNodes.add(var);
					parameters.add(parameter);
					
				} else if (matchAndRemove(tokenType.CHAR) != null) {
					VariableNode Char = new VariableNode(argName, true, tokenType.CHAR);
					Vnodes.add(Char);
					VariableReferenceNode var = new VariableReferenceNode(argName);
					ParameterNode parameter = new ParameterNode(var, tokenType.CHAR);
					VariableReferenceNodes.add(var);
					parameters.add(parameter);
					
				} else if (matchAndRemove(tokenType.BOOLEAN) != null) {
					VariableNode bool = new VariableNode(argName, true, tokenType.BOOLEAN);
					Vnodes.add(bool);
					VariableReferenceNode var = new VariableReferenceNode(argName);
					VariableReferenceNodes.add(var);
					ParameterNode parameter = new ParameterNode(var, tokenType.BOOLEAN);
					parameters.add(parameter);
					
				} else {
					throw new SyntaxErrorException("Error: Invalid variable type");
				}
			}
		} else {
			tmp = matchAndRemove(tokenType.IDENTIFIER);
			if (tmp == null) {
				throw new SyntaxErrorException("Error: No identifier token was detected");
			}
			
			//add parameter node
			
			String argName = tmp.getValue();
			
			if (matchAndRemove(tokenType.COLON) != null) {
			/*
			 * Handles various variables types
			 */
				if(matchAndRemove(tokenType.REAL) != null) {
					VariableNode real = new VariableNode(argName, false, tokenType.REAL);
					Vnodes.add(real);
					VariableReferenceNode var = new VariableReferenceNode(argName);
					VariableReferenceNodes.add(var);
					ParameterNode parameter = new ParameterNode(var, tokenType.REAL);
					parameters.add(parameter);
					
				} else if (matchAndRemove(tokenType.STRING) != null) {
					VariableNode String = new VariableNode(argName, false, tokenType.STRING);
					Vnodes.add(String);
					VariableReferenceNode var = new VariableReferenceNode(argName);
					VariableReferenceNodes.add(var);
					ParameterNode parameter = new ParameterNode(var, tokenType.STRING);
					parameters.add(parameter);
				
				} else if (matchAndRemove(tokenType.INTEGER) != null) {
					VariableNode Int = new VariableNode(argName, false, tokenType.INTEGER);
					Vnodes.add(Int);
					VariableReferenceNode var = new VariableReferenceNode(argName);
					VariableReferenceNodes.add(var);
					ParameterNode parameter = new ParameterNode(var, tokenType.INTEGER);
					parameters.add(parameter);
					
				} else if (matchAndRemove(tokenType.CHAR) != null) {
					VariableNode Char = new VariableNode(argName, false, tokenType.CHAR);
					Vnodes.add(Char);
					VariableReferenceNode var = new VariableReferenceNode(argName);
					VariableReferenceNodes.add(var);
					ParameterNode parameter = new ParameterNode(var, tokenType.CHAR);
					parameters.add(parameter);
					
				} else if (matchAndRemove(tokenType.BOOLEAN) != null) {
					VariableNode bool = new VariableNode(argName, false, tokenType.BOOLEAN);
					Vnodes.add(bool);
					VariableReferenceNode var = new VariableReferenceNode(argName);
					VariableReferenceNodes.add(var);
					ParameterNode parameter = new ParameterNode(var, tokenType.BOOLEAN);
					parameters.add(parameter);
					
				} else {
					throw new SyntaxErrorException("Error: Invalid variable type");
				}
			}
		}
		} while (matchAndRemove(tokenType.SEMICOLON) != null);
	
		return Vnodes;
	}
	
	/**
	 * Processes variables and return a list of variable nodes
	 * @throws SyntaxErrorException 
	 */
	private void processVariables() throws SyntaxErrorException {
		
		/**
		 * An array list of variable names
		 */
		ArrayList<String> Variables = new ArrayList<String>();
		
		Token tmp = matchAndRemove(tokenType.IDENTIFIER);
		String varName = tmp.getValue();
		Variables.add(varName);
		
		if (tmp != null) {
			if (matchAndRemove(tokenType.COMMA) != null) {
				do {
					tmp = matchAndRemove(tokenType.IDENTIFIER);
					varName = tmp.getValue();
					Variables.add(varName);
				} while (matchAndRemove(tokenType.COMMA) != null);
			}
			
			/*
			 * Handles various variables types
			 */
			if (matchAndRemove(tokenType.COLON) != null) {
				if(matchAndRemove(tokenType.REAL) != null) {

					/*
					 * Handles Ranges for real numbers (floats)
					 */
					if (matchAndRemove(tokenType.FROM) != null) {
						Token t = matchAndRemove(tokenType.NUMBER);
						if (t != null) {
							float realFrom = Float.valueOf(t.getValue());
							matchAndRemove(tokenType.TO);
							Token t2 = matchAndRemove(tokenType.NUMBER);
							float realTo = Float.valueOf(t2.getValue());
							for (int i=0; i < Variables.size(); i++) {
								VariableNode real = new VariableNode(Variables.get(i), false, tokenType.Real, realFrom, realTo);
								variables.add(real);
							}
						}
					/*
					 * If no range is detected
					 */
					} else {
					for (int i=0; i < Variables.size(); i++) {
						VariableNode real = new VariableNode(Variables.get(i), true, tokenType.REAL);
						variables.add(real);
						}
					}
					
				} else if (matchAndRemove(tokenType.STRING) != null) {
					
					/*
					 * Handles Ranges for strings
					 */
					if (matchAndRemove(tokenType.FROM) != null) {
						Token t = matchAndRemove(tokenType.STRINGLITERAL);
						if (t != null) {
							String StringFrom = t.getValue();
							matchAndRemove(tokenType.TO);
							Token t2 = matchAndRemove(tokenType.STRINGLITERAL);
							String StringTo = t2.getValue();
							for (int i=0; i < Variables.size(); i++) {
								VariableNode real = new VariableNode(Variables.get(i), false, tokenType.StrValue, StringFrom, StringTo);
								variables.add(real);
							}
						}
					
					/*
					 * If no range is detected
					 */
					} else {
					for (int i=0; i < Variables.size(); i++) {
						VariableNode String = new VariableNode(Variables.get(i), true, tokenType.STRING);
						variables.add(String);
						}
					}
					
				} else if (matchAndRemove(tokenType.INTEGER) != null) {
					
					/*
					 * Handles Ranges for integers
					 */
					if (matchAndRemove(tokenType.FROM) != null) {
						Token t = matchAndRemove(tokenType.NUMBER);
						if (t != null) {
							int intFrom = Integer.valueOf(t.getValue());
							matchAndRemove(tokenType.TO);
							Token t2 = matchAndRemove(tokenType.NUMBER);
							int intTo = Integer.valueOf(t2.getValue());
							for (int i=0; i < Variables.size(); i++) {
								VariableNode real = new VariableNode(Variables.get(i), false, tokenType.Integer, intFrom, intTo);
								variables.add(real);
							}
						}
					
					/*
					 * If no range is detected
					 */
					} else {
					for (int i=0; i < Variables.size(); i++) {
						VariableNode Int = new VariableNode(Variables.get(i), true, tokenType.INTEGER);
						variables.add(Int);
						}
					}
					
				} else if (matchAndRemove(tokenType.CHAR) != null) {
					for (int i=0; i < Variables.size(); i++) {
						VariableNode Char = new VariableNode(Variables.get(i), true, tokenType.CHAR);
						variables.add(Char);
					}
				} else if (matchAndRemove(tokenType.BOOLEAN) != null) {
					for (int i=0; i < Variables.size(); i++) {
						VariableNode bool = new VariableNode(Variables.get(i), true, tokenType.BOOLEAN);
						variables.add(bool);
					}
				} else {
					throw new SyntaxErrorException("Error: Invalid variable type");
				}
			}
		}
	}
	
	/**
	 * Processes constants and return a list of variable nodes
	 * @throws SyntaxErrorException 
	 */
	private void processConstants() throws SyntaxErrorException {
		
		Token tmp = matchAndRemove(tokenType.IDENTIFIER);
		if(tmp != null) {
			
			String constName = tmp.getValue();
			
			if (matchAndRemove(tokenType.EQUAL) != null) {
				
				Token tmp2 = matchAndRemove(tokenType.NUMBER);
				Token tmp3 = matchAndRemove(tokenType.STRINGLITERAL);
				Token tmp4 = matchAndRemove(tokenType.CHARACTERLITERAL);
				Token tmp5 = matchAndRemove(tokenType.TRUE);
				Token tmp6 = matchAndRemove(tokenType.FALSE);
				
				//handles numbers
				if (tmp2 != null) {
					if (tmp2.getValue().contains(".")) {
						RealNode rn = new RealNode(Float.valueOf(tmp2.getValue()));
						VariableNode num = new VariableNode(constName, true, tokenType.REAL, rn);
						constants.add(num);
					} else {
						IntegerNode In = new IntegerNode(Integer.valueOf(tmp2.getValue())); 
						VariableNode num = new VariableNode(constName, true, tokenType.INTEGER, In);
						constants.add(num);
					}
				}
				
				//handles string literals
				if (tmp3 != null) {
					StringNode SN = new StringNode(tmp3.getValue());
					VariableNode StrVal = new VariableNode (constName, true, tokenType.STRINGLITERAL, SN);
					constants.add(StrVal);
				}
				
				//handles character literals
				if (tmp4 != null) {
					CharacterNode CN = new CharacterNode(tmp4.getValue().charAt(0));
					VariableNode CharVal = new VariableNode (constName, true, tokenType.CHARACTERLITERAL, CN);
					constants.add(CharVal);
				}
				
				//handles booleans
				if (tmp5 != null) {
					BooleanNode BN = new BooleanNode(true);
					VariableNode BooleanVal = new VariableNode (constName, true, tokenType.BOOLEAN, BN);
					constants.add(BooleanVal);
				}
				else if (tmp6 != null) {
					BooleanNode BN = new BooleanNode(false);
					VariableNode BooleanVal = new VariableNode (constName, true, tokenType.BOOLEAN, BN);
					constants.add(BooleanVal);
				}
				
			} else {
				throw new SyntaxErrorException("Error: Invalid variable type");
			}
		}
	}
	
	/**
	 * Calls statement() after indent has been matched and remove then adds
	 * it to the list of statement nodes until dedent has been matched and removed
	 * @throws SyntaxErrorException 
	 */
	private void statements() throws SyntaxErrorException {
		
		if (matchAndRemove(tokenType.INDENT) != null || matchAndRemove(tokenType.DEDENT) != null) {
			//parses assignments
			while (peek(0).getType() == tokenType.IDENTIFIER || peek(0).getType() == tokenType.WRITE) {
				statement();
			
				//detects more lines of code
				while (peek(0).getType() == tokenType.IDENTIFIER) {
					do {
						statement();
					} while (peek(1).getType() == tokenType.DEDENT);
				}
				
				//parses if statements
				if (seekIf() != null) {
					parseIf();
				}
				
				//parses while loops
				if (seekWhile() != null) {
					parseWhile();
				}
				
				//parses for loops
				if (seekFor() != null) {
					parseFor();
				}
			}
		}
	}
	
	/**
	 * Calls Assignment and write method depending
	 * @return statement - The assignment method
	 * @throws SyntaxErrorException 
	 */
	private StatementNode statement() throws SyntaxErrorException {
		
		if (peek(1).getType() == tokenType.ASSIGNMENT) {
			if (peek(4).getType() == tokenType.WRITE) {
				matchAndRemove(tokenType.IDENTIFIER);
				matchAndRemove(tokenType.ASSIGNMENT);
				matchAndRemove(tokenType.NUMBER);
				matchAndRemove(tokenType.ENDOFLINE);
				StatementNode statement = write();
				return statement;
			} else {
				StatementNode statement = Assignment();
				return statement;
			}
		}
		if (peek(0).getType() == tokenType.WRITE || peek(1).getType() == tokenType.WRITE) {
			StatementNode statement = write();
			//statements.add(statement);
			return statement;
		}
		return null;
	}
	
	/**
	 * Handles Valid Assignments
	 * @throws SyntaxErrorException 
	 */
	private StatementNode Assignment() throws SyntaxErrorException {
		
		Token tmp = matchAndRemove(tokenType.IDENTIFIER);
		if (tmp != null) {
			//used to assign complex variables
			Token tmp2 = matchAndRemove(tokenType.ARRAY);
			if (tmp2 != null) {
				
				IndexNode index = new IndexNode(tmp2.getValue());
				VariableReferenceNode left = new VariableReferenceNode (tmp.getValue(), index);
				VariableReferenceNodes.add(left);
				tmp = matchAndRemove(tokenType.ASSIGNMENT);
				
				if (tmp != null) {
					Node right_num = factor(0);
					if (right_num != null) {
						AssignmentNode assign = new AssignmentNode(left, right_num);
						assignments.add(assign);
						statements.add(assign);
						//System.out.println(assign);
					} else {
						Token str = matchAndRemove(tokenType.STRINGLITERAL);
						Token Char = matchAndRemove(tokenType.CHARACTERLITERAL) ;
							
						if (str != null) {
							StringNode SN = new StringNode(str.getValue());
							AssignmentNode assign = new AssignmentNode(left, SN);
							assignments.add(assign);
							statements.add(assign);
							//System.out.println(assign);
						}
						if (Char != null) {
							CharacterNode CN = new CharacterNode(Char.getValue().charAt(0));
							AssignmentNode assign = new AssignmentNode(left, CN);
							assignments.add(assign);
							statements.add(assign);
							//System.out.println(assign);
						}
						if (matchAndRemove(tokenType.TRUE) != null) {
							BooleanNode BN = new BooleanNode(true);
							AssignmentNode assign = new AssignmentNode(left, BN);
							assignments.add(assign);
							statements.add(assign);
							//System.out.println(assign);
						}
						if (matchAndRemove(tokenType.FALSE) != null) {
							BooleanNode BN = new BooleanNode(true);
							AssignmentNode assign = new AssignmentNode(left, BN);
							assignments.add(assign);
							statements.add(assign);
							//System.out.println(assign);
						}
					}
				} else {
					throw new SyntaxErrorException("Expression isn't found.");
				}
				
			} else {
				//used to assign regular variables
				VariableReferenceNode left = new VariableReferenceNode (tmp.getValue());
				VariableReferenceNodes.add(left);
				tmp = matchAndRemove(tokenType.ASSIGNMENT);
				tmp2 = matchAndRemove(tokenType.IDENTIFIER);
				
				if (tmp != null) {
					if (tmp2 == null) {
						Node right_num = factor(0);
						if (right_num != null) {
							AssignmentNode assign = new AssignmentNode(left, right_num);
							assignments.add(assign);
							statements.add(assign);
							//System.out.println(assign);
						} else {
							Token str = matchAndRemove(tokenType.STRINGLITERAL);
							Token Char = matchAndRemove(tokenType.CHARACTERLITERAL) ;
								
							if (str != null) {
								StringNode SN = new StringNode(str.getValue());
								AssignmentNode assign = new AssignmentNode(left, SN);
								assignments.add(assign);
								statements.add(assign);
								//System.out.println(assign);
							}
							if (Char != null) {
								CharacterNode CN = new CharacterNode(Char.getValue().charAt(0));
								AssignmentNode assign = new AssignmentNode(left, CN);
								assignments.add(assign);
								statements.add(assign);
								//System.out.println(assign);
							}
							if (matchAndRemove(tokenType.TRUE) != null) {
								BooleanNode BN = new BooleanNode(true);
								AssignmentNode assign = new AssignmentNode(left, BN);
								assignments.add(assign);
								statements.add(assign);
								//System.out.println(assign);
							}
							if (matchAndRemove(tokenType.FALSE) != null) {
								BooleanNode BN = new BooleanNode(true);
								AssignmentNode assign = new AssignmentNode(left, BN);
								assignments.add(assign);
								statements.add(assign);
								//System.out.println(assign);
							}
						}
					}
					else {
						
						@SuppressWarnings("unused")
						String var = tmp2.getValue();
						for (int i=0; i < parameters.size(); i++) {
							//System.out.println(parameters.get(i));
						}
					}
				} else {
					throw new SyntaxErrorException("Expression isn't found.");
				}
			}
		}
		matchAndRemove(tokenType.ENDOFLINE);
		return null;
	}
	
	/**
	 * detects the word write and creates variables nodes which then get add
	 * to the global list of statement nodes
	 * @return null
	 * @throws SyntaxErrorException 
	 */
	private StatementNode write() throws SyntaxErrorException {
		
		matchAndRemove(tokenType.INDENT);
		matchAndRemove(tokenType.DEDENT);
		
		Token tmp = matchAndRemove(tokenType.WRITE);
		while (tmp != null) {
			Token str = matchAndRemove(tokenType.STRINGLITERAL);
			Token Char = matchAndRemove(tokenType.CHARACTERLITERAL) ;
			
			if (peek(0).getType() == tokenType.IDENTIFIER) {
				
				Node value = expression();
				if (value != null) {
					WriteNode write = new WriteNode(value);
					//System.out.println(write);
					expectEndsOfLine();
					matchAndRemove(tokenType.DEDENT);
					return write;
				}		
			}			
			
			if (str != null) {
				StringNode SN = new StringNode(str.getValue());
				WriteNode write = new WriteNode(SN);
				//System.out.println(write);
				expectEndsOfLine();
				matchAndRemove(tokenType.DEDENT);
				return write;
				
			}
			if (Char != null) {
				CharacterNode CN = new CharacterNode(Char.getValue().charAt(0));
				WriteNode write = new WriteNode(CN);
				//System.out.println(write);
				expectEndsOfLine();
				matchAndRemove(tokenType.DEDENT);
				return write;
			}
			if (matchAndRemove(tokenType.TRUE) != null) {
				BooleanNode BN = new BooleanNode(true);
				WriteNode write = new WriteNode(BN);
				//System.out.println(write);
				expectEndsOfLine();
				matchAndRemove(tokenType.DEDENT);
				return write;
			}
			if (matchAndRemove(tokenType.FALSE) != null) {
				BooleanNode BN = new BooleanNode(true);
				WriteNode write = new WriteNode(BN);
				//System.out.println(write);
				expectEndsOfLine();
				matchAndRemove(tokenType.DEDENT);
				return write;
			} 
		}
		
		return null;
	}

	/**
	 * Calls term() then it looks for a + or -. 
	 * If it finds one, it calls term again and constructs a MathOpNode using 
	 * the two term() returns as the left and right.
	 * @return expression()
	 */
	private Node expression() {
		
		Node left = term();
		Token plus = matchAndRemove(tokenType.PLUS);
		Token minus = matchAndRemove(tokenType.MINUS);
		
		if (plus != null) {
			Node right = term();
			MathOpNode math = new MathOpNode(left, right, operations.ADD);
			System.out.println(math);
		}
		if (minus != null) {
			Node right = term();
			MathOpNode math =  new MathOpNode(left, right, operations.SUBTRACT);
			System.out.println(math);
		}
		return left;
	}
	
	/**
	 * Calls factor() then it looks for a * or /. 
	 * If it finds one, it calls factor again and constructs a MathOpNode using 
	 * the two factor() returns as the left and right.
	 * @return term()
	 */
	public Node term() {
		
		Node left = factor(0);
		Token multiply = matchAndRemove(tokenType.MULTIPLY);
		Token divide = matchAndRemove(tokenType.DIVIDE);
		Token modulo = matchAndRemove(tokenType.MODULO);
		
		/*
		 * Used if expression contains multiple pluses or minuses
		 */
		Token plusMaybe = matchAndRemove(tokenType.PLUS);
		Token minusMaybe = matchAndRemove(tokenType.MINUS);
		
		//handles multiplication operations
		if (multiply != null) {
			Node right = factor(0);
			//System.out.println(right);
			MathOpNode math = new MathOpNode(left, right, operations.MULTIPLY);
			//System.out.println(math);
			left = math;
		}
		
		//handles division operations
		if (divide != null) {
			Node right = factor(0);
			//System.out.println(right);
			MathOpNode math =  new MathOpNode(left, right, operations.DIVIDE);
			//System.out.println(math);
			left = math;
		}
		
		//handles modulo operations
		if (modulo != null) {
			Node right = factor(0);
			//System.out.println(right);
			MathOpNode math =  new MathOpNode(left, right, operations.MODULO);
			//System.out.println(math);
			left = math;
		}
		
		if (plusMaybe != null) {
			Node right = factor(1);
			MathOpNode math = new MathOpNode(left, right, operations.ADD);
			//System.out.println(math);
			left = math;
		}
		
		if (minusMaybe != null) {
			Node right = factor(1);
			MathOpNode math =  new MathOpNode(left, right, operations.SUBTRACT);
			//System.out.println(math);
			left = math;
		}
		return left;
	}
	
	/**
	 * Calls factor() then looks for a various comparison type.
	 * @return left
	 * @throws SyntaxErrorException 
	 */
	private Node BoolCompare() throws SyntaxErrorException {
		
		Node left = factor(0);
		Token lessThan = matchAndRemove(tokenType.LESSTHAN);
		Token greaterThan = matchAndRemove(tokenType.GREATERTHAN);
		Token LessOrEqual = matchAndRemove(tokenType.LESSOREQUAL);
		Token GreaterOrEqual = matchAndRemove(tokenType.GREATEROREQUAL);
		Token NotEqual = matchAndRemove(tokenType.NOTEQUAL);
		Token Equal = matchAndRemove(tokenType.EQUAL);
		
		//handles less than comparisons
		if (lessThan != null) {
			Node right = factor(1);
			BooleanCompareNode compare = new BooleanCompareNode (left, right, compType.LessThan);
			left = compare;
		}
		
		//handles greater than comparisons
		if (greaterThan != null) {
			Node right = factor(1);
			BooleanCompareNode compare = new BooleanCompareNode (left, right, compType.GreaterThan);
			left = compare;
		}
		
		//handles less or equal comparisons
		if (LessOrEqual != null) {
			Node right = factor(1);
			BooleanCompareNode compare = new BooleanCompareNode (left, right, compType.LessOrEqual);
			left = compare;
		}
		
		//handles greater or equal comparisons
		if (GreaterOrEqual != null) {
			Node right = factor(1);
			BooleanCompareNode compare = new BooleanCompareNode (left, right, compType.GreaterOrEqual);
			//System.out.println(compare); 
			left = compare;
		}
		
		//handles not equal comparisons
		if (NotEqual != null) {
			Node right = factor(1);
			BooleanCompareNode compare = new BooleanCompareNode (left, right, compType.NotEqual);
			//System.out.println(compare); 
			left = compare;
		}
		
		//handles equal comparisons
		if (Equal != null) {
			Node right = factor(1);
			BooleanCompareNode compare = new BooleanCompareNode (left, right, compType.Equal);
			//System.out.println(compare); 
			left = compare;
		}
		
		return left;
	}
	
	/**
	 * Parses if statements and recursively call itself
	 * if an elsif token is detected. Calls statement() if 
	 * an else token is detected
	 * @return ifstatement
	 * @throws SyntaxErrorException 
	 */
	private IfNode parseIf() throws SyntaxErrorException {
		
		Token tmp = matchAndRemove(tokenType.IF);
		Token tmp2 = matchAndRemove(tokenType.ELSIF);
		Token tmp3 = matchAndRemove(tokenType.ELSE);
		if (tmp != null || tmp2 != null || tmp3 != null) {
			
			BooleanCompareNode condition = (BooleanCompareNode) BoolCompare();
			
			if (matchAndRemove(tokenType.THEN) != null) {
				expectEndsOfLine();
				matchAndRemove(tokenType.INDENT);
				
				ArrayList<StatementNode> ifstatements = new ArrayList<StatementNode>();
				while (peek(0).getType() == tokenType.WRITE) {
					ifstatements.add(statement());
				}
				
				IfNode ifstatement = new IfNode(condition, ifstatements);
				statements.add(ifstatement);
				
				//recursively calls parseIf() if an elsif token is detected
				if (peek(1).getType() == tokenType.ELSIF) {
					matchAndRemove(tokenType.DEDENT);
					parseIf();
				}
				
				//calls statement if else token is detected
				if (peek(1).getType() == tokenType.ELSE) {
					matchAndRemove(tokenType.DEDENT);
					matchAndRemove(tokenType.ELSE);
					expectEndsOfLine();
					statements();
				}
				return ifstatement;
			}
			return null;
		}
		return null;
	}
	
	/**
	 * Parses while statements/loops
	 * @return whilestatement
	 * @throws SyntaxErrorException 
	 */
	private Node parseWhile() throws SyntaxErrorException {
		
		Token tmp = matchAndRemove(tokenType.WHILE);
		if (tmp != null) {
			
			BooleanCompareNode condition = (BooleanCompareNode) BoolCompare();
			
			expectEndsOfLine();
			matchAndRemove(tokenType.INDENT);
				
			ArrayList<StatementNode> whilestatements = new ArrayList<StatementNode>();
			while (peek(0).getType() == tokenType.WRITE) {
				whilestatements.add(statement());
			}
			
			WhileNode whilestatement = new WhileNode(condition, whilestatements);
			statements.add(whilestatement);
				
			return whilestatement;
		}
		return null;
	}
	
	/**
	 * Parses For statements/loops
	 * @return forstatement
	 * @throws SyntaxErrorException 
	 */
	private Node parseFor() throws SyntaxErrorException {
		
		matchAndRemove(tokenType.FOR);
		Token tmp = matchAndRemove(tokenType.IDENTIFIER);
		
		if (tmp != null) {
			VariableReferenceNode variable = VariableReferenceNodes.get(i);
			matchAndRemove(tokenType.IDENTIFIER);
				
			matchAndRemove(tokenType.FROM);
			Token from = matchAndRemove(tokenType.NUMBER);
			IntegerNode In = new IntegerNode(Integer.valueOf(from.getValue()));
				
			matchAndRemove(tokenType.TO);
			Token to = matchAndRemove(tokenType.NUMBER);
			IntegerNode In2 = new IntegerNode(Integer.valueOf(to.getValue()));
			
			ArrayList<StatementNode> forstatements = new ArrayList<StatementNode>();
			while (peek(0).getType() == tokenType.WRITE) {
				forstatements.add(statement());
			}
			
			ForNode forstatement = new ForNode(variable, In, In2, forstatements); //creates forNode
			statements.add(forstatement); //Adds statement to list of statement
			expectEndsOfLine();
			//statement();
			i++;
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	private void parseFunctionCalls() {
		
		System.out.println("Parameter(s)->" + parameters);
		
	}
	
	/**
	 * Locates a decimal point and return a real or integer node
	 * @return rn if the token contains a '.'
	 * @return In if the token doesn't contain a '.'
	 */
	private Node factor(int i) {
		
		Token t = matchAndRemove(tokenType.NUMBER);
		Token t2 = matchAndRemove(tokenType.IDENTIFIER);
		
		//handles numeric values
		if (t != null) {
			//returns a real node
			if (t.getValue().contains(".")) {
				RealNode rn = new RealNode(Float.valueOf(t.getValue()));
				return rn;
			}
			//returns an integer node
			else {
				IntegerNode In = new IntegerNode(Integer.valueOf(t.getValue()));
				return In;
			}
		}
		
		//handles non-numeric values
		if (t2 != null) {
			
			if (assignments.get(i).getVal() instanceof RealNode) {
				RealNode Rn = new RealNode(Float.valueOf(assignments.get(i).toString()
						.substring(assignments.get(i).toString().length()-4, 
						assignments.get(i).toString().length())));
				return Rn;
			}
			else if (assignments.get(i).getVal() instanceof IntegerNode) {
				IntegerNode In = new IntegerNode(Integer.valueOf(assignments.get(i).toString()
						.substring(assignments.get(i).toString().length()-1, 
						assignments.get(i).toString().length())));
				return In;
			}
			else if (assignments.get(i).getVal() instanceof StringNode) {
				StringNode Sn = new StringNode(assignments.get(i).toString()
						.substring(assignments.get(i).toString().length()-4, 
						assignments.get(i).toString().length()));
				return Sn;				
			}
			else if (assignments.get(i).getVal() instanceof CharacterNode) {
				CharacterNode Cn = new CharacterNode(assignments.get(i).toString()
						.substring(assignments.get(i).toString().length()-4, 
						assignments.get(i).toString().length()).charAt(0));
				return Cn;
			}
			else if (assignments.get(i).getVal() instanceof BooleanNode) {
				BooleanNode Bn = null;
				if (assignments.get(i).getVal().toString().contains("true")) {
					Bn = new BooleanNode(true);
					return Bn;
				} else if (assignments.get(i).getVal().toString().contains("false")) {
					Bn = new BooleanNode(false);
					return Bn;
				}
			}
			else if (assignments.get(i).getVal() instanceof VariableReferenceNode) {
				VariableReferenceNode var = (VariableReferenceNode) assignments.get(i).getVal();
				var.getrefName();
			}
		}
		return null;
	}
		
	/**
	 * Parses through the list of tokens outputed from the lexer
	 * @return program - The program node to be populated and returned
	 * @throws SyntaxErrorException
	 */
	public Node Parse() throws SyntaxErrorException {
		
		
		while (tokens.size() > 0) {
			
			//detects numbers and either compares them, or carries out math operations
			if (peek(0).getType() == tokenType.NUMBER || peek(0).getType() == tokenType.IDENTIFIER) {
				if (peek(1).getType() == tokenType.PLUS || peek(1).getType() == tokenType.MINUS 
						|| peek(1).getType() == tokenType.MULTIPLY || peek(1).getType() == tokenType.DIVIDE
						|| peek(1).getType() == tokenType.MODULO) {
					expression();
					expectEndsOfLine();
				} else {
					BoolCompare();
					expectEndsOfLine();
				}
			} else if (peek(0).getType() == tokenType.IDENTIFIER) {
				Token tmp = matchAndRemove(tokenType.IDENTIFIER);
				if (tmp.getValue().equals(functions.get(0).getName())) {
					
				}
			}
			//detects an identifier and carries out an assignment
			else if (peek(0).getType() == tokenType.IDENTIFIER) {
				if (peek(1).getType() == tokenType.ASSIGNMENT || peek(1).getType() == tokenType.ARRAY) {
					Assignment();
				}
			}
			//detects define and creates a function
			else {
				if (peek(0).getType() == tokenType.DEFINE) {
					functions.add(function());
				//	matchAndRemove(tokenType.DEDENT);
					
					//matches and removes all possible amounts of dedent tokens
					for (int i=0; i < tokens.size(); i++) {
						if (peek(i).getType() == tokenType.DEDENT) {
							matchAndRemove(tokenType.DEDENT);
						}
					}
					
					if (matchAndRemove(tokenType.IDENTIFIER) != null) {
						expectEndsOfLine();
					}
				}
			}
//			System.out.println(tokens.size());
//			System.out.println(tokens.get(0));
//			System.out.println(tokens.get(1));
//			System.out.println(tokens.get(2));
//			System.out.println(tokens.get(3));
//			break;
		}
		program = new ProgramNode(functions);
		return program;
	}
}