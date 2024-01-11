package projetsi.parser;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import projetsi.interfaces.SpotFileKeywords;
import projetsi.models.Pair;
import projetsi.models.SimpleSpotFileKeywords;

public class SpotParser {

    public static final String SPOT_KEYWORDS = "keywords";
    public static final String SPOT_FILE_KEY = "file";

    public JSONObject getJSONSpot(InputStream spotFileStream) {
        JSONTokener tokener = new JSONTokener(spotFileStream);
        return new JSONObject(tokener);

    }

    public SpotFileKeywords getSpotFileKeywords(File spotFile) {
        InputStream spotFileStream = ParserController.getFileStream(spotFile.getAbsolutePath());
        JSONObject spotJSON = getJSONSpot(spotFileStream);
        JSONArray spotKeywords = spotJSON.getJSONArray(SPOT_KEYWORDS);
        List<Pair<String, Integer>> keywordsList = new ArrayList<>();

        // Loop through each keyword in the JSONArray
        for (int i = 0; i < spotKeywords.length(); i++) {
            JSONArray keywordArray = spotKeywords.getJSONArray(i);
            String keyword = keywordArray.getString(1);

            // Check if the keyword already exists in the list
            boolean keywordExists = false;
            for (Pair<String, Integer> pair : keywordsList) {
                if (pair.getFirst().equals(keyword)) {
                    int count = pair.getSecond();
                    pair.setSecond(count + 1);
                    keywordExists = true;
                    break;
                }
            }

            // If the keyword doesn't exist in the list, add it with a count of 1
            if (!keywordExists) {
                keywordsList.add(new Pair<>(keyword, 1));
            }
        }
        Map<String, String> spotFileMetadatas = Map.of("file", spotJSON.getString(SPOT_FILE_KEY));

        // Add the list of keywords and their occurrences to the SpotFileKeywords object
        return new SimpleSpotFileKeywords(keywordsList, spotFileMetadatas);
    }
}
