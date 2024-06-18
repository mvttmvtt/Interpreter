package Interpreter;
import java.util.ArrayList;
import java.util.Scanner;

import Parser.FunctionNode;
import Parser.StatementNode;
import Parser.VariableNode;
import Parser.VariableReferenceNode;

public class BuiltInRead extends FunctionNode {

	/**
	 * A constructor which calls the following elements
	 * @param n
	 * @param variables
	 * @param constants
	 * @param statements
	 */
	public BuiltInRead(String n, ArrayList<VariableNode> variables, ArrayList<VariableNode> constants, 
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
	public BuiltInRead(String n, ArrayList<VariableNode> variables, 
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
	 * Reads data types
	 */
	@SuppressWarnings("resource")
	public static void execute(ArrayList<InterpreterDataType> IDT) {
		
		Scanner scanner = new Scanner(System.in);
		for (int i = 0; i < IDT.size(); i++) {
	        String input = scanner.nextLine();
	        IDT.get(i).fromString(input);
		}
	}
}
