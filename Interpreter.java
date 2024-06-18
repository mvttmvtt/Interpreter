package Interpreter;

import java.util.ArrayList;
import java.util.HashMap;

import Lexer.Token.tokenType;
import Parser.AssignmentNode;
import Parser.BooleanCompareNode;
import Parser.BooleanCompareNode.compType;
import Parser.ForNode;
import Parser.FunctionNode;
import Parser.IfNode;
import Parser.IntegerNode;
import Parser.MathOpNode;
import Parser.Node;
import Parser.Parser;
import Parser.RealNode;
import Parser.RepeatNode;
import Parser.StatementNode;
import Parser.StringNode;
import Parser.VariableNode;
import Parser.VariableReferenceNode;
import Parser.WhileNode;
import Parser.WriteNode;
import Parser.MathOpNode.operations;

public class Interpreter {

	/**
	 * Private Hash map member (name-->InterpreterDataType)
	 */
	private HashMap <String, InterpreterDataType> VariablesAndConstants = new HashMap<>();
	
	/**
	 * Constructor which initializes the hash map
	 * @throws Exception 
	 */
//	public Interpreter() {
	public Interpreter(ArrayList <FunctionNode> functions) throws Exception {
			interpretFunction(functions.get(0));
	}
	
	/**
	 * Interprets function nodes
	 * @param function
	 * @throws Exception
	 */
	public void interpretFunction(FunctionNode function) throws Exception {
		
        ArrayList <VariableNode> variables = new ArrayList<VariableNode>();
        ArrayList <VariableNode> constants = new ArrayList<VariableNode>();
        
        variables = function.getVariables();
        constants = function.getConstants();
        
        /*
         * Handles local variables
         */
        for (int i=0; i < variables.size(); i++) {
        	if (variables.get(i).getType() == tokenType.INTEGER) {
        		
        		IntegerDataType Int = new IntegerDataType(variables.get(i).getName(), variables.get(i).getType());
        		VariablesAndConstants.put(variables.get(i).getName(), Int);
        	}
        	else if (variables.get(i).getType() == tokenType.REAL) {
        		
        		RealDataType real = new RealDataType(variables.get(i).getName(), variables.get(i).getType());
        		VariablesAndConstants.put(variables.get(i).getName(), real);
        	}
        	else if (variables.get(i).getType() == tokenType.STRING) {
        		
        		StringDataType str = new StringDataType(variables.get(i).getName(), variables.get(i).getType());
        		VariablesAndConstants.put(variables.get(i).getName(), str);
        	}
        	else if (variables.get(i).getType() == tokenType.CHAR) {
        		
        		CharacterDataType Char = new CharacterDataType(variables.get(i).getName(), variables.get(i).getType());
        		VariablesAndConstants.put(variables.get(i).getName(), Char);
        	}
        	else if (variables.get(i).getType() == tokenType.BOOLEAN) {
        		
        		BooleanDataType bool = new BooleanDataType(variables.get(i).getName(), variables.get(i).getType());
        		VariablesAndConstants.put(variables.get(i).getName(), bool);
        	}
        }
        
        /*
         * Handles constants
         */
        for (int i=0; i < constants.size(); i++) {
        	if (constants.get(i).getType() == tokenType.INTEGER) {
        		
        		IntegerDataType Int = new IntegerDataType(Integer.valueOf(constants.get(i).getVal()
        				.substring(9, constants.get(i).getVal().length())));
        		VariablesAndConstants.put(constants.get(i).getName(), Int);
        	}
        	else if (constants.get(i).getType() == tokenType.REAL) {
        		
        		RealDataType real = new RealDataType(Float.valueOf(constants.get(i).getVal()
        				.substring(7, constants.get(i).getVal().length())));
        		VariablesAndConstants.put(constants.get(i).getName(), real);
        	}
        	else if (constants.get(i).getType() == tokenType.STRINGLITERAL) {
        		
        		StringDataType str = new StringDataType(constants.get(i).getVal()
        				.substring(8, constants.get(i).getVal().length()));
        		VariablesAndConstants.put(constants.get(i).getName(), str);
        	}
        	else if (constants.get(i).getType() == tokenType.CHARACTERLITERAL) {
        		
        		CharacterDataType Char = new CharacterDataType(constants.get(i).getVal()
        				.substring(11, constants.get(i).getVal().length()).charAt(0));
        		VariablesAndConstants.put(constants.get(i).getName(), Char);
        	}
        	else if (constants.get(i).getType() == tokenType.BOOLEAN) {
        		
        		BooleanDataType bool = null;
        		if (constants.get(i).getVal().toString().contains("true")) {
        			bool = new BooleanDataType(true);
        		}
        		else if (constants.get(i).getVal().toString().contains("false")) {
        			bool = new BooleanDataType(false);
        		}
        		VariablesAndConstants.put(constants.get(i).getName(), bool);
        	}
        }
        
        interpretBlock(function.getStatements());
	}
	
