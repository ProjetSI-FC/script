package projetsi.keywordsetvalidator;

import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.BlockingQueue;

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

    public KeywordSearchMap createSearchMapFromKeywordsPermutations(
            BlockingQueue<Pair<SortedSet<String>, Integer>> permutations, Map<String, String> metadatas,
            int threshold) throws InterruptedException {
        KeywordSearchHashMap keywordsMap = new KeywordSearchHashMap();
        try {
            while (true) {
                Pair<SortedSet<String>, Integer> permutation = permutations.take();
                if (permutation.getFirst() == null) {
                    break;
                }
                if (permutation.getSecond() >= threshold) {
                    SimpleFileMetadataWithScore metadata = new SimpleFileMetadataWithScore();
                    metadata.setScore(permutation.getSecond());
                    for (Map.Entry<String, String> items : metadatas.entrySet()) {
                        metadata.addMetadata(items.getKey(), items.getValue());
                    }
                    keywordsMap.add(permutation.getFirst(), metadata);
                }
            }
        } catch (InterruptedException e) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }
        return keywordsMap;

    }
}