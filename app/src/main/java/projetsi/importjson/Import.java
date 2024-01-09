package projetsi.importjson;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import projetsi.interfaces.FileMetadataWithScore;
import projetsi.models.KeywordSearchHashMap;
import projetsi.models.SimpleFileMetadataWithScore;



public class Import {
    public static KeywordSearchHashMap importHashMapFromJson(){

        JSONArray array = null;
        try (InputStream fileReader = new FileInputStream("ressources/output.json")) {
            JSONTokener tokener = new JSONTokener(fileReader);
            array = new JSONArray(tokener);
        } catch (IOException e) {
            e.printStackTrace();
  
        }
        KeywordSearchHashMap hashMap = new KeywordSearchHashMap();
        for (int i=0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            JSONArray keywordsArray = obj.getJSONArray("keywords");
            SortedSet<String> keywords = getKeywordsFromJSON(keywordsArray);
            JSONArray resultArray = obj.getJSONArray("results");
            hashMap = new KeywordSearchHashMap();
            for(int j=0 ; j < resultArray.length(); j++){
                FileMetadataWithScore fileMetadata = getFileMetadataFromJSON(resultArray.getJSONObject(j));
                hashMap.add(keywords,fileMetadata);
            }
        }
        return hashMap;
    }
    private static SortedSet<String> getKeywordsFromJSON(JSONArray array){
        SortedSet<String> keywords = new TreeSet<>();
        for(int i = 0; i < array.length(); i++){
            String keyword = array.getString(i);
            keywords.add(keyword);
        }
        return keywords;
    }
    private static FileMetadataWithScore getFileMetadataFromJSON(JSONObject obj){
        SimpleFileMetadataWithScore file = new SimpleFileMetadataWithScore();
        file.setScore(Integer.parseInt(obj.getString("score")));
        JSONObject metadata = obj.getJSONObject("metadata");
        file.addMetadata(metadata.getString("hashkey"), metadata.getString("value"));
        return file;
    }
}
