package Interpreter;
import java.util.ArrayList;

import Parser.FunctionNode;
import Parser.StatementNode;
import Parser.VariableNode;

public class BuiltInIntegerToReal extends FunctionNode {

	/**
	 * A constructor which calls the following elements
	 * @param n
	 * @param variables
	 * @param constants
	 * @param statements
	 */
	public BuiltInIntegerToReal(String n, ArrayList<VariableNode> variables, ArrayList<VariableNode> constants,
																			ArrayList<StatementNode> statements) {
		super(n, variables, constants, statements);
	}
	
	/**
	 * Converts an integer to a real
	 */
	public static void execute(final ArrayList<IntegerDataType> num) {
		for (int i = 0; i < num.size(); i++) {
			int number = (int) num.get(i).getValue();
			System.out.println((float) number);	
		}
	}
}
