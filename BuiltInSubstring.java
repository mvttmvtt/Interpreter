package Interpreter;
import java.util.ArrayList;

import Parser.FunctionNode;
import Parser.StatementNode;
import Parser.VariableNode;

public class BuiltInSubstring extends FunctionNode {

	/**
	 * A constructor which calls the following elements
	 * @param n
	 * @param variables
	 * @param constants
	 * @param statements
	 */
	public BuiltInSubstring(String n, ArrayList<VariableNode> variables, ArrayList<VariableNode> constants,
																		ArrayList<StatementNode> statements) {
		super(n, variables, constants, statements);
	}
	
	/**
	 * Returns a string that is a substring a string
	 */
	public static void execute(StringDataType SDT, int beginning, int end) {
		String str = SDT.getValue();
		System.out.println(str.substring(beginning, end));
	}
}
