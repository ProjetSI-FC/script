/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package projetsi;

import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import projetsi.export.Export;
import projetsi.interfaces.KeywordSearchMap;
import projetsi.interfaces.SpotFileKeywords;
import projetsi.keywordsetvalidator.KeywordSearchMapController;
import projetsi.models.Pair;
import projetsi.models.PermutationsGeneratorProducer;
import projetsi.parser.ParserController;
import projetsi.parser.SpotFileKeywordProducer;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws InterruptedException {
        ParserController parserController = new ParserController();
        BlockingQueue<String> keywords = parserController
                .getFilesToParse(
                        "C:\\Users\\valen\\Documents\\01_Polytech\\5A\\S9\\ProjetSI\\index\\part1\\0a83db5c5d885ef7dbddeb5bc6ef09e640a26b1e",
                        ParserController.SPOT_FILE_REGEX);
        int numCores = Runtime.getRuntime().availableProcessors();
        BlockingQueue<SpotFileKeywords> spotFileKeywordsQueue = new ArrayBlockingQueue<>(
                keywords.size() + numCores);

        BlockingQueue<Pair<Pair<SortedSet<String>, Integer>, Map<String, String>>> permutationQueue = new LinkedBlockingQueue<>();

        System.out.println("Number of cores: " + numCores);
        System.out.println("number of keywords: " + keywords.size());
        System.out.println(Runtime.getRuntime().maxMemory());

        Thread[] threads = new Thread[numCores];
        for (int i = 0; i < numCores; i++) {
            threads[i] = new Thread(new SpotFileKeywordProducer(spotFileKeywordsQueue, keywords));
            threads[i].start();
        }
        // Wait for spotFileKeywords
        Thread[] threads2 = new Thread[numCores];
        for (int i = 0; i < numCores; i++) {
            threads2[i] = new Thread(new PermutationsGeneratorProducer(spotFileKeywordsQueue, permutationQueue));
            threads2[i].start();
        }

        // Sleep for 5 seconds
        KeywordSearchMapController keywordSearchMapController = new KeywordSearchMapController();
        KeywordSearchMap keywordSearchMap = keywordSearchMapController
                .createSearchMapFromKeywordsPermutations(permutationQueue, 0, numCores);

        System.out.println("Ended permutations");

        for (int i = 0; i < numCores; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("SpotFileKeywords queue size: " + spotFileKeywordsQueue.size());
        // Wait for permutations
        for (int i = 0; i < numCores; i++) {
            try {
                threads2[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Permutations queue size: " + permutationQueue.size());

        System.out.println("Number of keywords: " + keywordSearchMap.toString());

        System.out.println("Exporting to json...");
        Export.exportToJson(keywordSearchMap);
        System.out.println("Done.");

    }
}
