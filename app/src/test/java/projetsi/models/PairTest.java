package projetsi.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PairTest {
    
    @Test void pairGetFirstAndSecond() {
        Pair<String, Integer> classUnderTest = new Pair<>("first", 2);
        assertNotNull(classUnderTest.getFirst(), "getFirst should return the first element");
        assertNotNull(classUnderTest.getSecond(), "getSecond should return the second element");
    }

    @Test void pairSetFirstAndSecond() {
        Pair<String, Integer> classUnderTest = new Pair<>("first", 2);
        classUnderTest.setFirst("first");
        classUnderTest.setSecond(2);
        assertNotNull(classUnderTest.getFirst(), "getFirst should return the first element");
        assertNotNull(classUnderTest.getSecond(), "getSecond should return the second element");
    }
}
