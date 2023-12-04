package projetsi.models;

import java.util.concurrent.BlockingQueue;

import projetsi.interfaces.SpotFileKeywords;

public class PermutationsGeneratorProducer implements Runnable {

    private BlockingQueue<SpotFileKeywords> spotFileKeywordsQueue;
    private BlockingQueue<PermutationsGenerator> PermutationQueue;

    /**
     * Constructor with 2 parameters
     * 
     * @param spotFileKeywordsQueue
     * @param queue
     */
    public PermutationsGeneratorProducer(BlockingQueue<SpotFileKeywords> spotFileKeywordsQueue,
            BlockingQueue<PermutationsGenerator> queue) {
        this.spotFileKeywordsQueue = spotFileKeywordsQueue;
        this.PermutationQueue = queue;
    }

    @Override
    public void run() {

        produce();

        // try {
        // produce();
        // }
        // catch (InterruptedException e) {
        // Thread.currentThread().interrupt();
        // System.err.println("Thread interrupted: " + e.getMessage());
        // }
        // catch (InterruptedException e) {
        // Thread.currentThread().interrupt();
        // System.err.println("Thread interrupted: " + e.getMessage());
        // throw e;
        // }
    }

    private void produce() {
        PermutationsGenerator PermutationsGenerator = new PermutationsGenerator();
        while (!spotFileKeywordsQueue.isEmpty()) {
            SpotFileKeywords spotFileKeywords;
            try {
                spotFileKeywords = spotFileKeywordsQueue.take();
                PermutationsGenerator = new PermutationsGenerator(spotFileKeywords);
                /* Compute permutations - The method is filling the permutations queue */
                PermutationsGenerator.computePermutations(PermutationQueue);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }
    }

}
