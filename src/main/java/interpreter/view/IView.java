package interpreter.view;

import interpreter.model.prgState.ProgramState;

public interface IView {
    public void print(String string);
    public void displayProgramState(ProgramState state);
    public void displayMenu();
}
