package interpreter.model.stmt;

import interpreter.model.prgState.ProgramState;
import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.VariableNotDefinedException;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record AssignStmt(String varName, IExp exp) implements IStmt {
        @Override
        public ProgramState execute(ProgramState state)  {

            var symTable = state.getSymTable();

            if (!symTable.containsKey(varName))
                throw new VariableNotDefinedException("variable " + varName + " was not declared");

            IValue val = exp.eval(symTable);

            if (!val.getType().equals(symTable.get(varName).getType()))
                throw new InvalidTypeException("declared type of variable " + varName
                        + " and type of the assigned expression do not match");
            symTable.add(varName, val);
            return state;

        }
        @Override public String toString() { return varName + " = " + exp; }

    }
