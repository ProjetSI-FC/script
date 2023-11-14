package projetsi.interfaces;

import java.util.List;
import java.util.Map;

import projetsi.models.Pair;

/**
 * Interface class for the result of the spot file parser (keywords and metadatas)
 */
public interface SpotFileKeywords {

    /**
     * @return The list of keywords with their number of occurrences
     */
    List<Pair<String, Integer>> getKeywordsList();

    /**
     * @return A dictionary of the spot file metadatas (transcript file path, audio file path, video file path, xml file path, occurence score, etc...)
     */
    Map<String, String> getSpotFileMetadatas();
}