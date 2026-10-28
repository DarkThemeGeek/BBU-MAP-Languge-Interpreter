package interpreter.exceptions;

public class NonBooleanConditionException extends StmtException {
    public NonBooleanConditionException(String message) {
        super(message);
    }
}
