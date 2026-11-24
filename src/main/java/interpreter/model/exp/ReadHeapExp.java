package interpreter.model.exp;

import interpreter.exceptions.InvalidTypeException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.NotAllocateOnHeapException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;


public record ReadHeapExp(IExp exp) implements  IExp{
    @Override
    public IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {


        if (!(exp.eval(symTable,heap) instanceof RefValue refValue)) {
            throw new InvalidTypeException(exp.toString() + " is not of ref type");
        }
        if(!heap.isDefined(refValue.address())){
            throw new NotAllocateOnHeapException(refValue.toString()+"at address "+refValue.address()+" not allocated on heap");
        }

        return heap.get(refValue.address());
    }
    @Override public String toString() { return "readHeap(" + exp + ")"; }
}