	/**
	 * Handles statements by checking the instance of the statement
	 * @param statements
	 * @throws Exception
	 */
	public void interpretBlock(ArrayList<StatementNode> statements) throws Exception {
		
		for (int i=0; i < statements.size(); i++) {
			
			if (statements.get(i) instanceof AssignmentNode) {
				@SuppressWarnings("unused")
				AssignmentNode assignment = (AssignmentNode) statements.get(i);
				
//				System.out.println(assignment.getTarget());
//				Node output = expression(assignment.getTarget());
//				System.out.println("Output: " + output);
			}
			else if (statements.get(i) instanceof WriteNode) {
				WriteNode output = (WriteNode) statements.get(i);
				
				if (output.getVal() instanceof MathOpNode) {
					MathOpNode n = (MathOpNode) output.getVal();
					System.out.println("OUTPUT: " + expression(n));
				} else {
					System.out.println("OUTPUT: " + output.toString().substring(7, output.toString().length()));
				}
			}
			else if (statements.get(i) instanceof VariableReferenceNode) {
				VariableReferenceNode output = (VariableReferenceNode) statements.get(i);
				System.out.println("OUTPUT: " + output);
			}
			else if (statements.get(i) instanceof VariableNode) {
				VariableNode output = (VariableNode) statements.get(i);
				if (output.getVal().contains("Character")) {
					System.out.println("OUTPUT: " + output.getVal().substring(11, output.getVal().length()));
				}
				
				else if (output.getVal().contains("String")) {
					System.out.println("OUTPUT: " + output.getVal().substring(8, output.getVal().length()));
				}
			}
			else if (statements.get(i) instanceof IfNode) {
				IfNode If = (IfNode) statements.get(i);
				processIfNode(If);
			}
			else if (statements.get(i) instanceof WhileNode) {
				WhileNode While = (WhileNode) statements.get(i);
				processWhileNode(While);
			}
			else if (statements.get(i) instanceof ForNode) {
				ForNode For = (ForNode) statements.get(i);
				processForNode(For);
			}
			else if (statements.get(i) instanceof RepeatNode) {
				RepeatNode repeat = (RepeatNode) statements.get(i);
				processRepeatNode(repeat);
			}
		}
	}
	
	/**
	 * Handles mathematical expressions within a boolean condition
	 * @throws Exception 
	 */
	public float math(MathOpNode arg) throws Exception {
		
		if (arg.getLeft() instanceof MathOpNode) {
			math((MathOpNode) arg.getLeft());
		}
		else if(arg.getLeft() instanceof IntegerNode) {
			return ((IntegerNode)arg.getLeft()).getInt();
		}
		else if(arg.getLeft() instanceof RealNode) {
			return ((RealNode)arg.getLeft()).getValue();
		}
		else if(arg.getLeft() instanceof VariableReferenceNode) {
			InterpreterDataType tempDT = VariablesAndConstants.get(arg.getLeft());
			
			if(tempDT instanceof IntegerDataType) {
				return Float.valueOf(((IntegerDataType)tempDT).toString());
			}
			else if (tempDT instanceof RealDataType) {
				return Float.valueOf(((RealDataType)tempDT).toString());
			}
			else if (tempDT instanceof StringDataType) {
				return Float.valueOf(((StringDataType)tempDT).toString());
			}
			else if (tempDT instanceof CharacterDataType) {
				return Float.valueOf(((CharacterDataType)tempDT).toString());
			}
		}
		
		if (arg.getRight() instanceof MathOpNode) {
			math((MathOpNode) arg.getRight());
		}
		else if(arg.getRight() instanceof IntegerNode) {
			return ((IntegerNode)arg.getRight()).getInt();
		}
		else if(arg.getRight() instanceof RealNode) {
			return ((RealNode)arg.getRight()).getValue();
		}
		else if(arg.getRight() instanceof VariableReferenceNode) {
			InterpreterDataType tempDT = VariablesAndConstants.get(arg.getRight());
			
			if(tempDT instanceof IntegerDataType) {
				return Float.valueOf(((IntegerDataType)tempDT).toString());
			}
			else if (tempDT instanceof RealDataType) {
				return Float.valueOf(((RealDataType)tempDT).toString());
			}
			else if (tempDT instanceof StringDataType) {
				return Float.valueOf(((StringDataType)tempDT).toString());
			}
			else if (tempDT instanceof CharacterDataType) {
				return Float.valueOf(((CharacterDataType)tempDT).toString());
			}
		}
		throw new Exception("Error: Invalid Data Type.");
	}
	
