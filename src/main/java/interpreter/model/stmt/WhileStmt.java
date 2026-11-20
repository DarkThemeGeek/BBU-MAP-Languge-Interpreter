package interpreter.model.stmt;


import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.MyException;
import interpreter.model.exp.IExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.value.BoolValue;

public record WhileStmt(IExp cond, IStmt body) implements IStmt { // S13
    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        if (!(cond.eval(state.getSymTable()) instanceof BoolValue condValue)) {
            throw new InvalidTypeException("expression does not return a boolean value\n");
        }
        // true: push this, then push body; false: do nothing
        if (condValue.val()) {
            state.getExeStack().push(this);
            state.getExeStack().push(body);
        }

        return state;
    }

    @Override
    public String toString() {
        return "(while (" + cond + ") " + body + ")";
    }
}