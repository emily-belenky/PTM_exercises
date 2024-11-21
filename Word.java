package test;

import java.util.Arrays;
import java.util.Objects;

public class Word {
    private Tile[] tiles;
    private int row;
    private int col;
    private boolean vertical;

    // Constructor
    public Word(Tile[] tiles, int row, int col, boolean vertical) {
        if ((row >= 0 && row < 14) && (col >= 0 && col < 14)) {
            this.tiles = tiles.clone();
            this.row = row;
            this.col = col;
            this.vertical = vertical;
        }
    }

    // Getter for tiles
    public Tile[] getTiles() {
        return tiles;
    }

    // Getter for row
    public int getRow() {
        return row;
    }

    // Getter for column
    public int getCol() {
        return col;
    }

    // Getter for vertical
    public boolean isVertical() {
        return vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return row == word.row && col == word.col && vertical == word.vertical
            && Arrays.equals(tiles, word.tiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(tiles), row, col, vertical);
    }
}
