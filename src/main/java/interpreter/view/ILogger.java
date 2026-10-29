package interpreter.view;

public interface ILogger {

    public void log(String state);
    public boolean isDisplayFlag();
    public void setDisplayFlag(boolean displayFlag);
}
