package Parser;

/**
 * This class represents assignments
 * @author Matthew Welsh
 */
public class AssignmentNode extends StatementNode {

	/*
	 * Represents the target of the assignment
	 */
	private VariableReferenceNode target;
	
	/*
	 * Represents the value
	 */
	private Node value;
	
	/**
	 *  A constructor that initializes the private members
	 * @param target
	 * @param value
	 */
	public AssignmentNode(VariableReferenceNode target, Node value) {
		this.target = target;
		this.value = value;
	}
	
	/**
	 * An accessor that allows the target variable to be called in
	 * another file
	 * @return target
	 */
	public VariableReferenceNode getTarget() {
		return target;
	}
	
	/**
	 * An accessor that allows the value variable to be called in
	 * another file
	 * @return value
	 */
	public Node getVal() {

		return value;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		
		return "Expression: Assignment: "+"Left-> " + target + "   Right-> " + value;
	}
}
