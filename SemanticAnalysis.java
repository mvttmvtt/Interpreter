package Interpreter;

import Parser.AssignmentNode;
import Parser.ForNode;
import Parser.FunctionNode;
import Parser.IfNode;
import Parser.ProgramNode;
import Parser.RepeatNode;
import Parser.StatementNode;
import Parser.WhileNode;
import Parser.WriteNode;


public class SemanticAnalysis {
    
    /**
     * A method which traverses a an array list of statement nodes and calls
     * the checkBlock method on it.
     * @param program
     * @throws Exception 
     */
    public static void CheckAssignments(ProgramNode program) throws Exception {
        for (FunctionNode function : program.getFunctions()) {
        	for (int i=0; i < function.getStatements().size(); i++) {
        		CheckBlock(function.getStatements().get(i));
        	}
        }
    }
    
    /**
     * A method which looks at a statement and 
     * calls other methods depending on the instance if the statement
     * @param statement
     * @throws Exception 
     */
    static void CheckBlock(StatementNode statement) throws Exception {
    	if (statement instanceof AssignmentNode) {
    		CheckAssignment((AssignmentNode) statement);
        } else if (statement instanceof WriteNode) {
        	CheckWrite((WriteNode) statement);
        } else if (statement instanceof IfNode) {
        	CheckIf((IfNode) statement);
        } else if (statement instanceof WhileNode) {
        	CheckWhile((WhileNode) statement);
        } else if (statement instanceof ForNode) {
        	CheckFor((ForNode) statement);
        } else if (statement instanceof RepeatNode) {
        	CheckRepeat((RepeatNode) statement);
        }
    }
    
    /**
     * This method check if the left and right side of
     * an assignment statement matches
     * @param assignment
     * @throws Exception 
     */
    static void CheckAssignment(AssignmentNode assignment) throws Exception {
        String leftType = assignment.getTarget().getrefName();
        String rightType = assignment.getTarget().getrefName();
        if (!leftType.equals(rightType)) {
            throw new Exception("Type mismatch in assignment: " +
                leftType + " = " + rightType);
        }
    }
    
    /**
     * This method check if the left and right side of
     * an write statement matches
     * @param write
     * @throws Exception 
     */
    static void CheckWrite(WriteNode write) throws Exception {
        String leftType = write.getTarget().getrefName();
        String rightType = write.getTarget().getrefName();
        if (!leftType.equals(rightType)) {
            throw new Exception("Type mismatch in assignment: " +
                leftType + " = " + rightType);
        }
    }
    
    /**
     * This method recursively calls checkBlock on 
     * statements within an if statement
     * @param ifNode
     * @throws Exception
     */
    static void CheckIf(IfNode ifNode) throws Exception {
    	for (int i=0; i < ifNode.getStatements().size(); i++) {
    		CheckBlock(ifNode.getStatements().get(i));
    	}
    }
    
    /**
     * This method recursively calls checkBlock on 
     * statements within a while statement
     * @param whileNode
     * @throws Exception
     */
    static void CheckWhile(WhileNode whileNode) throws Exception {
    	for (int i=0; i < whileNode.getStatements().size(); i++) {
    		CheckBlock(whileNode.getStatements().get(i));
    	}
    }
    
    /**
     * This method recursively calls checkBlock on 
     * statements within a for statement
     * @param forNode
     * @throws Exception
     */
    static void CheckFor(ForNode forNode) throws Exception {
    	for (int i=0; i < forNode.getStatements().size(); i++) {
    		CheckBlock(forNode.getStatements().get(i));
    	}
    }
    
    /**
     * This method recursively calls checkBlock on 
     * statements within a repeat statement
     * @param repeatNode
     * @throws Exception
     */
    static void CheckRepeat(RepeatNode repeatNode) throws Exception {
    	for (int i=0; i < repeatNode.getStatements().size(); i++) {
    		CheckBlock(repeatNode.getStatements().get(i));
    	}
    }
    
}