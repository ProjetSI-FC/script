package projetsi.models;

import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import projetsi.interfaces.SpotFileKeywords;

public class PermutationsGeneratorProducer implements Runnable {

    private BlockingQueue<SpotFileKeywords> spotFileKeywordsQueue;
    private BlockingQueue<Pair<Pair<SortedSet<String>, Integer>, Map<String, String>>> permutationQueue;

    /**
     * Constructor with 2 parameters
     * 
     * @param spotFileKeywordsQueue
     * @param queue
     */
    public PermutationsGeneratorProducer(BlockingQueue<SpotFileKeywords> spotFileKeywordsQueue,
            BlockingQueue<Pair<Pair<SortedSet<String>, Integer>, Map<String, String>>> queue) {
        this.spotFileKeywordsQueue = spotFileKeywordsQueue;
        this.permutationQueue = queue;
    }

    @Override
    public void run() {

        produce();

    }

    private void produce() {
        while (true) {
            SpotFileKeywords spotFileKeywords;
            try {
                spotFileKeywords = spotFileKeywordsQueue.take();
                if (spotFileKeywords.getKeywordsList() == null) {
                    break;
                }
                PermutationsGenerator permutationsGenerator = new PermutationsGenerator(spotFileKeywords);
                /* Compute permutations - The method is filling the permutations queue */
                permutationsGenerator.computePermutations(permutationQueue);
                System.out.println("Produced a permutation");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Logger.getLogger(PermutationsGeneratorProducer.class.getName()).log(Level.SEVERE,
                        String.format("Thread interrupted: %s", e.getMessage()));
            }
        }
        permutationQueue.add(new Pair<>(null, null));
    }

}
