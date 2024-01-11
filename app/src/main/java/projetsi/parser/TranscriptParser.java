package projetsi.parser;

import java.io.File;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class TranscriptParser {

    private static final String LEMMAS = "lemmas";

    public JSONObject parseInputStream(InputStream transcriptFileStream) {
        JSONTokener tokener = new JSONTokener(transcriptFileStream);
        return new JSONObject(tokener);
    }

    public JSONArray getLemmas(File transcriptFile) {
        InputStream transcriptFileStream = ParserController.getFileStream(transcriptFile.getAbsolutePath());
        JSONObject transcript = parseInputStream(transcriptFileStream);
        return transcript.getJSONArray(LEMMAS);
    }

    public int getLemmasCount(File transcriptFile) {
        return getLemmas(transcriptFile).length();
    }

}
