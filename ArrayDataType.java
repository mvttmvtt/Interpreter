package Interpreter;
import java.util.List;
import Lexer.SyntaxErrorException;

public class ArrayDataType<T extends InterpreterDataType> extends InterpreterDataType{
	
	/*
	 * Private member
	 */
	private List<T> values;

	/**
	 * Constructor which initializes the values member
	 * @param value
	 */
    public ArrayDataType(List<T> values) {
        setValues(values);
    }

    /**
     * An accessor which returns the values member
     * @return values
     */
    public List<T> getValues() {
        return values;
    }

    /**
     * Initializes the values variable
     * @param values
     */
    public void setValues(List<T> values) {
        this.values = values;
    }
	
    /**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(values.get(i).toString());
        }
        sb.append("]");
        return sb.toString();
	}

	/**
	 * Parser a specific string input
	 */
	public void fromString(String input) {
		try {
			throw new SyntaxErrorException("FromString not supported for ArrayDataType");
		} catch (SyntaxErrorException e) {
			e.printStackTrace();
		}
	}
}
