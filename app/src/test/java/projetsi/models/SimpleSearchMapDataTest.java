package projetsi.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.SortedSet;
import java.util.TreeSet;

import projetsi.interfaces.SearchMapData;

class SimpleSearchMapDataTest {

    @Test
    void simpleSearchMapDataGetKeywordsSetShouldBeUnmodifiable() {
        SearchMapData classUnderTest = new SimpleSearchMapData();
        assertThrows(UnsupportedOperationException.class, () -> {
            classUnderTest.getKeywordsSet().add("test_keyword");
        }, "getKeywordsSet should return an unmodifiable set");
    }

    @Test
    void simpleSearchMapDataGetKeywordsSetShouldBeEmptyBeforeInit() {
        SearchMapData classUnderTest = new SimpleSearchMapData();
        assertTrue(classUnderTest.getKeywordsSet().isEmpty(), "getKeywordsSet should be empty before init");
    }

    @Test
    void simpleSearchMapDataGetFilesSetShouldBeUnmodifiable() {
        SearchMapData classUnderTest = new SimpleSearchMapData();
        assertThrows(UnsupportedOperationException.class, () -> {
            classUnderTest.getFilesSet().add(new SimpleFileMetadataWithScore());
        }, "getFilesSet should return an unmodifiable set");
    }

    @Test
    void simpleSearchMapDataGetFilesSetShouldBeEmptyBeforeInit() {
        SearchMapData classUnderTest = new SimpleSearchMapData();
        assertTrue(classUnderTest.getFilesSet().isEmpty(), "getFilesSet should be empty before init");
    }

    @Test
    void simpleSearchMapDataSetKeywordsSet() {
        SimpleSearchMapData classUnderTest = new SimpleSearchMapData();
        SortedSet<String> keywords = new TreeSet<>();
        keywords.add("test_keyword1");
        keywords.add("test_keyword2");
        keywords.add("test_keyword3");

        classUnderTest.setKeywordsSet(keywords);
        assertEquals(keywords, classUnderTest.getKeywordsSet(), "setKeywordsSet should set keywords");
    }

    @Test
    void simpleSearchMapDataSetKeywordsSetWithNull() {
        SimpleSearchMapData classUnderTest = new SimpleSearchMapData();
        assertThrows(NullPointerException.class, () -> {
            classUnderTest.setKeywordsSet(null);
        }, "setKeywordsSet should throw NullPointerException if keywords is null");
    }

    @Test
    void simpleSearchMapDataAddFile() {
        SimpleSearchMapData classUnderTest = new SimpleSearchMapData();
        SimpleFileMetadataWithScore file = new SimpleFileMetadataWithScore();
        classUnderTest.addFile(file);
        assertTrue(classUnderTest.getFilesSet().contains(file), "addFile should add file");
    }

    @Test
    void simpleSearchMapDataAddFileWithNull() {
        SimpleSearchMapData classUnderTest = new SimpleSearchMapData();
        assertThrows(NullPointerException.class, () -> {
            classUnderTest.addFile(null);
        }, "addFile should throw NullPointerException if file is null");
    }

    @Test
    void simpleSearchMapDataRemoveFile() {
        SimpleSearchMapData classUnderTest = new SimpleSearchMapData();
        SimpleFileMetadataWithScore file = new SimpleFileMetadataWithScore();
        classUnderTest.addFile(file);
        assertTrue(classUnderTest.removeFile(file), "removeFile should return true if file removed");
        assertFalse(classUnderTest.getFilesSet().contains(file), "file should be removed");
    }

    @Test
    void simpleSearchMapDataRemoveFileWithNull() {
        SimpleSearchMapData classUnderTest = new SimpleSearchMapData();
        assertThrows(NullPointerException.class, () -> {
            classUnderTest.removeFile(null);
        }, "removeFile should throw NullPointerException if file is null");
    }

    @Test
    void simpleSearchMapDataEquals() {
        SimpleSearchMapData classUnderTest = new SimpleSearchMapData();
        SimpleSearchMapData classUnderTest2 = new SimpleSearchMapData();
        SimpleFileMetadataWithScore file1 = new SimpleFileMetadataWithScore();
        SimpleFileMetadataWithScore file2 = new SimpleFileMetadataWithScore();
        SortedSet<String> keywords1 = new TreeSet<>();
        SortedSet<String> keywords2 = new TreeSet<>();

        keywords1.add("test_keyword1");
        keywords1.add("test_keyword2");
        keywords1.add("test_keyword3");

        keywords2.add("test_keyword1");
        keywords2.add("test_keyword2");
        keywords2.add("test_keyword3");

        classUnderTest.setKeywordsSet(keywords1);
        classUnderTest2.setKeywordsSet(keywords2);

        classUnderTest.addFile(file1);
        classUnderTest2.addFile(file2);

        assertEquals(classUnderTest, classUnderTest2, "equals should return true if objects are equal");
    }

    @Test
    void simpleSearchMapDataNotEqual() {
        SimpleSearchMapData classUnderTest = new SimpleSearchMapData();
        SimpleSearchMapData classUnderTest2 = new SimpleSearchMapData();
        SimpleFileMetadataWithScore file1 = new SimpleFileMetadataWithScore();
        file1.setScore(1);
        SimpleFileMetadataWithScore file2 = new SimpleFileMetadataWithScore();
        file2.setScore(2);
        SortedSet<String> keywords1 = new TreeSet<>();
        SortedSet<String> keywords2 = new TreeSet<>();

        keywords1.add("test_keyword1");
        keywords1.add("test_keyword2");
        keywords1.add("test_keyword3");

        keywords2.add("test_keyword1");
        keywords2.add("test_keyword2");
        keywords2.add("test_keyword3");

        classUnderTest.setKeywordsSet(keywords1);
        classUnderTest2.setKeywordsSet(keywords2);

        classUnderTest.addFile(file1);
        classUnderTest2.addFile(file2);

        classUnderTest2.addFile(file1);

        assertNotEquals(classUnderTest, classUnderTest2, "equals should return false if objects are not equal");
        
        classUnderTest = new SimpleSearchMapData();
        classUnderTest2 = new SimpleSearchMapData();

        keywords1.clear();
        keywords2.clear();

        keywords1.add("test_keyword1");
        keywords1.add("test_keyword2");
        keywords1.add("test_keyword3");

        keywords2.add("test_keyword4");
        keywords2.add("test_keyword5");
        keywords2.add("test_keyword6");

        classUnderTest.setKeywordsSet(keywords1);
        classUnderTest2.setKeywordsSet(keywords2);

        classUnderTest.addFile(file1);
        classUnderTest2.addFile(file2);
    } 
}
