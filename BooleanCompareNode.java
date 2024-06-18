package Parser;

public class BooleanCompareNode extends Node {
	
	/**
	 * Enum for math operations
	 */
	public enum compType {
		
		GreaterThan, LessThan, GreaterOrEqual, LessOrEqual,
		Equal, NotEqual
		
	}
	
	/**
	 * The left and right sub nodes
	 */
	private Node Left, Right;
	
	/**
	 * The operator variable
	 */
	private compType comparison;
	
	/**
	 * A constructor that initializes the math op nodes
	 * @param Left - left node
	 * @param Right - right node
	 * @param op - operator 
	 */
	public BooleanCompareNode (Node Left, Node Right, compType comparison) {
		this.Left = Left;
		this.Right = Right;
		this.comparison = comparison;
	}
	
	/**
	 * An accessor that allows the left node
	 * to be called in another file
	 * @return Left node
	 */
	public Node getLeft() {
		return this.Left;
	}
	
	/**
	 * An accessor that allows the left node
	 * to be called in another file
	 * @return right node
	 */
	public Node getRight() {
		return this.Right;
	}

	/**
	 * An accessor that allows the operator
	 * to be called in another file
	 * @return operator
	 */
	public compType getCompType() {
		return this.comparison;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		return "Comparison: ("+this.comparison+": Left->"+this.Left+"  Right->"+this.Right+")\n";
	}
}
