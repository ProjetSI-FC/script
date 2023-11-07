package projetsi.models;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Objects;

/**
 * Class for the scored Combination
 * It contains the list of words and the score of the Combination
 */
public class Combination extends Pair<SortedSet<String> , Integer> {
    
    /**
     * Default constructor, assigns a score of 0
     */
    public Combination(){
        super(new TreeSet<>(), 0);
    }
    
    /**
     * Constructor from the set and the score 
     * @param first the set of combinations
     * @param second the score
     */
    public Combination(SortedSet<String> first, Integer score) {
    super(first, score);
    }

    /**
     * Copy Constructor
     * @param pair
     */
    public Combination(Pair<SortedSet<String> , Integer> pair){
        super(pair.getFirst(), pair.getSecond());
    }

    /**
     * Second attribute setter
     * @param score the score to set
     */
    @Override
    public void setSecond(Integer score){
        if (score < 0) {
            throw new IllegalArgumentException("Score must be positive");
        }
        this.setSecond(score);
    }
    

    /**
     * Add a word in the combination set
     * The word is automatically put at its correct index due to SortedSet propriety
     * @param word the word to add
     */
    public void addWord(String word){
        if (!(this.getFirst().contains(word))) {
            this.getFirst().add(word);
        }
    }

    /**
     * Converts the combination to string
     */
    public String toString(){
        String listWords = this.getFirst().toString();
        return listWords + this.getSecond().toString();
    }

    /**
     * Compares this Combination to another object to check for equality.
     *
     * @param o The object to compare to this Combination.
     * @return true if the objects are equal in terms of their content, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(this.getFirst(), pair.getFirst()) && Objects.equals(this.getSecond(), pair.getSecond());
    }

    /**
     * Computes a hash code (mock memory id) for this Pair object.
     *
     * @return The hash code based on the content of the first and second attributes.
     */
    @Override
    public int hashCode() {
        // first attribute modifies the hashcode
        int hashcode = this.getFirst() != null ? this.getFirst().hashCode() : 0;
        // second attribute modifies the hashcode
        hashcode = 31 * hashcode + (this.getSecond() != null ? this.getSecond().hashCode() : 0);
        return hashcode;
    }
}