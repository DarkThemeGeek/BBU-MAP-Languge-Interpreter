package interpreter.exceptions;

public class InvalidOperatorException extends ExpressionException {
    public InvalidOperatorException(String message) {
        super(message);
    }
}
