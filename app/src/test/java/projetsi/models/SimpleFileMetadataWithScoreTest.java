package projetsi.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import projetsi.interfaces.FileMetadataWithScore;

class SimpleFileMetadataWithScoreTest {
    
    @Test
    void simpleFileMetadataWithScoreGetScoreNotInit() {
        FileMetadataWithScore classUnderTest = new SimpleFileMetadataWithScore();
        assertEquals(0, classUnderTest.getScore(), "getScore should return 0 if not initialized");
    }

    @Test
    void simpleFileMetadataWithScoreAddMetadata() {
        SimpleFileMetadataWithScore classUnderTest = new SimpleFileMetadataWithScore();
        classUnderTest.addMetadata("test_file", "test_content");
        assertEquals("test_content", classUnderTest.getFileMetadata().get("test_file"), "addMetadata should add metadata");
    }

    @Test
    void simpleFileMetadataWithScoreAddMetadataWithNullName() {
        SimpleFileMetadataWithScore classUnderTest = new SimpleFileMetadataWithScore();
        assertThrows(NullPointerException.class, () -> {
            classUnderTest.addMetadata(null, "test_content");
        }, "addMetadata should throw NullPointerException if name is null");
    }

    @Test
    void simpleFileMetadataWithScoreAddMetadataWithNullValue() {
        SimpleFileMetadataWithScore classUnderTest = new SimpleFileMetadataWithScore();
        assertThrows(NullPointerException.class, () -> {
            classUnderTest.addMetadata("test_file", null);
        }, "addMetadata should throw NullPointerException if value is null");
    }

    @Test
    void simpleFileMetadataWithScoreGetMetadataShouldBeUnmodifiable() {
        SimpleFileMetadataWithScore classUnderTest = new SimpleFileMetadataWithScore();
        classUnderTest.addMetadata("test_file", "test_content");
        assertThrows(UnsupportedOperationException.class, () -> {
            classUnderTest.getFileMetadata().put("test_file", "test_content");
        }, "getFileMetadata should return an unmodifiable map");
    }

    @Test
    void simpleFileMetadataWithScoreRemoveMetadataWithDataToRemove() {
        SimpleFileMetadataWithScore classUnderTest = new SimpleFileMetadataWithScore();
        classUnderTest.addMetadata("test_file", "test_content");
        assertTrue(classUnderTest.removeMetadata("test_file"), "removeMetadata should return true if data removed");
        assertNull(classUnderTest.getFileMetadata().get("test_file"), "metadata should be removed");
    }

    @Test
    void simpleFileMetadataWithScoreRemoveMetadataWithoutDataToRemove() {
        SimpleFileMetadataWithScore classUnderTest = new SimpleFileMetadataWithScore();
        assertFalse(classUnderTest.removeMetadata("test_file"), "removeMetadata should return false if no data removed");
    }

    @Test
    void simpleFileMetadataWithScoreRemoveMetadataWithNullName() {
        SimpleFileMetadataWithScore classUnderTest = new SimpleFileMetadataWithScore();
        assertThrows(NullPointerException.class, () -> {
            classUnderTest.removeMetadata(null);
        }, "removeMetadata should throw NullPointerException if name is null");
    }

    @Test
    void simpleFileMetadataWithScoreEquals() {
        SimpleFileMetadataWithScore classUnderTest = new SimpleFileMetadataWithScore();
        classUnderTest.addMetadata("test_file", "test_content");
        classUnderTest.setScore(1);
        SimpleFileMetadataWithScore classUnderTest2 = new SimpleFileMetadataWithScore();
        classUnderTest2.addMetadata("test_file", "test_content");
        classUnderTest2.setScore(1);
        assertEquals(classUnderTest, classUnderTest2, "equals should return true if equals");
    }

    @Test
    void simpleFileMetadataWithScoreNotEquals() {
        SimpleFileMetadataWithScore classUnderTest = new SimpleFileMetadataWithScore();
        classUnderTest.addMetadata("test_file", "test_content");
        classUnderTest.setScore(1);
        SimpleFileMetadataWithScore classUnderTest2 = new SimpleFileMetadataWithScore();
        classUnderTest2.addMetadata("test_file", "test_content");
        classUnderTest2.setScore(2);
        assertNotEquals(classUnderTest, classUnderTest2, "equals should return false if not equals");

        classUnderTest = new SimpleFileMetadataWithScore();
        classUnderTest.addMetadata("test_file", "test_content");
        classUnderTest.setScore(1);
        classUnderTest2 = new SimpleFileMetadataWithScore();
        classUnderTest2.addMetadata("test_file1", "test_content1");
        classUnderTest2.setScore(1);
        assertNotEquals(classUnderTest, classUnderTest2, "equals should return false if not equals");
    }
}
