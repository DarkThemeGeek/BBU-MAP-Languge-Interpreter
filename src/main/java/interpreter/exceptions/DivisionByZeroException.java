package interpreter.exceptions;

public class DivisionByZeroException extends MathException {
    public DivisionByZeroException(String message) {
        super(message);
    }
}
