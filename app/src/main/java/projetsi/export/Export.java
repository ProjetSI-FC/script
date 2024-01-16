package projetsi.export;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.json.JSONArray;
import org.json.JSONObject;

import projetsi.interfaces.FileMetadataWithScore;
import projetsi.interfaces.KeywordSearchMap;
import projetsi.interfaces.SearchMapData;

public class Export {
    private Export() {
    }

    public static void exportToJson(KeywordSearchMap hash) {
        String fileName = "output.json";
        JSONObject main = new JSONObject();
        JSONArray array = new JSONArray();
        for (SearchMapData searchMapData : hash) {
            SortedSet<String> keywords = searchMapData.getKeywordsSet();
            Set<FileMetadataWithScore> files = searchMapData.getFilesSet();
            array.put(exportToJsonObject(keywords, files));
        }
        main.put("hashObject", array);

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName),
                StandardCharsets.UTF_8);) {
            writer.write(main.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject exportToJsonObject(SortedSet<String> keywords, Set<FileMetadataWithScore> files) {
        JSONObject main = new JSONObject();
        JSONArray results = Export.filesToJsonArray(files);
        JSONArray keywordArray = Export.keywordsToJsonArray(keywords);
        main.put("keywords", keywordArray);
        main.put("results", results);
        return main;
    }

    private static JSONArray keywordsToJsonArray(SortedSet<String> keywords) {

        JSONArray keywordArray = new JSONArray();

        for (String keyword : keywords) {
            keywordArray.put(keyword);
        }
        return keywordArray;
    }

    private static JSONArray filesToJsonArray(Set<FileMetadataWithScore> files) {
        JSONArray results = new JSONArray();

        for (FileMetadataWithScore file : files) {
            JSONObject fileMetaDataWithScore = new JSONObject();
            JSONObject metadata = metaDataToJSonObject(file.getFileMetadata());
            fileMetaDataWithScore.put("metadata", metadata);
            fileMetaDataWithScore.put("score", file.getScore());
            results.put(fileMetaDataWithScore);
        }
        return results;
    }

    private static JSONObject metaDataToJSonObject(Map<String, String> fileMetaData) {
        JSONObject metadata = new JSONObject();
        for (Map.Entry<String, String> entry : fileMetaData.entrySet()) {
            metadata.put("hashkey", entry.getKey());
            metadata.put("value", entry.getValue());
        }
        return metadata;
    }

    public static boolean isValidJsonObject(JSONObject jsonObject) {
        return jsonObject.has("hashObject");
    }
}
