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
                classUnderTest.computePermutations(permutationsQueue);

                /* Get the combinations from the queue */
                List<Combination> combinationsList = new ArrayList<>();
                for (Pair<Map<String, String>, Combination> pair : permutationsQueue) {
                        combinationsList.add(pair.getSecond());
                        /* Display it*/ System.out.println(pair.getSecond().toString());
                }
                /* Create all the stub combinations manually and put them into a list in order to compare */
                List<Combination> combinationsStub = create3setMockCombinationList();
                /* Compare the lists of combinations */
                boolean shouldBeTrueIfCombinationsAreEqual = true;
                for (Combination combination : combinationsStub) {
                        shouldBeTrueIfCombinationsAreEqual = combinationsList.contains(combination);
                }
                assertTrue(shouldBeTrueIfCombinationsAreEqual, "Should be true if all the combinations are found");
        }
   

        /**
         * Tests if the combinations are limited to 4 keywords
         */
        @Test
        void PermutationsTestLimitSize() {
        /* Creates mock object and put in keywords */
        PermutationsGenerator classUnderTest = new PermutationsGenerator();
        Map<String, Integer> keywordsMap = new HashMap<>();
        keywordsMap.put("rouge", 0);
        keywordsMap.put("president", 0);
        keywordsMap.put("euro", 0);
        keywordsMap.put("national", 0);
        keywordsMap.put("france", 0);
        classUnderTest.setKeywordsMap(keywordsMap);
        /* Computes the combinations */
        BlockingQueue<Pair<Map<String, String>, Combination>> permutationsQueue = new LinkedBlockingQueue<>();
        classUnderTest.computePermutations(permutationsQueue);

        /* Get the combinations from the queue */
        List<Combination> combinationsList = new ArrayList<>();
        for (Pair<Map<String, String>, Combination> pair : permutationsQueue) {
                combinationsList.add(pair.getSecond());
                /* Display it*/ System.out.println(pair.getSecond().toString());
        }
        /* Check that each combination has at most 4 keywords */
        boolean allCombinationsHaveAtMostFourKeywords = combinationsList.stream()
        .allMatch(combination -> combination.getFirst().size() <= 4);
        
        /* Additional test: compare all the computations */
        /* Create all the stub combinations manually and put them into a list in order to compare */
        List<Combination> combinationsStub = create4setMockCombinationList();
        /* Compare the lists of combinations */
        boolean shouldBeTrueIfCombinationsAreEqual = true;
        for (Combination combination : combinationsStub) {
                shouldBeTrueIfCombinationsAreEqual = combinationsList.contains(combination);
        }

        assertTrue(allCombinationsHaveAtMostFourKeywords, "Should be true if all combinations have at most 4 keywords");
        assertTrue(shouldBeTrueIfCombinationsAreEqual, "Should be true if all the combinations are found");
        }


        /**
         * Creates the mock combinations List the set of 3 keywords
         * 
         * @return
         */
        private List<Combination> create3setMockCombinationList() {
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





       /**
         * Creates the mock combinations List the set of 4 keywords
         * 
         * @return
         */
        private List<Combination> create4setMockCombinationList() {
        List<Combination> combinationsList = new ArrayList<>();
        
        // Permutations de taille 1
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("national")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("president")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("rouge")), 0));
        
        // Permutations de taille 2
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "national")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "president")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "rouge")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("national", "president")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("national", "rouge")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("president", "rouge")), 0));
        
        // Permutations de taille 3
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "national", "president")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "national", "rouge")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "president", "rouge")), 0));
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("national", "president", "rouge")), 0));
        
        // Permutations de taille 4
        combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "national", "president", "rouge")), 0));
        
        return combinationsList;
        }
        

        private List<Combination> create5setMockCombinationList() {
                List<Combination> combinationsList = new ArrayList<>();
                
                // Permutations de taille 1
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("france")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("national")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("president")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("rouge")), 0));
            
                // Permutations de taille 2
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "france")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "national")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "president")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("france", "national")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("france", "president")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("france", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("national", "president")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("national", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("president", "rouge")), 0));
            
                // Permutations de taille 3
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "france", "national")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "france", "president")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "france", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "national", "president")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "national", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "president", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("france", "national", "president")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("france", "national", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("france", "president", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("national", "president", "rouge")), 0));
            
                // Permutations de taille 4
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "france", "national", "president")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "france", "national", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "france", "president", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "national", "president", "rouge")), 0));
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("france", "national", "president", "rouge")), 0));
            
                // Permutations de taille 5
                combinationsList.add(new Combination(new TreeSet<>(Arrays.asList("euro", "france", "national", "president", "rouge")), 0));
                
                return combinationsList;
            }
            

}


