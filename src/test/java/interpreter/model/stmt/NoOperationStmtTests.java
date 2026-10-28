package interpreter.model.stmt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoOperationStmtTests {
    @Test
    void testNoOperationStmtCreation() {
        NoOperationStmt stmt = new NoOperationStmt(new PrintStmt(new interpreter.model.exp.ValueExp(new interpreter.model.value.IntValue(0))));
        assertNotNull(stmt);
    }

    @Test
    void testToString() {
        NoOperationStmt stmt = new NoOperationStmt(new PrintStmt(new interpreter.model.exp.ValueExp(new interpreter.model.value.IntValue(0))));
        assertNotNull(stmt.toString());
    }
}
