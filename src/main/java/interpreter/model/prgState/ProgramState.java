package interpreter.model.prgState;

import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.util.List;

public class ProgramState {
    private MyStack<IStmt> exeStack;
    private MyDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private final IStmt originalProgram;

    public ProgramState(MyDictionary<String, IValue> symTable, MyStack<IStmt> exeStack, IList<IValue> out, IStmt originalProgram, MyDictionary<StringValue, BufferedReader> fileTable) {
        this.symTable = symTable;
        this.exeStack = exeStack;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        exeStack.push(originalProgram);

    }

    public String toLogString() {
        var sb = new StringBuilder();
        sb.append("ExeStack:\n");
        exeStack.toList().forEach(s -> sb.append(s).append('\n'));
        sb.append("SymTable:\n");
        symTable.toMap().forEach((k, v) ->
                sb.append(k).append(" --> ").append(v).append('\n'));
        sb.append("Out:\n");
        out.toList().forEach(v -> sb.append(v).append('\n'));
        sb.append("FileTable:\n");
        fileTable.toMap().keySet().forEach(f -> sb.append(f.val()).append('\n'));
        return sb.toString();
    }

    //Constructor
    public static ProgramState of(IStmt programEntry) {
        return new ProgramState(programEntry);
    }

    public ProgramState(IStmt programEntry) {
        this.originalProgram = programEntry;
        this.exeStack = new MyStack<>();
        this.symTable = new MyDictionary<>();
        this.fileTable = new MyDictionary<>();
        this.out = new MyList<>();
        exeStack.push(originalProgram);

    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    ;

    public MyDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    @Override
    public String toString() {
        return "-------Execution Stack-------: \n" + exeStack.toString() +
                "\n-------Symbol Table-----:\n" + symTable.toString() +
                "\n--------Output--------: \n" + out.toString();
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

}

