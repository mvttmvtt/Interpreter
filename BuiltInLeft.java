package Interpreter;
import java.util.ArrayList;

import Parser.FunctionNode;
import Parser.StatementNode;
import Parser.VariableNode;

public class BuiltInLeft extends FunctionNode {

	/**
	 * A constructor which calls the following elements
	 * @param n
	 * @param variables
	 * @param constants
	 * @param statements
	 */
	public BuiltInLeft(String n, ArrayList<VariableNode> variables, ArrayList<VariableNode> constants,
																	ArrayList<StatementNode> statements) {
		super(n, variables, constants, statements);
	}
	
	/**
	 * Returns the first character of a string
	 */
	public static void execute(ArrayList<InterpreterDataType> IDT) {
		InterpreterDataType str;
		for (int i = 0; i < IDT.size(); i++) {
			str = IDT.get(i);
		}
	}
}
