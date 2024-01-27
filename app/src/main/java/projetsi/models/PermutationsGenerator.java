package projetsi.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.BlockingQueue;

import projetsi.interfaces.SpotFileKeywords;

public class PermutationsGenerator {

    private Map<String, Integer> keywordsMap;
    private Map<String, String> spotFileMetadatas;

    /**
     * Default Constructor
     */
    public PermutationsGenerator() {
        this.keywordsMap = new HashMap<>();
        this.spotFileMetadatas = new HashMap<>();
    }

    /***
     * Constructor with a SpotFileKeywords object
     * 
     * @param spotFileKeywords
     */
    public PermutationsGenerator(SpotFileKeywords spotFileKeywords) {
        // Call default constructor
        this();
        for (Pair<String, Integer> pair : spotFileKeywords.getKeywordsList()) {
            this.keywordsMap.put(pair.getFirst(), pair.getSecond());
        }
        this.spotFileMetadatas = spotFileKeywords.getSpotFileMetadatas();
    }

    /**
     * Constructor with 2 parameters and keywords as list of pairs
     * 
     * @param keywordsList list of Pair<keyword, number of occurence>
     * @param metadatas
     */
    public PermutationsGenerator(List<Pair<String, Integer>> keywordsList, Map<String, String> metadatas) {
        for (Pair<String, Integer> pair : keywordsList) {
            this.keywordsMap.put(pair.getFirst(), pair.getSecond());
        }
        this.spotFileMetadatas = metadatas;
    }

    /**
     * Constructor with 2 parameters and keywords as map
     * 
     * @param keywordsmap map : keyword as key and occurence as value
     * @param metadatas
     */
    public PermutationsGenerator(Map<String, Integer> keywordsmap, Map<String, String> metadatas) {
        this.keywordsMap = keywordsmap;
        this.spotFileMetadatas = metadatas;
    }

    /**
     * keywordsMap getter
     * 
     * @return KeywordsMap
     */
    public Map<String, Integer> getKeywordsMap() {
        return this.keywordsMap;
    }

    /**
     * SpotFileMetadatas getter
     * 
     * @return SpotFileMetadatas
     */
    public Map<String, String> getSpotFileMetadatas() {
        return spotFileMetadatas;
    }

    /**
     * keywordsMap setter
     * 
     * @param map the map to set
     */
    public void setKeywordsMap(Map<String, Integer> map) {
        this.keywordsMap = map;
    }

    /**
     * SpotFileMetadatas setter
     * 
     * @param map the map to set
     */
    public void setSpotFileMetadatas(Map<String, String> map) {
        this.spotFileMetadatas = map;
    }

    /**
     * Add a keyword and the number of occurence to keywordsMap
     * 
     * @param word
     * @param occ
     */
    public void addKeywordToMap(String word, Integer occ) {
        this.keywordsMap.put(word, occ);
    }

    /**
     * Add metadata
     * 
     * @param name
     * @param value
     */
    public void addMetadata(String name, String value) {
        this.spotFileMetadatas.put(name, value);
    }

    /**
     * Converts to string and displays the object
     */
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("\n keywordsMap : \n");
        for (Map.Entry<String, Integer> entry : keywordsMap.entrySet()) {
            strBuilder.append(entry.getKey() + " ");
            strBuilder.append(entry.getValue() + "\n");
        }
        strBuilder.append("\n metadatas : \n");
        for (Map.Entry<String, String> entry : spotFileMetadatas.entrySet()) {
            strBuilder.append(entry.getKey() + " : ");
            strBuilder.append(entry.getValue() + "\n");
        }
        return strBuilder.toString();
    }

    /**
     * Calculates the score of one given combination
     * Fetches the occurences of each word from the keywordsMap
     * 
     * @param combination the combination which we want to attribute score to
     */
    public void calculateOneScore(Combination combination) {
        int score = 0;
        for (String word : combination.getFirst()) {
            score += keywordsMap.get(word);
        }
        combination.setSecond(score / combination.getFirst().size());
    }

    /**
     * Computes the combinations of keywords
     * 
     * @throws Exception
     */
    public void computePermutations(BlockingQueue<Pair<Pair<SortedSet<String>, Integer>, Map<String, String>>> queue,
            int limit)
            throws InterruptedException {
        List<String> keywords = new ArrayList<>(getKeywordsMap().keySet());
        int n = keywords.size();
        /* Go trough keywords */
        for (int i = 1; i < (1 << n); i++) { // 1 << n is equivalent to 2^n
            if (Integer.bitCount(i) > limit) {
                continue;
            }
            /* Creates a combination */
            Combination combination = new Combination();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    /* Add word to the combination */
                    combination.addWord(keywords.get(j));
                }
            }
            /* Calculate score of the combination */
            calculateOneScore(combination);
            /* Add combination to the queue */
            try {
                queue.put(new Pair<>(combination, spotFileMetadatas));
            } catch (InterruptedException e) {
                throw new InterruptedException("Erreur lors de l'ajout à la file d'attente");
            }
        }
    }
}
