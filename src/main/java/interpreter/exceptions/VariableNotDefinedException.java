package interpreter.exceptions;

public class VariableNotDefinedException extends ExpressionException {
    public VariableNotDefinedException(String message) {
        super(message);
    }
}
