package interpreter.model.stmt;

import interpreter.model.prgState.ProgramState;
import interpreter.exceptions.VariableAlreadyDefinedException;
import interpreter.model.type.IType;

public record VariableDeclarationStmt(IType type, String variableName) implements IStmt {
    public VariableDeclarationStmt(IType type, String variableName) {
        this.type = type;
        this.variableName = variableName;
    }


    @Override
    public ProgramState execute(ProgramState programState) {
        if (programState.getSymTable().containsKey(variableName)) {
            throw new VariableAlreadyDefinedException(variableName+" already exists");
        }
        programState.getSymTable().add(variableName,type.defaultValue());
        return  programState;
    }
    public String toString(){
        return type.toString() + " " + variableName;
    }
}
