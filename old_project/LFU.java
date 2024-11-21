package test;

import java.util.HashMap;
import java.util.PriorityQueue;

public class LFU implements CacheReplacementPolicy {

    private final HashMap<String, Integer> frequencyMap = new HashMap<>();
    private final PriorityQueue<String> frequencyQueue = new PriorityQueue<>((word1, word2) -> 
        Integer.compare(frequencyMap.get(word1), frequencyMap.get(word2))
    );

    @Override
    public void add(String word) {
        if (frequencyMap.containsKey(word)) {
            int currentCount = frequencyMap.get(word);
            frequencyMap.put(word, currentCount + 1);
            frequencyQueue.remove(word);
            frequencyQueue.add(word);
        } else {
            frequencyMap.put(word, 1);
            frequencyQueue.add(word);
        }
    }

    @Override
    public String remove() {
        String leastFrequentWord = frequencyQueue.poll();
        frequencyMap.remove(leastFrequentWord);
        return leastFrequentWord;
    }
}