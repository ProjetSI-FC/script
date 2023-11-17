package projetsi.models;

import org.junit.jupiter.api.Test;

import projetsi.models.Combination;
// import projetsi.models.CombinationsList;
import projetsi.models.Pair;
import projetsi.models.SpotFileKeywordsPermutations;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SpotFileKeywordsPermutationsTest {

    @Test
    void SpotFileKeywordsPermutationsTestBuildNotInit() {
        SpotFileKeywordsPermutations classUnderTest = new SpotFileKeywordsPermutations();
        assertNotNull(classUnderTest.getKeywordsMap(), "getKeywordsMap should return an empty map if not initialized");
        assertNotNull(classUnderTest.getSpotFileMetadatas(),
                "getSpotFileMetadatas should return an empty map if not initialized");
        assertNotNull(classUnderTest.getPermutations(),
                "getPermutations should return an empty list if not initialized");
    }

    @Test
    void SpotFileKeywordsPermutationsTestGetters() {
        /* Creates mock object */
        SpotFileKeywordsPermutations classUnderTest = new SpotFileKeywordsPermutations();
        Map<String, Integer> keywordsMap = new HashMap<>();
        keywordsMap.put("rouge", 10);
        keywordsMap.put("president", 5);
        keywordsMap.put("education", 20);
        classUnderTest.setKeywordsMap(keywordsMap);
        classUnderTest.addMetadata("fichier test", "video");
        classUnderTest.addCombination(new Combination(
                new TreeSet<>(
                        Arrays.asList("rouge", "president", "education")),
                0));
        assertNotNull(classUnderTest.getKeywordsMap(), "getKeywordsMap should return the keywordsMap");
        assertNotNull(classUnderTest.getSpotFileMetadatas(), "getSecond should return the metadatas");
        assertNotNull(classUnderTest.getPermutations(), "getSecond should return the permutations");
    }

    /**
     * Tests if the combinations are the ones expected
     */
    @Test
    void SpotFileKeywordsPermutationsTestComputePermutations() {
        /* Creates mock object and put in keywords */
        SpotFileKeywordsPermutations classUnderTest = new SpotFileKeywordsPermutations();
        Map<String, Integer> keywordsMap = new HashMap<>();
        keywordsMap.put("rouge", 0);
        keywordsMap.put("president", 0);
        keywordsMap.put("education", 0);
        classUnderTest.setKeywordsMap(keywordsMap);
        /* Computes the combinations */
        classUnderTest.computePermutations();
        /* Display the computed combinations */
        for (Pair<SortedSet<String>, Integer> pair : classUnderTest) {
            System.out.println((new Combination(pair)).toString());
        }
        /* Create all the combinations manually and put them into a list */
        List<Combination> combinationsList = createMockCombinationList();
        /* Compare the lists of combinations */
        boolean shouldBeTrueIfCombinationsAreEqual = true;
        for (Combination combination : combinationsList) {
            shouldBeTrueIfCombinationsAreEqual = classUnderTest.getPermutations().contains(combination);
        }
        assertTrue(shouldBeTrueIfCombinationsAreEqual, "Should be true if all the combinations are found");
    }

    /**
     * Tests the calculus of the score of a combination
     */
    @Test
    void SpotFileKeywordsPermutationsTestCalulateOneScore() {
        SpotFileKeywordsPermutations classUnderTest = new SpotFileKeywordsPermutations();
        /* Adds a map to fetch occurences */
        Map<String, Integer> keywordsMap = new HashMap<>();
        keywordsMap.put("five_occ", 5);
        keywordsMap.put("three_occ", 3);
        keywordsMap.put("nine_occ", 9);
        classUnderTest.setKeywordsMap(keywordsMap);
        /* Adds a combination with corresponding words */
        classUnderTest.addCombination(new Combination(
                new TreeSet<>(
                        Arrays.asList("five_occ", "three_occ", "nine_occ")),
                0));
        /* Calulculates the score of the combination */
        classUnderTest.calculateOneScore(classUnderTest.getPermutations().get(0));
        assertEquals(5, classUnderTest.getPermutations().get(0).getSecond(),
                "should be true if the calulated score is the one expected");
        for (Pair<SortedSet<String>, Integer> pair : classUnderTest) {
            System.out.println((new Combination(pair)).toString());
        }
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
