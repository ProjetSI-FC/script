package projetsi.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedSet;

import projetsi.interfaces.FileMetadataWithScore;
import projetsi.interfaces.KeywordSearchMap;
import projetsi.interfaces.SearchMapData;

public class KeywordSearchHashMap implements KeywordSearchMap {

    private HashMap<SortedSet<String>, SearchMapData> keywordMap;

    public KeywordSearchHashMap() {
        keywordMap = new HashMap<>();
    }

    @Override
    public Iterator<SearchMapData> iterator() {
        return keywordMap.values().iterator();
    }

    @Override
    public SearchMapData get(SortedSet<String> keywords) {
        Objects.requireNonNull(keywords);
        return keywordMap.get(keywords);
    }

    public void add(SortedSet<String> keywords, FileMetadataWithScore metadata) {
        Objects.requireNonNull(keywords);
        Objects.requireNonNull(metadata);
        if (keywords.isEmpty())
            throw new IllegalArgumentException("Empty keywords set");
        if (metadata.getFileMetadata().isEmpty())
            throw new IllegalArgumentException("Empty metadata set");

        if (keywordMap.containsKey(keywords)) {
            ((SimpleSearchMapData) (keywordMap.get(keywords))).addFile(metadata);
        } else {
            SimpleSearchMapData data = new SimpleSearchMapData();
            data.setKeywordsSet(keywords);
            data.addFile(metadata);
            keywordMap.put(keywords, data);
        }
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof KeywordSearchHashMap)) {
            return false;
        }
        KeywordSearchHashMap keywordSearchHashMap = (KeywordSearchHashMap) o;
        return Objects.equals(keywordMap, keywordSearchHashMap.keywordMap);
    }

    public int hashCode() {
        return Objects.hashCode(keywordMap);
    }
}