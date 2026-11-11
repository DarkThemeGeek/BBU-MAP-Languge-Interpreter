package interpreter;

import interpreter.controller.Controller;
import interpreter.model.prgState.ProgramState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repo;
import interpreter.view.Example;
import interpreter.view.ExitCommand;
import interpreter.view.RunExampleCommand;
import interpreter.view.TextMenu;
import interpreter.view.old.IView;
import interpreter.view.old.View;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<IStmt> programs = List.of(Example.ex1, Example.ex2, Example.ex3,
                Example.ex4, Example.ex5);
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        for (int i = 0; i < programs.size(); i++) {
            IStmt program = programs.get(i);
            var repo = new Repo(ProgramState.of(program), "log" + (i + 1) + ".txt");
            var controller = new Controller(repo, true);
            menu.addCommand(new RunExampleCommand(String.valueOf(i + 1), program.toString(), controller));
        }
        menu.show();
    }
}
