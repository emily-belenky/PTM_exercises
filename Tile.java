package test;

import java.util.Objects;
import java.util.Random;

public class Tile {
    public final char letter;
    public final int score;

    // Constructor
    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    public static class Bag {
        private static final int ALPHABET_SIZE = 26;
        private int[] quantities;
        private Tile[] tiles;
        private static Bag instance;

        // Private constructor for Singleton pattern
        private Bag() {
            quantities = new int[ALPHABET_SIZE];
            tiles = new Tile[ALPHABET_SIZE];
            initializeTiles();
            initializeQuantities();
        }

        // Initialize the Tile array with predefined tiles
        private void initializeTiles() {
            tiles[0] = new Tile('A', 1);
            tiles[1] = new Tile('B', 3);
            tiles[2] = new Tile('C', 3);
            tiles[3] = new Tile('D', 2);
            tiles[4] = new Tile('E', 1);
            tiles[5] = new Tile('F', 4);
            tiles[6] = new Tile('G', 2);
            tiles[7] = new Tile('H', 4);
            tiles[8] = new Tile('I', 1);
            tiles[9] = new Tile('J', 8);
            tiles[10] = new Tile('K', 5);
            tiles[11] = new Tile('L', 1);
            tiles[12] = new Tile('M', 3);
            tiles[13] = new Tile('N', 1);
            tiles[14] = new Tile('O', 1);
            tiles[15] = new Tile('P', 3);
            tiles[16] = new Tile('Q', 10);
            tiles[17] = new Tile('R', 1);
            tiles[18] = new Tile('S', 1);
            tiles[19] = new Tile('T', 1);
            tiles[20] = new Tile('U', 1);
            tiles[21] = new Tile('V', 4);
            tiles[22] = new Tile('W', 4);
            tiles[23] = new Tile('X', 8);
            tiles[24] = new Tile('Y', 4);
            tiles[25] = new Tile('Z', 10);
        }

        // Initialize the quantities array
        private void initializeQuantities() {
            int[] initialQuantities = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            System.arraycopy(initialQuantities, 0, quantities, 0, quantities.length);
        }

        // Get a random tile from the bag
        public Tile getRand() {
            Random random = new Random();
            int index;
            do {
                index = random.nextInt(ALPHABET_SIZE);
            } while (quantities[index] == 0);
            
            quantities[index]--;
            return tiles[index];
        }

        // Get a specific tile based on a given character
        public Tile getTile(char letter) {
            int index = charToIndex(letter);
            if (index >= 0 && index < ALPHABET_SIZE && quantities[index] > 0) {
                quantities[index]--;
                return tiles[index];
            }
            return null;
        }

        // Return a tile to the bag
        public void put(Tile tile){ 
            if (size(quantities)<98){
                quantities[charToIndex(tile.letter)]++;
            }
        }
        
        public int size(int[] arr){ // counts the number of total tiles
            int size = 0;
            for (int num : arr) {
                size += num;
            }
            return size;
        }

        // Return a copy of the quantities array
        public int[] getQuantities() {
            return quantities.clone();
        }

        // Convert a character to its index in the quantities and tiles arrays
        private int charToIndex(char c) {
            return c - 'A';
        }

        // Provide the singleton instance of Bag
        public static Bag getBag() {
            if (instance == null) {
                instance = new Bag();
            }
            return instance;
        }
    }
}