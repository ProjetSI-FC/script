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
            BlockingQueue<Pair<Pair<SortedSet<String>, Integer>, Map<String, String>>> permutations,
            int threshold) throws InterruptedException {
        KeywordSearchHashMap keywordsMap = new KeywordSearchHashMap();
        try {
            while (true) {
                Pair<Pair<SortedSet<String>, Integer>, Map<String, String>> permutation = permutations.take();
                if (permutation.getFirst().getFirst() == null) {
                    break;
                }
                if (permutation.getFirst().getSecond() >= threshold) {
                    SimpleFileMetadataWithScore metadata = new SimpleFileMetadataWithScore();
                    metadata.setScore(permutation.getFirst().getSecond());
                    for (Map.Entry<String, String> items : permutation.getSecond().entrySet()) {
                        metadata.addMetadata(items.getKey(), items.getValue());
                    }
                    keywordsMap.add(permutation.getFirst().getFirst(), metadata);
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