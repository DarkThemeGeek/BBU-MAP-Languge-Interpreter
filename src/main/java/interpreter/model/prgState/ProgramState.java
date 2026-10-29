package interpreter.model.prgState;

import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;

public class ProgramState {
    private MyStack<IStmt> exeStack;
    private MyDictionary<String, IValue>symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;

    public ProgramState(MyDictionary<String, IValue> symTable, MyStack<IStmt> exeStack, IList<IValue> out, IStmt originalProgram) {
        this.symTable = symTable;
        this.exeStack = exeStack;
        this.out = out;
        this.originalProgram = originalProgram;
        exeStack.push(originalProgram);

    }



    public  IStack<IStmt> getExeStack(){
       return exeStack;
    };

    public MyDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }
    @Override
    public String toString() {
        return  "-------Execution Stack-------: \n" + exeStack.toString() +
                "\n-------Symbol Table-----:\n" + symTable.toString() +
                "\n--------Output--------: \n" + out.toString() ;
    }

}

