package projetsi.keywordsetvalidator;

import java.util.Map;
import java.util.SortedSet;

import projetsi.interfaces.KeywordSearchMap;
import projetsi.interfaces.SpotfileKeywordsPermutations;
import projetsi.models.KeywordSearchHashMap;
import projetsi.models.Pair;
import projetsi.models.SimpleFileMetadataWithScore;

public class KeywordSearchMapController {

    public KeywordSearchMap createSearchMapFromKeywordsPermutations(SpotfileKeywordsPermutations permutations,
            int threshold) {
        KeywordSearchHashMap keywordsMap = new KeywordSearchHashMap();
        for (Pair<SortedSet<String>, Integer> permutation : permutations) {
            if (permutation.getSecond() >= threshold) {
                SimpleFileMetadataWithScore metadata = new SimpleFileMetadataWithScore();
                metadata.setScore(permutation.getSecond());
                for (Map.Entry<String, String> items : permutations.getSpotFileMetadatas().entrySet()) {
                    metadata.addMetadata(items.getKey(), items.getValue());
                }
                keywordsMap.add(permutation.getFirst(), metadata);
            }
        }
        return keywordsMap;
    }
}
