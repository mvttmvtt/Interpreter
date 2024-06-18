package Interpreter;
import java.util.ArrayList;

import Parser.FunctionNode;
import Parser.StatementNode;
import Parser.VariableNode;
import Parser.VariableReferenceNode;

public class BuiltInWrite extends FunctionNode {

	/**
	 * A constructor which calls the following elements
	 * @param n
	 * @param variables
	 * @param constants
	 * @param statements
	 */
	public BuiltInWrite(String n, ArrayList<VariableNode> variables, ArrayList<VariableNode> constants,
																	ArrayList<StatementNode> statements) {
		super(n, variables, constants, statements);
	}
	
	/**
	 * A constructor which calls the following elements
	 * @param n
	 * @param variables
	 * @param VariableReferenceNodes
	 * @param constants
	 * @param statements
	 */
	public BuiltInWrite(String n, ArrayList<VariableNode> variables, 
			ArrayList<VariableReferenceNode> VariableReferenceNodes, ArrayList<VariableNode> constants, 
			ArrayList<StatementNode> statements) {
		super(n, variables, constants, statements);
	}
	
	/**
	 * Checks if a variable is variadic
	 */
	@Override
	public boolean isVariadic() {
		return true;
	}
	
	/**
	 * Prints data types
	 */
	public static void execute(ArrayList<InterpreterDataType> IDT) {
		for (int i = 0; i <= IDT.size(); i++) {
			System.out.println(IDT.get(i).toString());
		}
	}
}
