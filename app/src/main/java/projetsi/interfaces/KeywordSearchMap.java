package projetsi.interfaces;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.SortedSet;

import projetsi.models.Pair;

/**
 * Interface class that must be implemented by class that will represent the Map of keyword lists with the associated files
 * the structure is the following :
 * Map [
 *   String : hash of the keyword list,
 *   Pair {
 *     SortedSet<String> : keyword list,
 *     Set [ : set of files associated with the keyword list
 *       Pair { 
 *         Map<String, String> : spot file metadatas,
 *         Integer : occurence score of the keyword list in the given spot file
 *     ]
 * ]
 */
public interface KeywordSearchMap extends Iterable<Pair<? extends SortedSet<String>, ? extends Set<Pair<? extends Map<String, String>, Integer>>>>{
    Pair<? extends SortedSet<String>, ? extends Set<Pair<? extends Map<String, String>, Integer>>> get(SortedSet<String> keywords);


}
