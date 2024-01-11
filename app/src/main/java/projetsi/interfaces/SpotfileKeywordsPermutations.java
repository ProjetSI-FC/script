package projetsi.interfaces;

import java.util.Map;
import java.util.SortedSet;

import projetsi.models.Pair;

/**
 * Interface class that must be implemented by classes that will compute the permutations of keywords and the associated score for a spot file
 */
public interface SpotfileKeywordsPermutations extends Iterable<Pair<SortedSet<String>, Integer>> {

    /**
     * @return A dictionary of the spot file metadatas (transcript file path, audio file path, video file path, xml file path, occurence score, etc...)
     */
    Map<String, String> getSpotFileMetadatas();
}
