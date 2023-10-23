package projetsi.Stubs;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import projetsi.interfaces.SpotfileKeywordsPermutations;
import projetsi.models.Pair;

public class SpotfileKeywordsPermutationsStub implements SpotfileKeywordsPermutations {

    private Map<String, String> spotFileMetadatas;

    private List<Pair<SortedSet<String>, Integer>> permutations;

    public SpotfileKeywordsPermutationsStub(Map<String, String> spotFileMetadatas,
            List<Pair<SortedSet<String>, Integer>> permutations) {
        this.spotFileMetadatas = spotFileMetadatas;
        this.permutations = permutations;
    }

    @Override
    public Iterator<Pair<SortedSet<String>, Integer>> iterator() {
        return permutations.iterator();
    }

    @Override
    public Map<String, String> getSpotFileMetadatas() {
        return spotFileMetadatas;
    }

}
