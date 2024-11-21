package test;

import java.util.LinkedList;
import java.util.List;

public class LRU implements CacheReplacementPolicy {

    private final List<String> cacheList = new LinkedList<>();

    @Override
    public void add(String word) {
        cacheList.remove(word);
        cacheList.add(word);
    }

    @Override
    public String remove() {
        return cacheList.remove(0);
    }
}