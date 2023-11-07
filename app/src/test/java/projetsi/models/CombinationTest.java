package projetsi.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.TreeSet;

class CombinationTest {

    /**
     * Tests if the score of combinations is set to 0 if not initialized
     */
    @Test
    void CombinationTestScoreNoInit(){
        Combination classUnderTest1 = new Combination();
        assertEquals(0, classUnderTest1.getSecond(), "getSecond should return a score of 0 if not initialized");
    }

    /**
     * Tests that setting a negative score throws the correct Exception
     */
    @Test
    void CombinationTestNegativeScore() {
        Combination classUnderTest = new Combination();
        assertThrows(IllegalArgumentException.class, () -> {
            classUnderTest.setSecond(-1);
        }, "setScore should throw IllegalArgumentException if score is negative");
    }
    
    /**
     * Tests the equality of Combinations objects
     */
    @Test
    void CombinationTestEquality() {
        Combination classUnderTest1 = new Combination(
            new TreeSet<>(
                Arrays.asList("rouge", "president", "education")
            ),0
        );

        Combination classUnderTest2 = new Combination(
            new TreeSet<>(
                Arrays.asList("rouge", "president", "education")
            ),0
        );

        Combination classUnderTest3 = new Combination(
            new TreeSet<>(
                Arrays.asList("president", "education", "rouge")
            ),0
        );
        assertEquals(classUnderTest1, classUnderTest2, "should return true if equals");
        assertEquals(classUnderTest1, classUnderTest3, "should return true if equals");
    }

    /**
     * Tests the no equality of Combinations objects
     */
    @Test
    void CombinationTestNoEquality() {
        Combination classUnderTest1 = new Combination(
            new TreeSet<>(
                Arrays.asList("rouge", "president", "education")
            ),0
        );
        Combination classUnderTest2 = new Combination(
            new TreeSet<>(
                Arrays.asList("rouge")
            ),0
        );
        Combination classUnderTest3 = new Combination(
            new TreeSet<>(
                Arrays.asList("rouge", "president", "scolaire")
            ),0
        );
        assertNotEquals(classUnderTest1, classUnderTest2, "should return false if not equals");    
        assertNotEquals(classUnderTest1, classUnderTest3, "should return false if not equals");    
    }
}