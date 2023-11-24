package projetsi.models;

import java.util.concurrent.BlockingQueue;

import projetsi.interfaces.SpotFileKeywords;

public class SpotFileKeywordsPermutationsProducer implements Runnable {

    private BlockingQueue<SpotFileKeywords> spotFileKeywordsQueue;
    private BlockingQueue<SpotFileKeywordsPermutations> spotFileKeywordsPermutationsQueue;

    /**
     * Constructor with 2 parameters
     * 
     * @param spotFileKeywordsQueue
     * @param queue
     */
    public SpotFileKeywordsPermutationsProducer(BlockingQueue<SpotFileKeywords> spotFileKeywordsQueue,
            BlockingQueue<SpotFileKeywordsPermutations> queue) {
        this.spotFileKeywordsQueue = spotFileKeywordsQueue;
        this.spotFileKeywordsPermutationsQueue = queue;
        }

    @Override
    public void run() {

        produce();

        // try {
        //     produce();
        // }
        // catch (InterruptedException e) {
        //     Thread.currentThread().interrupt();
        //     System.err.println("Thread interrupted: " + e.getMessage()); 
        // }
        // catch (InterruptedException e) {
        //     Thread.currentThread().interrupt();
        //     System.err.println("Thread interrupted: " + e.getMessage()); 
        //     throw e;
        // }
    }

    private void produce() {
        SpotFileKeywordsPermutations spotFileKeywordsPermutations = new SpotFileKeywordsPermutations();
        while(!spotFileKeywordsQueue.isEmpty()) {
            SpotFileKeywords spotFileKeywords;
            try {
                spotFileKeywords = spotFileKeywordsQueue.take();
                spotFileKeywordsPermutations = new SpotFileKeywordsPermutations(spotFileKeywords);
                spotFileKeywordsPermutations.computePermutations();
                spotFileKeywordsPermutations.calculateAllScores();
                spotFileKeywordsPermutationsQueue.put(spotFileKeywordsPermutations);
                spotFileKeywordsPermutationsQueue.put(spotFileKeywordsPermutations);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage()); 
            }
        }
    }

    
}
