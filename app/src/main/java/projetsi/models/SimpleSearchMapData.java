package projetsi.models;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Objects;
import java.util.HashSet;
import java.util.Collections;

import projetsi.interfaces.FileMetadataWithScore;
import projetsi.interfaces.SearchMapData;

public class SimpleSearchMapData implements SearchMapData {

    private TreeSet<String> keywords;
    private HashSet<FileMetadataWithScore> files;

    public SimpleSearchMapData() {
        this.keywords = null;
        this.files = new HashSet<>();
    }

    @Override
    public SortedSet<String> getKeywordsSet() {
        if (keywords == null) {
            return Collections.emptySortedSet();
        }
        return Collections.unmodifiableSortedSet(keywords);
    }

    @Override
    public Set<FileMetadataWithScore> getFilesSet() {
        return Collections.unmodifiableSet(files);
    }

    public void setKeywordsSet(SortedSet<String> keywords) {
        Objects.requireNonNull(keywords);
        if(this.keywords != null) {
            this.keywords.clear();
            this.keywords.addAll(keywords);
        } else {
            this.keywords = new TreeSet<>(keywords);
        }
    }

    public void addFile(FileMetadataWithScore file) {
        Objects.requireNonNull(file);
        files.add(file);
    }

    public boolean removeFile(FileMetadataWithScore file) {
        Objects.requireNonNull(file);
        return files.remove(file);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SimpleSearchMapData)) {
            return false;
        }
        SimpleSearchMapData simpleSearchMapData = (SimpleSearchMapData) o;
        return Objects.equals(keywords, simpleSearchMapData.keywords) && Objects.equals(files, simpleSearchMapData.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywords, files);
    }
}
