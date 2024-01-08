package projetsi.export;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.json.JSONArray;
import org.json.JSONObject;

import projetsi.interfaces.FileMetadataWithScore;
import projetsi.interfaces.KeywordSearchMap;
import projetsi.interfaces.SearchMapData;

public class Export{
    private Export(){}
    public static void exportToJson(KeywordSearchMap hash){
        String fileName = "ressources/output.json";
        JSONObject main = new JSONObject();
        JSONArray array = new JSONArray();
        for (SearchMapData searchMapData : hash) {
            SortedSet<String> keywords = searchMapData.getKeywordsSet();
            Set<FileMetadataWithScore> files = searchMapData.getFilesSet();
            array.put(exportToJsonObject(keywords,files));
        }
        main.put("hashObject",array);

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(main.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
  
        }
    }
    private static JSONObject exportToJsonObject(SortedSet<String> keywords , Set<FileMetadataWithScore> files ){
        JSONObject main = new JSONObject();
        JSONArray results = Export.filesToJsonArray(files);
        JSONArray keywordArray = Export.keywordsToJsonArray(keywords);
        main.put("keywords", keywordArray);
        main.put("results", results);
        return main;
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

    public static boolean isValidJsonObject(JSONObject jsonObject){
        if ( jsonObject.has("hashObject")){
            return true;
        }
        else {
            return false ;
        }
    }
}
