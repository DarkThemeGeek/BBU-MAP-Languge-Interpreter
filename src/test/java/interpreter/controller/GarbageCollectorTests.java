package interpreter.controller;

import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GarbageCollectorTests {

    @Test
    void unsafeCollectorDropsChainedReferences() {
        Map<Integer, IValue> heap = Map.of(
                1, new RefValue(2, new IntType()),
                2, new RefValue(3, new IntType()),
                3, new IntValue(5),
                4, new IntValue(99));

        assertEquals(Set.of(1), GarbageCollector.unsafeGarbageCollector(Set.of(1), heap).keySet());
    }

    @Test
    void safeCollectorKeepsChainedReferences() {
        Map<Integer, IValue> heap = Map.of(
                1, new RefValue(2, new IntType()),
                2, new RefValue(3, new IntType()),
                3, new IntValue(5),
                4, new IntValue(99));

        assertEquals(Set.of(1, 2, 3), GarbageCollector.reachableAddresses(Set.of(1), heap));
        assertEquals(Set.of(1, 2, 3), GarbageCollector.safeGarbageCollector(Set.of(1), heap).keySet());
    }

    @Test
    void safeCollectorTerminatesOnReferenceCycles() {
        Map<Integer, IValue> heap = Map.of(
                1, new RefValue(2, new IntType()),
                2, new RefValue(1, new IntType()),
                3, new IntValue(99));

        assertEquals(Set.of(1, 2), GarbageCollector.reachableAddresses(Set.of(1), heap));
        assertEquals(Set.of(1, 2), GarbageCollector.safeGarbageCollector(Set.of(1), heap).keySet());
    }
}
