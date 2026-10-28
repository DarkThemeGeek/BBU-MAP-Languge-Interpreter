package interpreter.model.stmt;

import interpreter.model.prgState.ProgramState;
import interpreter.exceptions.InvalidTypeException;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;


public record IfStmt(IExp condition, IStmt stmt1, IStmt stmt2) implements  IStmt{


    @Override
    public ProgramState execute(ProgramState programState) {
        IValue value = condition.eval(programState.getSymTable());
        if (!(value instanceof BoolValue(boolean booleanValue))) {
            throw new InvalidTypeException(condition.toString()+"Is not of type boolean");
        }
        if(booleanValue){
            stmt1.execute(programState);
        }
        else{
            stmt2.execute(programState);
        }

        return programState;
    }

    @Override
    public String toString() {
        return "if (" + condition.toString() + ") then " + stmt1.toString() + " else " + stmt2.toString();
    }
}
