package test;

import java.io.File;
import java.util.Scanner;

public class Dictionary {

    private final CacheManager cacheExists;
    private final CacheManager cacheNotExists;
    private final BloomFilter bloomFilter;
    private final String[] fileNames;
    private static IOSearcher sharedSearcher;

    public Dictionary(String... fileNames) {
        this.fileNames = fileNames;
        this.cacheExists = new CacheManager(400, new LRU());
        this.cacheNotExists = new CacheManager(100, new LFU());
        this.bloomFilter = new BloomFilter(256, "MD5", "SHA1");

        initializeBloomFilter();
        sharedSearcher = new IOSearcher();
    }

    private void initializeBloomFilter() {
        for (String fileName : fileNames) {
            try (Scanner scanner = new Scanner(new File(fileName))) {
                while (scanner.hasNext()) {
                    bloomFilter.add(scanner.next());
                }
            } catch (Exception e) {
                // Handle any exceptions that may occur, e.g., log them
            }
        }
    }

    public boolean query(String word) {
        if (cacheExists.query(word)) {
            return true;
        }
        if (cacheNotExists.query(word)) {
            return false;
        }

        boolean existsInBloomFilter = bloomFilter.contains(word);
        if (existsInBloomFilter) {
            cacheExists.add(word);
        } else {
            cacheNotExists.add(word);
        }

        return existsInBloomFilter;
    }

    public boolean challenge(String word) {
        boolean existsInFile = sharedSearcher.search(word, fileNames);
        if (existsInFile) {
            cacheExists.add(word);
        } else {
            cacheNotExists.add(word);
        }

        return existsInFile;
    }

    public void close() {
        sharedSearcher.stop();
    }
}
