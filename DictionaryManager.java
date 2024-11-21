package test;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {

    private final Map<String, Dictionary> dictionaryMap;
    private static DictionaryManager instance;

    private DictionaryManager() {
        this.dictionaryMap = new HashMap<>();
    }

    public static DictionaryManager get() {
        if (instance == null) {
            instance = new DictionaryManager();
        }
        return instance;
    }

    public boolean query(String... arguments) {
        boolean result = false;
        String wordToFind = arguments[arguments.length - 1];
        for (int i = 0; i < arguments.length - 1; i++) {
            String fileName = arguments[i];
            if (!dictionaryMap.containsKey(fileName)) {
                Dictionary dictionary = new Dictionary(fileName);
                dictionaryMap.put(fileName, dictionary);
            }
        }
        for (Dictionary dictionary : dictionaryMap.values()) {
            if (dictionary.query(wordToFind)) {
                result = true;
                break; // Exit the loop as soon as a match is found
            }
        }
        return result;
    }

    public boolean challenge(String... arguments) {
        boolean result = false;
        String wordToChallenge = arguments[arguments.length - 1];
        for (int i = 0; i < arguments.length - 1; i++) {
            String fileName = arguments[i];
            if (!dictionaryMap.containsKey(fileName)) {
                Dictionary dictionary = new Dictionary(fileName);
                dictionaryMap.put(fileName, dictionary);
            }
        }
        for (Dictionary dictionary : dictionaryMap.values()) {
            if (dictionary.challenge(wordToChallenge)) {
                result = true;
                break; // Exit the loop as soon as a match is found
            }
        }
        return result;
    }

    public int getSize() {
        return dictionaryMap.size();
    }
}
