package Parser;

/**
 * This node class represents float values
 * @author Matthew Welsh
 */
public class RealNode extends Node {

	/*
	 * Float member
	 */
	private float f;

	/**
	 * A constructor that initializes the float member
	 * @param f - float varibale
	 */
	public RealNode(float f) {
		this.f = f;
	}
	
	/**
	 * An accessor that allows a float member
	 * to be called in another file
	 * @return f
	 */
	public float getValue() {
		return f;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		return "Float: "+ this.f;
	}
}
