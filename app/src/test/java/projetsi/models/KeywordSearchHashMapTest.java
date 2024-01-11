package projetsi.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import projetsi.interfaces.SearchMapData;

class KeywordSearchHashMapTest {

    @Test
    void KeywordSearchHashMapGet() {
        KeywordSearchHashMap classUnderTest = new KeywordSearchHashMap();
        SortedSet<String> keywords = new TreeSet<>();
        keywords.add("keyword1");
        keywords.add("keyword2");

        SimpleFileMetadataWithScore metadata = new SimpleFileMetadataWithScore();
        metadata.setScore(1);
        metadata.addMetadata("file", "path");

        classUnderTest.add(keywords, metadata);

        assertEquals(keywords, classUnderTest.get(keywords).getKeywordsSet(),
                "keywords and getKeywordsSet should be equal");
        assertTrue(classUnderTest.get(keywords).getFilesSet().contains(metadata), "metadata should be in filesSet");
    }

    @Test
    void KeywordSearchHashMapGetWithNull() {
        KeywordSearchHashMap classUnderTest = new KeywordSearchHashMap();
        assertThrows(NullPointerException.class, () -> {
            classUnderTest.get(null);
        }, "get should throw NullPointerException if keywords is null");
    }

    @Test
    void KeywordSearchHashMapAddMultipleFile() {
        KeywordSearchHashMap classUnderTest = new KeywordSearchHashMap();
        SortedSet<String> keywords1 = new TreeSet<>();
        keywords1.add("keyword1");
        keywords1.add("keyword2");

        SimpleFileMetadataWithScore metadata1 = new SimpleFileMetadataWithScore();
        metadata1.setScore(1);
        metadata1.addMetadata("file1", "path1");

        SortedSet<String> keywords2 = new TreeSet<>();
        keywords2.add("keyword1");
        keywords2.add("keyword2");

        SimpleFileMetadataWithScore metadata2 = new SimpleFileMetadataWithScore();
        metadata2.setScore(2);
        metadata2.addMetadata("file2", "path2");

        classUnderTest.add(keywords1, metadata1);
        classUnderTest.add(keywords2, metadata2);

        assertEquals(keywords1, classUnderTest.get(keywords1).getKeywordsSet(),
                "keywords and getKeywordsSet should be equal");
        assertTrue(classUnderTest.get(keywords1).getFilesSet().contains(metadata1), "metadata1 should be in filesSet");
        assertTrue(classUnderTest.get(keywords1).getFilesSet().contains(metadata2),
                "metadata2 should be in the same filesSet than metadata1");
    }

    @Test
    void KeywordSearchHashMapAddWithNullKeywords() {
        KeywordSearchHashMap classUnderTest = new KeywordSearchHashMap();

        SimpleFileMetadataWithScore metadata = new SimpleFileMetadataWithScore();
        metadata.setScore(1);
        metadata.addMetadata("file", "path");

        assertThrows(NullPointerException.class, () -> {
            classUnderTest.add(null, metadata);
        }, "add should throw NullPointerException if keywords is null");
    }

    @Test
    void KeywordSearchHashMapAddWithNullMetadata() {
        KeywordSearchHashMap classUnderTest = new KeywordSearchHashMap();
        SortedSet<String> keywords = new TreeSet<>();
        keywords.add("keyword1");
        keywords.add("keyword2");

        assertThrows(NullPointerException.class, () -> {
            classUnderTest.add(keywords, null);
        }, "add should throw NullPointerException if metadata is null");
    }

    @Test
    void KeywordSearchHashMapAddWithEmptyKeywords() {
        KeywordSearchHashMap classUnderTest = new KeywordSearchHashMap();
        SortedSet<String> keywords = new TreeSet<>();

        SimpleFileMetadataWithScore metadata = new SimpleFileMetadataWithScore();
        metadata.setScore(1);
        metadata.addMetadata("file", "path");

        assertThrows(IllegalArgumentException.class, () -> {
            classUnderTest.add(keywords, metadata);
        }, "add should throw IllegalArgumentException if keywords is empty");
    }

    @Test
    void KeywordSearchHashMapAddWithEmptyMetadata() {
        KeywordSearchHashMap classUnderTest = new KeywordSearchHashMap();
        SortedSet<String> keywords = new TreeSet<>();
        keywords.add("keyword1");
        keywords.add("keyword2");

        SimpleFileMetadataWithScore metadata = new SimpleFileMetadataWithScore();
        metadata.setScore(1);

        assertThrows(IllegalArgumentException.class, () -> {
            classUnderTest.add(keywords, metadata);
        }, "add should throw IllegalArgumentException if metadata is empty");
    }

    @Test
    void KeywordSearchHashMapIterator() {
        KeywordSearchHashMap classUnderTest = new KeywordSearchHashMap();
        SortedSet<String> keywords1 = new TreeSet<>();
        keywords1.add("keyword1");
        keywords1.add("keyword2");

        SimpleFileMetadataWithScore metadata1 = new SimpleFileMetadataWithScore();
        metadata1.setScore(1);
        metadata1.addMetadata("file1", "path1");

        SortedSet<String> keywords2 = new TreeSet<>();
        keywords2.add("keyword3");
        keywords2.add("keyword4");

        SimpleFileMetadataWithScore metadata2 = new SimpleFileMetadataWithScore();
        metadata2.setScore(2);
        metadata2.addMetadata("file2", "path2");

        classUnderTest.add(keywords1, metadata1);
        classUnderTest.add(keywords2, metadata2);

        List<SearchMapData> listSearchMapDatas = new ArrayList<>();

        for (SearchMapData data : classUnderTest) {
            listSearchMapDatas.add(data);
        }

        assertEquals(2, listSearchMapDatas.size(), "iterator should have returned 2 elements");

        assertTrue(listSearchMapDatas.contains(classUnderTest.get(keywords1)),
                "iterator should have returned the metadata1");
        assertTrue(listSearchMapDatas.contains(classUnderTest.get(keywords2)),
                "iterator should have returned the metadata2");
    }

    @Test
    void KeywordSearchHashMapIteratorEmpty() {
        KeywordSearchHashMap classUnderTest = new KeywordSearchHashMap();

        List<SearchMapData> listSearchMapDatas = new ArrayList<>();

        assertFalse(classUnderTest.iterator().hasNext(), "iterator should have returned false");

        for (SearchMapData data : classUnderTest) {
            listSearchMapDatas.add(data);
        }

        assertEquals(0, listSearchMapDatas.size(), "iterator should have returned 0 element");
    }
}
