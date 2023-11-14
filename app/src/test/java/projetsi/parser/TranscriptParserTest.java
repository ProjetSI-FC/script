package projetsi.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class TranscriptParserTest {

    @Test
    void transcriptParserParseInputStream() {
        TranscriptParser classUnderTest = new TranscriptParser();
        System.out.println(System.getProperty("user.dir"));
        InputStream inputStream = ParserController.getFileStream("src/test/resources/2021010100_transcript.json");
        JSONObject resp = classUnderTest.parseInputStream(inputStream);
        assertNotNull(resp);
        // assert that the json object contains a specific key
        assertTrue(resp.has("lemmas") && !resp.isNull("lemmas"));
        assertTrue(resp.has("file") && !resp.isNull("file"));
        assertTrue(resp.has("text") && !resp.isNull("text"));
    }

    @Test
    void transcriptParserGetLemmas() {
        TranscriptParser classUnderTest = new TranscriptParser();
        File file = new File("src/test/resources/2021010100_transcript.json");
        JSONArray resp = classUnderTest.getLemmas(file);
        assertNotNull(resp);
        assertTrue(resp.length() > 0);
        assertEquals("pourtant", resp.getString(0));
    }

    @Test
    void transcriptParserGetLemmasCount() {
        TranscriptParser classUnderTest = new TranscriptParser();
        File file = new File("src/test/resources/2021010100_transcript.json");
        int resp = classUnderTest.getLemmasCount(file);
        assertEquals(82, resp);
    }
}
