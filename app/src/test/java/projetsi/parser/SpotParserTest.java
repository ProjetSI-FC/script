package projetsi.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import projetsi.interfaces.SpotFileKeywords;

public class SpotParserTest {

    @Test
    void getJSONSpot() throws IOException {
        SpotParser classUnderTest = new SpotParser();
        File file = new File("src/test/resources/2021010100_spot.json");
        FileInputStream inputStream = new FileInputStream(file);
        JSONObject spotJSON = classUnderTest.getJSONSpot(inputStream);
        assertNotNull(spotJSON);
        assertTrue(spotJSON.has("keywords") && !spotJSON.isNull("keywords"));
        assertTrue(spotJSON.has("file") && !spotJSON.isNull("file"));
    }

    @Test
    void getSpotFileKeywords() {
        SpotParser classUnderTest = new SpotParser();
        File file = new File("src/test/resources/2021010100_spot.json");
        SpotFileKeywords spotFileKeywords = classUnderTest.getSpotFileKeywords(file);
        assertNotNull(spotFileKeywords);
        assertTrue(spotFileKeywords.getKeywordsList().size() > 0);
        assertTrue(spotFileKeywords.getSpotFileMetadatas().size() > 0);
        assertEquals("politique", spotFileKeywords.getKeywordsList().get(1).getFirst());
        assertEquals(7, spotFileKeywords.getKeywordsList().get(1).getSecond());
    }
}
