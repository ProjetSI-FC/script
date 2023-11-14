package projetsi.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class SimpleSpotFileKeywordTest {

    @Test
    void getEmptyKeywordsList() {
        SimpleSpotFileKeywords classUnderTest = new SimpleSpotFileKeywords(null, null);
        assertThrowsExactly(NullPointerException.class, () -> classUnderTest.getKeywordsList());
    }

    @Test
    void getEmptySpotFileMetadatas() {
        SimpleSpotFileKeywords classUnderTest = new SimpleSpotFileKeywords(null, null);
        assertThrowsExactly(NullPointerException.class, () -> classUnderTest.getSpotFileMetadatas());
    }

    @Test
    void setKeywordsList() {
        SimpleSpotFileKeywords classUnderTest = new SimpleSpotFileKeywords(null, null);
        List<Pair<String, Integer>> keywordsList = List.of(new Pair<>("first", 2), new Pair<>("second", 3));
        classUnderTest.setKeywordsList(keywordsList);
        assertEquals(classUnderTest.getKeywordsList().get(0).getFirst(), keywordsList.get(0).getFirst());
        assertEquals(classUnderTest.getKeywordsList().get(0).getSecond(), keywordsList.get(0).getSecond());
        assertEquals(classUnderTest.getKeywordsList().get(1).getFirst(), keywordsList.get(1).getFirst());
        assertEquals(classUnderTest.getKeywordsList().get(1).getSecond(), keywordsList.get(1).getSecond());
    }

    @Test
    void setSpotFileMetadatas() {
        SimpleSpotFileKeywords classUnderTest = new SimpleSpotFileKeywords(null, null);
        classUnderTest.setSpotFileMetadatas(Map.of("data", "datas", "file", "test.txt"));
        assertEquals(classUnderTest.getSpotFileMetadatas(), Map.of("data", "datas", "file", "test.txt"));
    }
}
