package projetsi.importjson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import projetsi.export.Export;
import projetsi.importjson.Import;
import projetsi.models.KeywordSearchHashMap;
import projetsi.models.SimpleFileMetadataWithScore;



class ImportTest {
    
    @Test
    void ExportToJsonARightDocument(){

        //Before Test
        String content = "{\"hashObject\":[{\"keywords\":[\"EuroNation\",\"Test\"],\"results\":[{\"score\":0,\"metadata\":{\"hashkey\":\"Test\",\"value\":\"test\"}},{\"score\":5,\"metadata\":{\"hashkey\":\"EuroNation\",\"value\":\"test\"}}]},{\"keywords\":[\"EuroNation\",\"Macron\",\"Test\"],\"results\":[{\"score\":0,\"metadata\":{\"hashkey\":\"Test\",\"value\":\"test\"}},{\"score\":5,\"metadata\":{\"hashkey\":\"EuroNation\",\"value\":\"test\"}}]}]}";
        String fileName = "ressources/output.json";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Method to test
        KeywordSearchHashMap hashmap = Import.importHashMapFromJson();

        //assertions
        SortedSet<String> keywords = new TreeSet<>();
        keywords.add("EuroNation");
        keywords.add("Test");
        SimpleFileMetadataWithScore file1 = new SimpleFileMetadataWithScore();
        SimpleFileMetadataWithScore file2 = new SimpleFileMetadataWithScore();
        file1.addMetadata("EuroNation","test");
        file1.setScore(5);
        file2.addMetadata("Test","test");
        KeywordSearchHashMap searchMap = new KeywordSearchHashMap();
        searchMap.add(keywords,file1);
        searchMap.add(keywords, file2);
        keywords.add("Macron");
        searchMap.add(keywords, file1);
        searchMap.add(keywords, file2);
        assertEquals(hashmap, searchMap);
    }
}
