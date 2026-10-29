package interpreter.view;

public class Logger implements  ILogger {
    private  boolean displayFlag;

    public void log(String state){
        if(!displayFlag) return;
        IO.println(state);
    }

    public boolean isDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }
}
