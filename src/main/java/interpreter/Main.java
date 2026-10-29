package interpreter;

import interpreter.controller.Controller;
import interpreter.model.prgState.ProgramState;
import interpreter.view.IView;
import interpreter.view.View;

public class Main {
    public static void main(){

        IView view=new View(true);
        view.displayMenu();




    }
}