	/**
	 * Processes if statements
	 * @return true if conditions are true
	 * @param arg
	 * @throws Exception 
	 */
	public boolean processIfNode(IfNode arg) throws Exception {
		
		BooleanCompareNode compare = arg.getCondition();
		//System.out.println(arg.getStatements());
		if (compare.getLeft() instanceof MathOpNode) {
			
			MathOpNode mathOp = (MathOpNode) compare.getLeft();
			math(mathOp);
		}
		
		if (compare.getRight() instanceof MathOpNode) {
			
			MathOpNode mathOp = (MathOpNode) compare.getRight();
			math(mathOp);
		}
		
		if (processBooleanComparison(compare) == true) {
			
			interpretBlock(arg.getStatements());
			return true;
		}
		return false;
	}
	
	/**
	 * Processes while loops
	 * @return true if conditions are true
	 * @param arg
	 * @throws Exception 
	 */
	public boolean processWhileNode(WhileNode arg) throws Exception {
		
		BooleanCompareNode compare = arg.getCondition();
		
		if (compare.getLeft() instanceof MathOpNode) {
			
			MathOpNode mathOp = (MathOpNode) compare.getLeft();
			math(mathOp);
		}
		
		if (compare.getRight() instanceof MathOpNode) {
			
			MathOpNode mathOp = (MathOpNode) compare.getRight();
			math(mathOp);
		}
		
		while (processBooleanComparison(compare) == true) {
			
			interpretBlock(arg.getStatements());
			return true;
		}
		return false;
	}
	
	/**
	 * Handles for loops
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public boolean processForNode(ForNode arg) throws Exception {
		
		Node compare1 = arg.getForFrom();
		Node compare2 = arg.getForTo();
		
		if (compare1 instanceof MathOpNode) {
			compare1 = expression(compare1);
		}
		
		if (compare2 instanceof MathOpNode) {
			compare2 = expression(compare2);
		}
		
		
		if ((Node) arg.getVariableReferenceNode().getVar() instanceof RealNode || (Node) arg.getVariableReferenceNode().getVar() instanceof IntegerNode) {
			if (Float.valueOf(arg.getVariableReferenceNode().getVar().getVal()) >= Float.valueOf(compare1.toString()) 
					&& Float.valueOf(arg.getVariableReferenceNode().getVar().getVal()) >= Float.valueOf(compare2.toString())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Handles repeat statements
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public boolean processRepeatNode(RepeatNode arg) throws Exception {

		BooleanCompareNode compare = arg.getCondition();
		
		if (compare.getLeft() instanceof MathOpNode) {
			
			MathOpNode mathOp = (MathOpNode) compare.getLeft();
			math(mathOp);
		}
		
		if (compare.getRight() instanceof MathOpNode) {
			
			MathOpNode mathOp = (MathOpNode) compare.getRight();
			math(mathOp);
		}
		
		do {
			interpretBlock(arg.getStatements());
			return true;
		} while (processBooleanComparison(compare) == true);
	}
	
	/**
	 * Handles boolean comparisons
	 * @param condition
	 * @return true if conditions are satisfied
	 * @throws Exception
	 */
	public boolean processBooleanComparison(BooleanCompareNode condition) throws Exception {
		
		if (condition.getCompType() == compType.Equal) {
			if (Float.valueOf(condition.getLeft().toString().substring(8, condition.getLeft().toString().length()))
					== Float.valueOf(condition.getLeft().toString().substring(8, condition.getRight().toString().length()))) {
				return true;
			}
			else {
				return false;
			}
		}
		
		if (condition.getCompType() == compType.LessThan) {
			if (Float.valueOf(condition.getLeft().toString().substring(8, condition.getLeft().toString().length()))
					< Float.valueOf(condition.getRight().toString().substring(8, condition.getRight().toString().length()))) {
				return true;
			}
			else {
				return false;
			}
		}
		
		if (condition.getCompType() == compType.GreaterThan) {
			if (Float.valueOf(condition.getLeft().toString().substring(8, condition.getLeft().toString().length()))
					> Float.valueOf(condition.getRight().toString().substring(8, condition.getRight().toString().length()))) {
				return true;
			}	
			else {
				return false;
			}
		}
		
		if (condition.getCompType() == compType.LessOrEqual) {
			if (Float.valueOf(condition.getLeft().toString().substring(8, condition.getLeft().toString().length()))
					<= Float.valueOf(condition.getRight().toString().substring(8, condition.getRight().toString().length()))) {
				return true;
			}
			else {
				return false;
			}
		}
		
		if (condition.getCompType() == compType.GreaterOrEqual) {
			if (Float.valueOf(condition.getLeft().toString().substring(8, condition.getLeft().toString().length()))
					>= Float.valueOf(condition.getRight().toString().substring(8, condition.getRight().toString().length()))) {
				return true;
			}
			else {
				return false;
			}
		}
		
		if (condition.getCompType() == compType.NotEqual) {
			if (Float.valueOf(condition.getLeft().toString().substring(8, condition.getLeft().toString().length()))
					!= Float.valueOf(condition.getRight().toString().substring(8, condition.getRight().toString().length()))) {
				return true;
			}
			else {
				return false;
			}
		}
		
		throw new Exception("Error: Invalid Comparison Type.");
	}
	
