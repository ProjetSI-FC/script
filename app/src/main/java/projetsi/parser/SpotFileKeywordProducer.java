package projetsi.parser;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import projetsi.interfaces.SpotFileKeywords;

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
        try {
            produce();
            System.out.println("Produced a spot file keywords");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.getLogger(SpotFileKeywordProducer.class.getName()).log(Level.SEVERE,
                    String.format("Thread interrupted: %s", e.getMessage()));
        }
    }

    private void produce() throws InterruptedException {
        SpotParser spotParser = new SpotParser();
        while (!spotFilePathsQueue.isEmpty()) {
            String spotFilePath;
            try {
                spotFilePath = spotFilePathsQueue.take();
                SpotFileKeywords spotFileKeywords = spotParser.getSpotFileKeywords(new File(spotFilePath));
                spotFileKeywordsQueue.put(spotFileKeywords);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }
    }

}
