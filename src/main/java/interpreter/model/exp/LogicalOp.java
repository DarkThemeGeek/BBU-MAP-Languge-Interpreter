package interpreter.model.exp;

public enum LogicalOp {
    AND("and"), OR("or");
    private final String symbol;

    LogicalOp(String symbol) { this.symbol = symbol; }
    @Override public String toString() { return symbol; }

}
