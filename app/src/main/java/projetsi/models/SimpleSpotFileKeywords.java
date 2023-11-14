package projetsi.models;

import java.util.List;
import java.util.Map;

import projetsi.interfaces.SpotFileKeywords;

public class SimpleSpotFileKeywords implements SpotFileKeywords {

    private List<Pair<String, Integer>> keywordsList;
    private Map<String, String> spotFileMetadatas;

    public SimpleSpotFileKeywords(List<Pair<String, Integer>> keywordsList, Map<String, String> spotFileMetadatas) {
        this.keywordsList = keywordsList;
        this.spotFileMetadatas = spotFileMetadatas;
    }

    @Override
    public List<Pair<String, Integer>> getKeywordsList() {
        if (keywordsList != null)
            return keywordsList;
        throw new NullPointerException("Uninitialized field 'keywordsList'");
    }

    public void setKeywordsList(List<Pair<String, Integer>> keywordsList) {
        this.keywordsList = keywordsList;
    }

    @Override
    public Map<String, String> getSpotFileMetadatas() {
        if (spotFileMetadatas != null)
            return spotFileMetadatas;
        throw new NullPointerException("Uninitialized field 'spotFileMetadatas'");
    }

    public void setSpotFileMetadatas(Map<String, String> spotFileMetadatas) {
        this.spotFileMetadatas = spotFileMetadatas;
    }

}
