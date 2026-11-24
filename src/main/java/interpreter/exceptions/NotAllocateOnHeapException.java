package interpreter.exceptions;

public class NotAllocateOnHeapException extends ExpressionException {
    public NotAllocateOnHeapException(String message) {
        super(message);
    }
}
