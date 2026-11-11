package interpreter.model.stmt;

import interpreter.exceptions.*;
import interpreter.model.exp.IExp;
import interpreter.model.prgState.ProgramState;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        var symTable = programState.getSymTable();
        var fileTable = programState.getFileTable();
        if (!symTable.containsKey(varName)) {
            throw new VariableNotDefinedException("the variable does not exist");
        }
        if (!(symTable.get(varName) instanceof IntValue)) {
            throw new InvalidTypeException("Wrong type only int values can be read from file");
        }

        if (!(exp.eval(symTable) instanceof StringValue filePath)) {
            throw new InvalidTypeException("The file path passed is not a valid string");
        }

        if (!fileTable.containsKey(filePath)) {
            throw new FileException("File not opened");
        }

        var fileReader = fileTable.get(filePath);
        try {
            var line = fileReader.readLine();
            if(line==null)
                symTable.add(varName,new IntValue(0));
            else
                symTable.add(varName,new IntValue(Integer.parseInt(line.strip())));
        } catch (IOException e) {
            throw new FileException("Could not read " + e.getMessage());
        }catch (NumberFormatException e){
            throw new FileException("Could not read " + e.getMessage());
        }


        return programState;
    }
}
