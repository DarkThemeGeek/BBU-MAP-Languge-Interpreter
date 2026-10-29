package interpreter.view;

import interpreter.controller.Controller;
import interpreter.model.prgState.ProgramState;

public class View implements IView {
    Controller controller;

    public View(boolean DisplayLogging){
        controller=new Controller(DisplayLogging);
    }
    public void print(String string){
        IO.println(string);
    }
    public void view(){
        controller=new Controller(true);
    }

    //Displays the menu
    //@return the choice from te menu
     public void displayMenu() {
         int choice = -1;
         while(choice!=0) {
             IO.println("Show examples(1,2,3)" +
                     "\n0 to exit");
             try {
                 choice = Integer.parseInt(IO.readln());
                 switch (choice){
                     case 1:{
                        controller.LoadProgram(Example.ex1);
                        controller.allStep();
                        break;
                     }
                     case 2:{
                         controller.LoadProgram(Example.ex2);
                         controller.allStep();
                         break;
                     }
                     case 3:{
                         controller.LoadProgram(Example.ex3);
                         controller.allStep();
                         break;
                     }
                     default:{
                         IO.println("wrong input");
                         break;
                     }
                 }
             } catch (NumberFormatException e) {
                 IO.println("wrong input");

             }

         }

    }
    public void displayProgramState(ProgramState state){
        IO.println(state.toString());
    }
}
