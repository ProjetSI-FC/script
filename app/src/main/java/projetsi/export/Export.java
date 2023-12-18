package projetsi.export;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.json.JSONArray;
import org.json.JSONObject;

import projetsi.interfaces.FileMetadataWithScore;

public class Export{
    public static void exportToJson(SortedSet<String> keywords , Set<FileMetadataWithScore> files ){
        JSONObject main = new JSONObject();
        JSONArray results = Export.filesToJsonArray(files);
        JSONArray keywordArray = Export.keywordsToJsonArray(keywords);
        main.put("keywords", keywordArray);
        main.put("results", results);

        try (FileWriter fileWriter = new FileWriter("output.json")) {
            fileWriter.write(main.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONArray keywordsToJsonArray(SortedSet<String> keywords){

    JSONArray keywordArray = new JSONArray();
    
    for(String keyword : keywords){
        keywordArray.put(keyword);
    }
    return keywordArray;
    }

    private static JSONArray filesToJsonArray(Set<FileMetadataWithScore> files){
        JSONArray results = new JSONArray();

        for( FileMetadataWithScore file : files){
            JSONObject fileMetaDataWithScore = new JSONObject();
            JSONObject metadata = metaDataToJSonObject(file.getFileMetadata());
            fileMetaDataWithScore.put("metadata", metadata);
            fileMetaDataWithScore.put("score", file.getScore());
            results.put(fileMetaDataWithScore);
        }
        return results;
    }

    private static JSONObject metaDataToJSonObject(Map<String, String> fileMetaData){
        JSONObject metadata = new JSONObject();
        for( Map.Entry<String,String> entry : fileMetaData.entrySet()) {
                        metadata.put("hashkey", entry.getKey());
        metadata.put("value", entry.getValue());
            }
        return metadata;
    }
}
