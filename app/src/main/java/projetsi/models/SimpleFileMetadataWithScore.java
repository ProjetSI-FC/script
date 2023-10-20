package projetsi.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import projetsi.interfaces.FileMetadataWithScore;

public class SimpleFileMetadataWithScore implements FileMetadataWithScore {

    private int score;
    private HashMap<String, String> metadata;

    public SimpleFileMetadataWithScore() {
        this.score = 0;
        this.metadata = new HashMap<>();
    }

    @Override
    public Map<String, String> getFileMetadata() {
        return Collections.unmodifiableMap(metadata);
    }

    @Override
    public Integer getScore() {
        return score;
    }

    public void addMetadata(String name, String value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        metadata.put(name, value);
    }

    public boolean removeMetadata(String name) {
        Objects.requireNonNull(name);
        return metadata.remove(name) != null;
    }
    
    public void setScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score must be positive");
        }
        this.score = score;
    }

    @Override
    public String toString() {
        return "SimpleFileMetadataWithScore{" + "score=" + score + ", metadata=" + metadata + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SimpleFileMetadataWithScore)) {
            return false;
        }
        SimpleFileMetadataWithScore simpleFileMetadataWithScore = (SimpleFileMetadataWithScore) o;
        return score == simpleFileMetadataWithScore.score && Objects.equals(metadata, simpleFileMetadataWithScore.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, metadata);
    }
}
