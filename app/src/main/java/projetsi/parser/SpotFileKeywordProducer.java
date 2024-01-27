package projetsi.parser;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import projetsi.interfaces.SpotFileKeywords;
import projetsi.models.SimpleSpotFileKeywords;

public class SpotFileKeywordProducer implements Runnable {

    private BlockingQueue<SpotFileKeywords> spotFileKeywordsQueue;
    private BlockingQueue<String> spotFilePathsQueue;

    public SpotFileKeywordProducer(BlockingQueue<SpotFileKeywords> spotFileKeywordsQueue,
            BlockingQueue<String> spotFilePathsQueue) {
        this.spotFileKeywordsQueue = spotFileKeywordsQueue;
        this.spotFilePathsQueue = spotFilePathsQueue;
    }

    @Override
    public void run() {
        produce();
    }

    private void produce() {
        SpotParser spotParser = new SpotParser();
        while (true) {
            String spotFilePath = spotFilePathsQueue.poll();
            if (spotFilePath == null) {
                break;
            }
            SpotFileKeywords spotFileKeywords = spotParser.getSpotFileKeywords(new File(spotFilePath));
            spotFileKeywordsQueue.add(spotFileKeywords);
            System.out.println("Produced a spot file keywords");
        }
        spotFileKeywordsQueue.add(new SimpleSpotFileKeywords(null, null));
    }

}
