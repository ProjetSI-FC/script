package projetsi.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.math.BigInteger;

import projetsi.interfaces.SpotFileKeywords;

public class PermutationsGenerator {

    private Map<String, Integer> keywordsMap;
    private Map<String, String> SpotFileMetadatas;

    /**
     * Default Constructor
     */
    public PermutationsGenerator() {
        this.keywordsMap = new HashMap<>();
        this.SpotFileMetadatas = new HashMap<>();
    }

    /***
     * Constructor with a SpotFileKeywords object
     * 
     * @param spotFileKeywords
     */
    public PermutationsGenerator(SpotFileKeywords spotFileKeywords) {
        for (Pair<String, Integer> pair : spotFileKeywords.getKeywordsList()) {
            this.keywordsMap.put(pair.getFirst(), pair.getSecond());
        }
        this.SpotFileMetadatas = spotFileKeywords.getSpotFileMetadatas();
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
        this.SpotFileMetadatas = metadatas;
    }

    /**
     * Constructor with 2 parameters and keywords as map
     * 
     * @param keywordsmap map : keyword as key and occurence as value
     * @param metadatas
     */
    public PermutationsGenerator(Map<String, Integer> keywordsmap, Map<String, String> metadatas) {
        this.keywordsMap = keywordsmap;
        this.SpotFileMetadatas = metadatas;
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
        return SpotFileMetadatas;
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
        this.SpotFileMetadatas = map;
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
        this.SpotFileMetadatas.put(name, value);
    }

    /**
     * Converts to string and displays the object
     */
    public String toString() {
        String str = new String();
        str += "\n keywordsMap : \n";
        for (Map.Entry<String, Integer> entry : keywordsMap.entrySet()) {
            str += entry.getKey() + " ";
            str += entry.getValue() + "\n";
        }
        str += "\n metadatas : \n";
        for (Map.Entry<String, String> entry : SpotFileMetadatas.entrySet()) {
            str += entry.getKey() + " : ";
            str += entry.getValue() + "\n";
        }
        return str;
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
        combination.setSecond((int) (score / combination.getFirst().size()));
    }
    
    /**
     * Computes the combinations of keywords
     */
    public void computePermutations(BlockingQueue queue) {
        List<String> keywords = new ArrayList<>(getKeywordsMap().keySet());
        int n = keywords.size();
        /* Go trough keywords */
        BigInteger limit = BigInteger.ONE.shiftLeft(n);   
        for (BigInteger i = BigInteger.ONE; i.compareTo(limit) < 0 && i.bitCount() <= 4; i = i.add(BigInteger.ONE)) {
            Combination combination = new Combination();
            for (int j = 0; j < n; j++) {
                if (i.testBit(j)) {
                    combination.addWord(keywords.get(j));
                }
            }
            /* Calculate score of the combination */
            calculateOneScore(combination);
            /* Add combination to the queue */
            try {
                queue.put(new Pair(SpotFileMetadatas, combination));
            } catch (Exception e) {
                // Gestion d'autres exceptions avec un message explicite
                throw new RuntimeException("Erreur lors de l'ajout à la file d'attente : " + e.getMessage(), e);
            }
        }
    }


}
