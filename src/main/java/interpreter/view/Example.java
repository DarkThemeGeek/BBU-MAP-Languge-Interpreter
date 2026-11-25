package interpreter.view;

import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.type.StringType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

public class Example {
    // int v; v = 2; print(v)
    public static final IStmt ex1 = new CompStmt(new VariableDeclarationStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new PrintStmt(new VariableExp("v"))));
    // Ex2: int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)
    public static final IStmt ex2 = new CompStmt(new VariableDeclarationStmt("a", new IntType()),
            new CompStmt(new VariableDeclarationStmt("b", new IntType()),
                    new CompStmt(new AssignStmt("a",
                            new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)),
                                    new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)),
                                            new ValueExp(new IntValue(5))))),
                            new CompStmt(new AssignStmt("b",
                                    new ArithExp(ArithOp.ADD, new VariableExp("a"),
                                            new ValueExp(new IntValue(1)))),
                                    new PrintStmt(new VariableExp("b"))))));
    // Ex3: bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)
    public static final IStmt ex3 = new CompStmt(new VariableDeclarationStmt("a", new BoolType()),
            new CompStmt(new VariableDeclarationStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                            new CompStmt(new IfStmt(new VariableExp("a"),
                                    new AssignStmt("v", new ValueExp(new IntValue(2))),
                                    new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                    new PrintStmt(new VariableExp("v"))))));
    // Ex4: string varf; varf=test.in; openRFile(varf); int varc; ReadFileStmt(varf.varc); print(varc); ReadFileStmt(varf.varc); print(varc); CloseRFileStmt(varf);
    public static final IStmt ex4 = fileExample("test.in");

    public static IStmt fileExample(String fileName) {
        return new CompStmt(new VariableDeclarationStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue(fileName))),
                        new CompStmt(new OpenRFileStmt(new VariableExp("varf")),
                                new CompStmt(new VariableDeclarationStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VariableExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VariableExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VariableExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VariableExp("varc")),
                                                                        new CloseRFileStmt(new VariableExp("varf"))))))))));
    }

    // EX5 – int a; int b; a = 5; b = 7;
    // (if a < b then print(a) else print(b))
    public static final IStmt ex5 = new CompStmt(new VariableDeclarationStmt("a", new IntType()),
            new CompStmt(new VariableDeclarationStmt("b", new IntType()),
                    new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
                            new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
                                    new IfStmt(new RelationalExp(RelOp.LT, new VariableExp("a"), new VariableExp("b")),
                                            new PrintStmt(new VariableExp("a")), new PrintStmt(new VariableExp("b")))))));
    public static final IStmt ex6 = new CompStmt(new VariableDeclarationStmt("v", new RefType(new IntType())),
            new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                    new CompStmt(new VariableDeclarationStmt("a",new RefType(new RefType(new IntType()))),
                            new CompStmt(new NewStmt("a",new VariableExp("v")),
                                    new CompStmt(new PrintStmt(new VariableExp("v")),new PrintStmt(new VariableExp("a")))))));
    // Ex7: Ref int v; new(v, 20); Ref Ref int a; new(a, v);
    //      print(rH(v)); print(rH(rH(a)) + 5)
    public static final IStmt ex7 = new CompStmt(
            new VariableDeclarationStmt("v", new RefType(new IntType())),
            new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new VariableDeclarationStmt("a", new RefType(new RefType(new IntType()))),
                            new CompStmt(new NewStmt("a", new VariableExp("v")),
                                    new CompStmt(new PrintStmt(new ReadHeapExp(new VariableExp("v"))),
                                            new PrintStmt(new ArithExp(ArithOp.ADD,
                                                    new ReadHeapExp(new ReadHeapExp(new VariableExp("a"))),
                                                    new ValueExp(new IntValue(5)))))))));

    // Ex8: Ref int v; new(v, 20); print(rH(v)); wH(v, 30);
    //      print(rH(v) + 5)
    public static final IStmt ex8 = new CompStmt(
            new VariableDeclarationStmt("v", new RefType(new IntType())),
            new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new PrintStmt(new ReadHeapExp(new VariableExp("v"))),
                            new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                    new PrintStmt(new ArithExp(ArithOp.ADD,
                                            new ReadHeapExp(new VariableExp("v")),
                                            new ValueExp(new IntValue(5))))))));

    // Ex9: Ref int v; new(v, 20); Ref Ref int a; new(a, v);
    //      new(v, 30); print(rH(rH(a)))
    public static final IStmt ex9 = new CompStmt(
            new VariableDeclarationStmt("v", new RefType(new IntType())),
            new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(new VariableDeclarationStmt("a", new RefType(new RefType(new IntType()))),
                            new CompStmt(new NewStmt("a", new VariableExp("v")),
                                    new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                            new PrintStmt(new ReadHeapExp(
                                                    new ReadHeapExp(new VariableExp("a")))))))));

    public static final IStmt ex10 =
            new CompStmt(new VariableDeclarationStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                            new CompStmt(new WhileStmt(
                                    new RelationalExp(RelOp.GT, new VariableExp("v"), new ValueExp(new IntValue(0))),
                                    new CompStmt(new PrintStmt(new VariableExp("v")),
                                            new AssignStmt("v", new ArithExp(ArithOp.SUB,
                                                    new VariableExp("v"), new ValueExp(new IntValue(1)))))),
                                    new PrintStmt(new VariableExp("v")))));

}
