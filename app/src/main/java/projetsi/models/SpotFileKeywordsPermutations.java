package projetsi.models;

import projetsi.interfaces.SpotfileKeywordsPermutations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedSet;

public class SpotFileKeywordsPermutations implements SpotfileKeywordsPermutations {

    private Map<String, Integer> keywordsMap;
    private Map<String, String> SpotFileMetadatas;
    private List<Combination> permutations;

    /**
     * Default Constructor
     */
    public SpotFileKeywordsPermutations() {
        this.keywordsMap = new HashMap<>();
        this.SpotFileMetadatas = new HashMap<>();
        this.permutations = new ArrayList<>();
    }

    /**
     * Constructor with 2 parameters and keywords as list of pairs
     * 
     * @param keywordsList list of Pair<keyword, number of occurence>
     * @param metadatas
     */
    public SpotFileKeywordsPermutations(List<Pair<String, Integer>> keywordsList, Map<String, String> metadatas) {
        for (Pair<String, Integer> pair : keywordsList) {
            this.keywordsMap.put(pair.getFirst(), pair.getSecond());
        }
        this.SpotFileMetadatas = metadatas;
        this.permutations = new ArrayList<>();
    }

    /**
     * Constructor with 2 parameters and keywords as map
     * 
     * @param keywordsmap map : keyword as key and occurence as value
     * @param metadatas
     */
    public SpotFileKeywordsPermutations(Map<String, Integer> keywordsmap, Map<String, String> metadatas) {
        this.keywordsMap = keywordsmap;
        this.SpotFileMetadatas = metadatas;
        this.permutations = new ArrayList<>();
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
    @Override
    public Map<String, String> getSpotFileMetadatas() {
        return SpotFileMetadatas;
    }

    /**
     * permutations getter
     * 
     * @return permutations
     */
    public List<Combination> getPermutations() {
        return this.permutations;
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
     * permutations setter
     * 
     * @param perm
     */
    public void setPermutations(List<Combination> perm) {
        this.permutations = perm;
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
     * Add a combination to permutations attribute
     */
    public void addCombination(Combination comb) {
        this.permutations.add(comb);
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
        str += "\n permutations : \n";
        for (Pair<SortedSet<String>, Integer> combination : this) {
            str += combination.toString();
            str += "\n";
        }
        return str;
    }

    /**
     * Function that creates iterator object that iterates permutations attribute
     * 
     * @return the Iterator<> Object
     */
    @Override
    public Iterator<Pair<SortedSet<String>, Integer>> iterator() {
        /* Creates iterator and implements hasNext() and next() methods */
        return new Iterator<Pair<SortedSet<String>, Integer>>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < permutations.size();
            }

            @Override
            public Combination next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException("Iterator position is out of bounds");
                }
                return permutations.get(index++);
            }
        };
    }

    /**
     * Computes the combinations of keywords
     */
    public void computePermutations() {
        List<String> keywords = new ArrayList<>(getKeywordsMap().keySet());
        int n = keywords.size();
        /* Go trough keywords */
        for (int i = 1; i < (1 << n); i++) { // 1 << n is equivalent to 2^n
            /* Creates a combination */
            Combination combination = new Combination();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    /* Add word to the combination */
                    combination.addWord(keywords.get(j));
                }
            }
            /* Add the combination in permutations iterable attribute */
            addCombination(combination);
        }
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
     * Calculates the score of the combinations
     * For each combination in the iterable attribute permutations,
     */
    public void calculateAllScores() {
        for (Combination combination : permutations) {
            calculateOneScore(combination);
        }
    }

}
