package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {

    private final String[] hashAlgorithms;
    private final BitSet bitSet;
    private final int bitSetSize;

    public BloomFilter(int size, String... hashAlgorithms) {
        this.hashAlgorithms = hashAlgorithms;
        this.bitSetSize = size;
        this.bitSet = new BitSet(size);
    }

    public void add(String item) {
        for (String algorithm : hashAlgorithms) { // Loop through each hash function
            try {
                MessageDigest digest = MessageDigest.getInstance(algorithm); // Get message digest for algorithm
                byte[] hashBytes = digest.digest(item.getBytes()); // Hash the item
                BigInteger hashValue = new BigInteger(hashBytes); // Convert hash to integer index
                int index = Math.abs(hashValue.intValue()) % bitSetSize;
                bitSet.set(index); // Set bit at index in BitSet
            } catch (NoSuchAlgorithmException e) {
                // Ignore the exception
            }
        }
    }

    public boolean contains(String item) {
        for (String algorithm : hashAlgorithms) { // Loop through each hash function 
            try {
                MessageDigest digest = MessageDigest.getInstance(algorithm); // Get message digest 
                byte[] hashBytes = digest.digest(item.getBytes()); // Hash the item
                BigInteger hashValue = new BigInteger(hashBytes); // Convert hash to integer index
                int index = Math.abs(hashValue.intValue()) % bitSetSize;
                if (!bitSet.get(index)) { // Return false if bit not set
                    return false;
                }
            } catch (NoSuchAlgorithmException e) {
                // Ignore the exception
            }
        }

        return true; // All bits were set, so return true
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(); // Build string from bits
        for (int i = 0; i < bitSet.length(); i++) {
            builder.append(bitSet.get(i) ? "1" : "0");
        }
        return builder.toString();
    }
}
