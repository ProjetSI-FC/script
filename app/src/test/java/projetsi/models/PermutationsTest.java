package projetsi.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.Test;

class PermutationsTest {

        @Test
        void PermutationsTestBuildNotInit() {
                PermutationsGenerator classUnderTest = new PermutationsGenerator();
                assertNotNull(classUnderTest.getKeywordsMap(),
                                "getKeywordsMap should return an empty map if not initialized");
                assertNotNull(classUnderTest.getSpotFileMetadatas(),
                                "getSpotFileMetadatas should return an empty map if not initialized");
        }

        @Test
        void PermutationsTestGetters() {
                /* Creates mock object */
                PermutationsGenerator classUnderTest = new PermutationsGenerator();
                Map<String, Integer> keywordsMap = new HashMap<>();
                keywordsMap.put("rouge", 10);
                keywordsMap.put("president", 5);
                keywordsMap.put("education", 20);
                classUnderTest.setKeywordsMap(keywordsMap);
                classUnderTest.addMetadata("fichier test", "video");
                assertNotNull(classUnderTest.getKeywordsMap(), "getKeywordsMap should return the keywordsMap");
                assertNotNull(classUnderTest.getSpotFileMetadatas(), "getSecond should return the metadatas");
        }

        /**
         * Tests the calculus of the score of a combination
         */
        @Test
        void PermutationsTestCalulateOneScore() {
                PermutationsGenerator classUnderTest = new PermutationsGenerator();
                /* Adds a map to fetch occurences */
                Map<String, Integer> keywordsMap = new HashMap<>();
                keywordsMap.put("five_occ", 5);
                keywordsMap.put("three_occ", 3);
                keywordsMap.put("nine_occ", 9);
                classUnderTest.setKeywordsMap(keywordsMap);
                /* Create two combinations with the keywords */
                Combination combination1 = new Combination(
                                new TreeSet<>(
                                                Arrays.asList("five_occ")),
                                0);
                Combination combination2 = new Combination(
                                new TreeSet<>(
                                                Arrays.asList("five_occ", "three_occ", "nine_occ")),
                                0);
                /* Calulculates the scores of the combinations */
                classUnderTest.calculateOneScore(combination1);
                classUnderTest.calculateOneScore(combination2);
                assertEquals(5, combination1.getSecond(),
                                "should be true if the 1st calulated score is the one expected");
                assertEquals(5, combination2.getSecond(),
                                "should be true if the 2nd calulated score is the one expected");
        }

        /**
         * Tests if the combinations are the ones expected
         */
        @Test
        void PermutationsTestComputePermutations() {
                /* Creates mock object and put in keywords */
                PermutationsGenerator classUnderTest = new PermutationsGenerator();
                Map<String, Integer> keywordsMap = new HashMap<>();
                keywordsMap.put("rouge", 0);
                keywordsMap.put("president", 0);
                keywordsMap.put("education", 0);
                classUnderTest.setKeywordsMap(keywordsMap);
                /* Computes the combinations */
                BlockingQueue<Pair<Map<String, String>, Combination>> permutationsQueue = new LinkedBlockingQueue<>();
                try {
                        classUnderTest.computePermutations(permutationsQueue);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                        assertTrue(false, "Should not throw an exception");
                }

                /* Get the combinations from the queue */
                List<Combination> combinationsList = new ArrayList<>();
                for (Pair<Map<String, String>, Combination> pair : permutationsQueue) {
                        combinationsList.add(pair.getSecond());
                        /* Display it*/ System.out.println(pair.getSecond().toString());
                }
                /* Create all the stub combinations manually and put them into a list in order to compare */
                List<Combination> combinationsStub = createMockCombinationList();
                /* Compare the lists of combinations */
                boolean shouldBeTrueIfCombinationsAreEqual = true;
                for (Combination combination : combinationsStub) {
                        shouldBeTrueIfCombinationsAreEqual = combinationsList.contains(combination);
                }
                assertTrue(shouldBeTrueIfCombinationsAreEqual, "Should be true if all the combinations are found");
        }

        /**
         * Creates the mock combinations List
         * 
         * @return
         */
        private List<Combination> createMockCombinationList() {
                List<Combination> combinationsList = new ArrayList<>();
                combinationsList.add(new Combination(
                                new TreeSet<>(
                                                Arrays.asList("education", "president", "rouge")),
                                0));
                combinationsList.add(new Combination(
                                new TreeSet<>(
                                                Arrays.asList("education")),
                                0));
                combinationsList.add(new Combination(
                                new TreeSet<>(
                                                Arrays.asList("rouge")),
                                0));
                combinationsList.add(new Combination(
                                new TreeSet<>(
                                                Arrays.asList("education", "rouge")),
                                0));
                combinationsList.add(new Combination(
                                new TreeSet<>(
                                                Arrays.asList("president")),
                                0));
                combinationsList.add(new Combination(
                                new TreeSet<>(
                                                Arrays.asList("education", "president")),
                                0));
                combinationsList.add(new Combination(
                                new TreeSet<>(
                                                Arrays.asList("president", "rouge")),
                                0));
                combinationsList.add(new Combination(
                                new TreeSet<>(
                                                Arrays.asList("education", "president", "rouge")),
                                0));
                return combinationsList;
        }

}