	/**
	 * Handles mathematical expressions
	 * @param n
	 * @return the result of the math operation
	 * @throws Exception
	 */
	public Node expression(Node n) throws Exception {
		
		if (n instanceof AssignmentNode) {
			n = ((AssignmentNode) n).getVal();
		}
		
		if (n instanceof MathOpNode) {
			MathOpNode mathOp = (MathOpNode) n;
			Node getleft = mathOp.getLeft();
			Node getright = mathOp.getRight();
			
			if (mathOp.getOp() == operations.ADD) {
				
				if (getleft instanceof IntegerNode) {
					IntegerNode result = new IntegerNode(Integer.valueOf(getleft.toString().substring(9, getleft.toString().length())) 
												+ Integer.valueOf(getright.toString().substring(9, getright.toString().length())));
					return result;
				}
				else if (getleft instanceof RealNode) {
					System.out.println("I hate comp sci");
					RealNode result = new RealNode(Float.valueOf(getleft.toString()) 
												+ Float.valueOf(getright.toString()));
					return result;
				}
				else if (getleft instanceof StringNode) {
					StringNode result = new StringNode(getleft.toString() + getright.toString());
					return result;
				}
			}
			else if (mathOp.getOp() == operations.SUBTRACT) {
				if (getleft instanceof IntegerNode) {
					IntegerNode result = new IntegerNode(Integer.valueOf(getleft.toString().substring(9, getleft.toString().length())) 
							- Integer.valueOf(getright.toString().substring(9, getright.toString().length())));
					return result;
				}
				else if (getleft instanceof RealNode) {
					RealNode result = new RealNode(Float.valueOf(getleft.toString()) 
												- Float.valueOf(getright.toString()));
					return result;
				}
			}
			else if (mathOp.getOp() == operations.MULTIPLY) {
				if (getleft instanceof IntegerNode) {
					IntegerNode result = new IntegerNode(Integer.valueOf(getleft.toString().substring(9, getleft.toString().length())) 
							* Integer.valueOf(getright.toString().substring(9, getright.toString().length())));
					return result;
				}
				else if (getleft instanceof RealNode) {
					RealNode result = new RealNode(Float.valueOf(getleft.toString()) 
												* Float.valueOf(getright.toString()));
					return result;
				}
			}
			else if (mathOp.getOp() == operations.DIVIDE) {
				if (getleft instanceof IntegerNode) {
					IntegerNode result = new IntegerNode(Integer.valueOf(getleft.toString().substring(9, getleft.toString().length())) 
							/ Integer.valueOf(getright.toString().substring(9, getright.toString().length())));
					return result;
				}
				else if (getleft instanceof RealNode) {
					RealNode result = new RealNode(Float.valueOf(getleft.toString()) 
												/ Float.valueOf(getright.toString()));
					return result;
				}
			}
			else if (mathOp.getOp() == operations.MODULO) {
				if (getleft instanceof IntegerNode) {
					IntegerNode result = new IntegerNode(Integer.valueOf(getleft.toString().substring(9, getleft.toString().length())) 
							% Integer.valueOf(getright.toString().substring(9, getright.toString().length())));
					return result;
				}
				else if (getleft instanceof RealNode) {
					RealNode result = new RealNode(Float.valueOf(getleft.toString()) 
												% Float.valueOf(getright.toString()));
					return result;
				}
			}
		}
		else if (n instanceof IntegerNode) {
			return n;
		}
		else if (n instanceof RealNode) {
			return n;
		}
		else if (n instanceof StringNode) {
			return n;
		}
		
		return null;
	}
	
	/**
	 * Searches for a specific variable by name
	 * @param var
	 * @return the referenced variable
	 * @throws Exception
	 */
	public InterpreterDataType VariableReferenceNode(VariableReferenceNode var) throws Exception {
		if (VariablesAndConstants.containsKey(var)) {
			return VariablesAndConstants.get(var.getrefName());
		}
		else {
			throw new Exception("Error: "+ var +" cannot be resolved to a variable");
		}
	}
}
