package projetsi.keywordsetvalidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.Test;

import projetsi.Stubs.SpotfileKeywordsPermutationsStub;
import projetsi.interfaces.KeywordSearchMap;
import projetsi.interfaces.SpotfileKeywordsPermutations;
import projetsi.models.Pair;

class KeywordSearchMapControllerTest {

        @Test
        void KeywordSearchMapControllerCreateSearchMapFromKeywordsPermutations() {

                KeywordSearchMapController classUnderTest = new KeywordSearchMapController();

                Map<String, String> spotFileMetadatas = new HashMap<>();
                spotFileMetadatas.put("test_key1", "test_value1");

                List<Pair<SortedSet<String>, Integer>> permutations = new ArrayList<>();
                SortedSet<String> keywords1 = new TreeSet<>();
                keywords1.add("test_keyword1");
                keywords1.add("test_keyword2");
                keywords1.add("test_keyword3");
                SortedSet<String> keywords2 = new TreeSet<>();
                keywords2.add("test_keyword4");
                keywords2.add("test_keyword5");
                keywords2.add("test_keyword6");
                SortedSet<String> keywords3 = new TreeSet<>();
                keywords3.add("test_keyword7");
                keywords3.add("test_keyword8");
                keywords3.add("test_keyword9");

                permutations.add(new Pair<>(keywords1, 5));
                permutations.add(new Pair<>(keywords2, 10));
                permutations.add(new Pair<>(keywords3, 15));

                SpotfileKeywordsPermutations spotFileKeywords = new SpotfileKeywordsPermutationsStub(spotFileMetadatas,
                                permutations);

                KeywordSearchMap keywordsMap = classUnderTest.createSearchMapFromKeywordsPermutations(spotFileKeywords,
                                10);

                assertNull(keywordsMap.get(keywords1), "keywords1 should not be in the keywordmap");
                assertNotNull(keywordsMap.get(keywords2), "keywords2 should be in the keywordmap");
                assertNotNull(keywordsMap.get(keywords3), "keywords3 should be in the keywordmap");

                assertEquals(keywordsMap.get(keywords2).getFilesSet().iterator().next().getFileMetadata(),
                                spotFileMetadatas,
                                "keywords2 should have the spotfilemetadatas");
                assertEquals(keywordsMap.get(keywords3).getFilesSet().iterator().next().getFileMetadata(),
                                spotFileMetadatas,
                                "keywords3 should have the spotfilemetadatas");

                assertEquals(10, keywordsMap.get(keywords2).getFilesSet().iterator().next().getScore(),
                                "keywords2 should have the score 10");
                assertEquals(15, keywordsMap.get(keywords3).getFilesSet().iterator().next().getScore(),
                                "keywords3 should have the score 15");

        }

        @Test
        void KeywordSearchMapControllerCreateSearchMapFromKeywordsPermutationsWithBlockingQueue() {

                KeywordSearchMapController classUnderTest = new KeywordSearchMapController();

                Map<String, String> spotFileMetadatas = new HashMap<>();
                spotFileMetadatas.put("test_key1", "test_value1");

                BlockingQueue<Pair<Pair<SortedSet<String>, Integer>, Map<String, String>>> permutations = new ArrayBlockingQueue<>(
                                4);
                SortedSet<String> keywords1 = new TreeSet<>();
                keywords1.add("test_keyword1");
                keywords1.add("test_keyword2");
                keywords1.add("test_keyword3");
                SortedSet<String> keywords2 = new TreeSet<>();
                keywords2.add("test_keyword4");
                keywords2.add("test_keyword5");
                keywords2.add("test_keyword6");
                SortedSet<String> keywords3 = new TreeSet<>();
                keywords3.add("test_keyword7");
                keywords3.add("test_keyword8");
                keywords3.add("test_keyword9");

                permutations.add(new Pair<>(new Pair<>(keywords1, 5), spotFileMetadatas));
                permutations.add(new Pair<>(new Pair<>(keywords2, 10), spotFileMetadatas));
                permutations.add(new Pair<>(new Pair<>(keywords3, 15), spotFileMetadatas));
                permutations.add(new Pair<>(null, null)); // end of permutations

                KeywordSearchMap keywordsMap = null;

                try {
                        keywordsMap = classUnderTest.createSearchMapFromKeywordsPermutations(permutations, 10, 1);
                        assertNull(keywordsMap.get(keywords1), "keywords1 should not be in the keywordmap");
                        assertNotNull(keywordsMap.get(keywords2), "keywords2 should be in the keywordmap");
                        assertNotNull(keywordsMap.get(keywords3), "keywords3 should be in the keywordmap");

                        assertEquals(keywordsMap.get(keywords2).getFilesSet().iterator().next().getFileMetadata(),
                                        spotFileMetadatas,
                                        "keywords2 should have the spotfilemetadatas");
                        assertEquals(keywordsMap.get(keywords3).getFilesSet().iterator().next().getFileMetadata(),
                                        spotFileMetadatas,
                                        "keywords3 should have the spotfilemetadatas");

                        assertEquals(10, keywordsMap.get(keywords2).getFilesSet().iterator().next().getScore(),
                                        "keywords2 should have the score 10");
                        assertEquals(15, keywordsMap.get(keywords3).getFilesSet().iterator().next().getScore(),
                                        "keywords3 should have the score 15");
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }

        }

}
