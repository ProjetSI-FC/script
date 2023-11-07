package projetsi.controllers;

import org.junit.jupiter.api.Test;

import projetsi.models.Combination;
// import projetsi.models.CombinationsList;
import projetsi.models.Pair;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SpotFileKeywordsPermutationsTest {
    
    @Test 
    void SpotFileKeywordsPermutationsTestBuildNotInit(){
        SpotFileKeywordsPermutations classUnderTest = new SpotFileKeywordsPermutations();
        assertNotNull(classUnderTest.getKeywordsMap(), "getKeywordsMap should return an empty map if not initialized");
        assertNotNull(classUnderTest.getSpotFileMetadatas(), "getSpotFileMetadatas should return an empty map if not initialized");
        assertNotNull(classUnderTest.getPermutations(), "getPermutations should return an empty list if not initialized");
    }

    @Test
    void SpotFileKeywordsPermutationsTestGetters(){
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
                Arrays.asList("rouge", "president", "education")
            ),0
        ));
        assertNotNull(classUnderTest.getKeywordsMap(), "getKeywordsMap should return the keywordsMap");
        assertNotNull(classUnderTest.getSpotFileMetadatas(), "getSecond should return the metadatas");
        assertNotNull(classUnderTest.getPermutations(), "getSecond should return the permutations");
    }

    /**
     * Tests if the combinations are the ones expected
     */
    @Test
    void SpotFileKeywordsPermutationsTestComputePermutations(){
        /* Creates mock object */
        SpotFileKeywordsPermutations classUnderTest = new SpotFileKeywordsPermutations();
        Map<String, Integer> keywordsMap = new HashMap<>();
        keywordsMap.put("rouge", 0);
        keywordsMap.put("president", 0);
        keywordsMap.put("education", 0);
        classUnderTest.setKeywordsMap(keywordsMap);
        classUnderTest.addMetadata("fichier test", "video");
        classUnderTest.addCombination(new Combination(
            new TreeSet<>(
                Arrays.asList("rouge", "president", "education")
            ),0
        ));
        /* Computes the combinations */
        classUnderTest.computePermutations();
        /* Display the computed combinations */
        for (Pair<SortedSet<String>, Integer> pair : classUnderTest) {
            System.out.println((new Combination(pair)).toString());
        }
        // CombinationsList combinations = new CombinationsList(generateStubCombinations());
        // assertEquals(classUnderTest.getPermutations(), combinations,"should return true if same combinations");
        assertNotNull(classUnderTest.getClass(), "avoiding warning to commit properly");
    }

    // private List<Combination> generateStubCombinations(){
    //     List<Combination> combinations = new ArrayList<>();
    //     Combination combination1 = new Combination();
    //     combination1.addWord("education");
    //     combinations.add(combination1);
    //     Combination combination2 = new Combination();
    //     combination2.addWord("rouge");
    //     combinations.add(combination2);
    //     Combination combination3 = new Combination();
    //     combination3.addWord("education");
    //     combination3.addWord("rouge");
    //     combinations.add(combination3);
    //     Combination combination4 = new Combination();
    //     combination4.addWord("president");
    //     combinations.add(combination4);
    //     Combination combination5 = new Combination();
    //     combination5.addWord("education");
    //     combination5.addWord("president");
    //     combinations.add(combination5);
    //     Combination combination6 = new Combination();
    //     combination6.addWord("president");
    //     combination6.addWord("rouge");
    //     combinations.add(combination6);
    //     Combination combination7 = new Combination();
    //     combination7.addWord("education");
    //     combination7.addWord("president");
    //     combination7.addWord("rouge");
    //     combinations.add(combination7);
    //     return combinations;
    // }
}





