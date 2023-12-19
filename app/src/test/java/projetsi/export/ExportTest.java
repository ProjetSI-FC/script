package projetsi.export;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.HashSet;
import java.io.InputStream;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import org.json.JSONTokener;

import projetsi.interfaces.FileMetadataWithScore;
import projetsi.models.SimpleFileMetadataWithScore;

class ExportTest {
    
    @Test
    void ExportToJsonARightDocument(){

        //Before test
        SortedSet<String> keywords = new TreeSet<>();
        keywords.add("EuroNation");
        keywords.add("Test");
        Set<FileMetadataWithScore> files = new HashSet<>();
        SimpleFileMetadataWithScore file1 = new SimpleFileMetadataWithScore();
        SimpleFileMetadataWithScore file2 = new SimpleFileMetadataWithScore();
        file1.addMetadata("EuroNation","test");
        file1.setScore(5);
        file2.addMetadata("Test","test");
        files.add(file1);
        files.add(file2);


        //Method to test
        Export.exportToJson(keywords, files);

        //assert
        assertDoesNotThrow(()-> {InputStream fileReader = new FileInputStream("ressources/output.json");
        JSONTokener tokener = new JSONTokener(fileReader);
        JSONObject jsonObject = new JSONObject(tokener);
        assertTrue(Export.isValidJsonObject(jsonObject));
        fileReader.close();});
    }

}
