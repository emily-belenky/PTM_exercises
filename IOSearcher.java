package test;

import java.io.File;
import java.util.Scanner;

public class IOSearcher {

    private static boolean stopFlag;

    public IOSearcher() {
        stopFlag = false;
    }

    public static boolean search(String word, String... fileNames) {
        boolean wordFound = false;
        try {
            for (int i = 0; !stopFlag && i < fileNames.length && !wordFound; i++) {
                try (Scanner scanner = new Scanner(new File(fileNames[i]))) {
                    while (scanner.hasNext() && !wordFound && !stopFlag) {
                        if (scanner.next().equals(word)) {
                            wordFound = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Handle exceptions, e.g., log them
        }

        return wordFound;
    }

    public void stop() {
        stopFlag = true;
    }
}
