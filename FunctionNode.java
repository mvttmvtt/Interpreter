package Parser;
import java.util.ArrayList;

/**
 * This node represents functions
 * @author Matthew Welsh
 */
public class FunctionNode extends Node {

	/*
	 * Private members
	 */
	private String name;
	private ArrayList<VariableNode> variables;
	private ArrayList<VariableNode> constants;
	private ArrayList<StatementNode> statements;
	
	
	
	/**
	 * A constructor that initializes the members
	 * @param n - String variable
	 * @param variables - ArrayList<VariableNode> variable
	 * @param statements - ArrayList<StatementNode> variable
	 */
	public FunctionNode (String n, ArrayList<VariableNode> variables, 
			ArrayList<VariableNode> constants, ArrayList<StatementNode> statements) {
		name = n;
		this.variables = variables;
		this.constants = constants;
		this.statements = statements;
	}
	
	/**
	 * An accessor that allows a string member
	 * to be called in another file
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * An accessor that allows a variable member
	 * to be called in another file
	 * @return variables
	 */
	public ArrayList<VariableNode> getVariables() {
		return variables;
	}
	
	/**
	 * An accessor that allows a constant member
	 * to be called in another file
	 * @return constant
	 */
	public ArrayList<VariableNode> getConstants() {
		return constants;
	}
	
	/**
	 * An accessor that allows a statements member
	 * to be called in another file
	 * @return statements
	 */
	public ArrayList<StatementNode> getStatements() {
		return statements;
	}
	
//	public FunctionNode getBlock() {
//		getVariables();
//		getConstants();
//		getStatements();
//		return null;
//		
//	}
	
	/**
	 * Checks if a variable is variadic
	 */
	public boolean isVariadic() {
		return false;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		if (constants != null) {
			return "Function Name: " + name + "\n\n" + "Variables: " + variables + "\n"
					+ "Constants: " + constants + "\n" + "Statements: " + statements;
		} else {
			return "Function: " + name + "\n\n" + "Variables: " + variables + "\n"
					+ "Statements: " + statements;
		}
	}
}
