package projetsi.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import java.util.stream.Stream;

import projetsi.interfaces.SpotFileKeywords;

public class ParserController {

    static Logger logger = Logger.getLogger(ParserController.class.getName());

    public static final String SPOT_FILE_REGEX = "\\d{10}_spot\\.json";
    public static final String TRANSCRIPT_FILE_REGEX = "\\d{10}_transcript\\.json";

    public BlockingQueue<String> getFilesToParse(String rootPath, String regex) {
        Path rootDir = Paths.get(rootPath);
        List<String> pathList = null;
        // Serialized path list file name
        String serializedPathList = rootPath + "test" + ".ser";

        // Check if serialized path list exists
        File serializedFile = new File(serializedPathList);
        if (serializedFile.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(serializedPathList);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                pathList = (ArrayList<String>) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                logger.warning("Error while reading serialized path list: " + serializedPathList);
                e.printStackTrace();
                throw new NullPointerException("Error while reading serialized path list: " + serializedPathList);
            }
        } else {
            try (Stream<Path> pathStream = Files.walk(rootDir)) {
                List<Path> pathListFromStream = pathStream
                        .filter(file -> file.getFileName().toString().matches(regex))
                        .toList();
                pathList = new ArrayList<>();
                for (Path path : pathListFromStream) {
                    pathList.add(path.toString());
                }
                // Serialize path list
                FileOutputStream fileOut = new FileOutputStream(serializedPathList);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(pathList);
                out.close();
                fileOut.close();
            } catch (IOException e) {
                logger.severe("Error while reading directory: " + rootPath);
                e.printStackTrace();
                throw new NullPointerException("Error while reading directory: " + rootPath);
            }
        }

        return new ArrayBlockingQueue<>(pathList.size(), false, pathList);
    }

    public static InputStream getFileStream(String transcriptFilePath) {
        FileInputStream fileStream = null;
        try {
            fileStream = new FileInputStream(transcriptFilePath);
        } catch (IOException e) {
            if (e instanceof java.io.FileNotFoundException)
                logger.warning("File not found: " + transcriptFilePath);
            else
                logger.severe("Error while reading file: " + transcriptFilePath);
            e.printStackTrace();
        }
        return fileStream;
    }

    public List<SpotFileKeywords> parseSpotFilesFromDir(String rootPath) {
        Queue<String> spotFilePaths = getFilesToParse(rootPath, SPOT_FILE_REGEX);
        List<SpotFileKeywords> spotFileKeywordsList = new ArrayList<>();
        SpotParser spotParser = new SpotParser();
        while (!spotFilePaths.isEmpty()) {
            String filePath = spotFilePaths.poll();
            spotFileKeywordsList.add(spotParser.getSpotFileKeywords(new File(filePath)));
        }
        return spotFileKeywordsList;
    }

    public int parseTranscriptFilesFromDir(String rootPath) {
        long start = System.nanoTime();
        Queue<String> transcriptFilePaths = getFilesToParse(rootPath, TRANSCRIPT_FILE_REGEX);
        long end = System.nanoTime();
        System.out.println("Time to get files: " + (end - start) / 1000000 + "ms");
        TranscriptParser transcriptParser = new TranscriptParser();
        int resultCount = 0;
        for (String path : transcriptFilePaths) {
            File toParseFile = new File(path);
            resultCount += transcriptParser.getLemmasCount(toParseFile);
        }
        System.out.println("Total number of results: " + resultCount);
        return resultCount;
    }

}
