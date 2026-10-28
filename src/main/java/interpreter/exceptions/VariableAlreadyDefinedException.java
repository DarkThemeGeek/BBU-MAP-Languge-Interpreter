package interpreter.exceptions;

public class VariableAlreadyDefinedException extends StmtException {
    public VariableAlreadyDefinedException(String message) {
        super(message);
    }
}
