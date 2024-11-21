package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CacheManager {

    private final HashSet<String> cache;
    private final int maxSize;
    private final CacheReplacementPolicy replacementPolicy;
    private static final IOSearcher sharedSearcher = new IOSearcher();

    public CacheManager(int size, CacheReplacementPolicy policy) {
        this.cache = new HashSet<>(size);
        this.maxSize = size;
        this.replacementPolicy = policy;
    }

    public boolean query(String item) {
        return cache.contains(item);
    }

    public void add(String item) {
        replacementPolicy.add(item);
        cache.add(item);
        if (cache.size() > maxSize) {
            cache.remove(replacementPolicy.remove());
        }
    }

    public static class ParIOSearcher {

        private final ExecutorService executorService;

        public ParIOSearcher() {
            this.executorService = Executors.newCachedThreadPool();
        }

        public boolean search(String word, String... fileNames) {
            ArrayList<IOSearcher> searchers = new ArrayList<>();
            ArrayList<Future<Boolean>> futures = new ArrayList<>();

            for (String fileName : fileNames) {
                IOSearcher searcher = new IOSearcher();
                searchers.add(searcher);

                Future<Boolean> future = executorService.submit(() -> {
                    boolean found = sharedSearcher.search(word, fileName);
                    if (found) {
                        searchers.forEach(IOSearcher::stop);
                    }
                    return found;
                });
                futures.add(future);
            }

            boolean found = false;
            for (Future<Boolean> future : futures) {
                try {
                    found |= future.get();
                } catch (InterruptedException | ExecutionException e) {
                    // Handle exceptions as needed
                }
            }

            return found;
        }

        public void stop() {
            executorService.shutdownNow();
        }

        @Override
        protected void finalize() throws Throwable {
            try {
                executorService.shutdown();
            } finally {
                super.finalize();
            }
        }
    }
}
