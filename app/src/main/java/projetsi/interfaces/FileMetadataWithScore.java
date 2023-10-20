package projetsi.interfaces;

import java.util.Map;

public interface FileMetadataWithScore {
    Map<String, String> getFileMetadata();
    Integer getScore();
}
