package test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Board {
    boolean isStar = false;
    public ArrayList < Word > existWordsList = new ArrayList < Word > ();
    public static Board TheBoard = null;
    Tile[][] matBoard = new Tile[15][15];

    public Board() {}

    static HashMap < String, String > BonousScore = new HashMap < String, String > ();
    static {
        BonousScore.put("7,7", "star");
        BonousScore.put("0,3", "DoubleLettScor");
        BonousScore.put("0,11", "DoubleLettScor");
        BonousScore.put("2,6", "DoubleLettScor");
        BonousScore.put("2,8", "DoubleLettScor");
        BonousScore.put("3,0", "DoubleLettScor");
        BonousScore.put("3,7", "DoubleLettScor");
        BonousScore.put("3,14", "DoubleLettScor");
        BonousScore.put("6,2", "DoubleLettScor");
        BonousScore.put("6,6", "DoubleLettScor");
        BonousScore.put("6,8", "DoubleLettScor");
        BonousScore.put("6,12", "DoubleLettScor");
        BonousScore.put("7,3", "DoubleLettScor");
        BonousScore.put("7,11", "DoubleLettScor");
        BonousScore.put("8,2", "DoubleLettScor");
        BonousScore.put("8,6", "DoubleLettScor");
        BonousScore.put("8,8", "DoubleLettScor");
        BonousScore.put("8,12", "DoubleLettScor");
        BonousScore.put("11,0", "DoubleLettScor");
        BonousScore.put("11,7", "DoubleLettScor");
        BonousScore.put("11,14", "DoubleLettScor");
        BonousScore.put("12,6", "DoubleLettScor");
        BonousScore.put("12,8", "DoubleLettScor");
        BonousScore.put("14,3", "DoubleLettScor");
        BonousScore.put("14,11", "DoubleLettScor");
        BonousScore.put("1,5", "TripelLettScor");
        BonousScore.put("1,9", "TripelLettScor");
        BonousScore.put("5,1", "TripelLettScor");
        BonousScore.put("5,5", "TripelLettScor");
        BonousScore.put("5,9", "TripelLettScor");
        BonousScore.put("5,13", "TripelLettScor");
        BonousScore.put("9,1", "TripelLettScor");
        BonousScore.put("9,5", "TripelLettScor");
        BonousScore.put("9,9", "TripelLettScor");
        BonousScore.put("9,13", "TripelLettScor");
        BonousScore.put("13,5", "TripelLettScor");
        BonousScore.put("13,9", "TripelLettScor");
        BonousScore.put("1,1", "DoubleWorScore");
        BonousScore.put("2,2", "DoubleWorScore");
        BonousScore.put("3,3", "DoubleWorScore");
        BonousScore.put("4,4", "DoubleWorScore");
        BonousScore.put("10,4", "DoubleWorScore");
        BonousScore.put("3,11", "DoubleWorScore");
        BonousScore.put("2,12", "DoubleWorScore");
        BonousScore.put("1,13", "DoubleWorScore");
        BonousScore.put("10,10", "DoubleWorScore");
        BonousScore.put("11,11", "DoubleWorScore");
        BonousScore.put("12,12", "DoubleWorScore");
        BonousScore.put("13,13", "DoubleWorScore");
        BonousScore.put("4,10", "DoubleWorScore");
        BonousScore.put("11,3", "DoubleWorScore");
        BonousScore.put("12,2", "DoubleWorScore");
        BonousScore.put("13,1", "DoubleWorScore");
        BonousScore.put("0,0", "TripelWorScor");
        BonousScore.put("0,7", "TripelWorScor");
        BonousScore.put("0,14", "TripelWorScor");
        BonousScore.put("7,0", "TripelWorScor");
        BonousScore.put("7,14", "TripelWorScor");
        BonousScore.put("14,0", "TripelWorScor");
        BonousScore.put("14,7", "TripelWorScor");
        BonousScore.put("14,14", "TripelWorScor");
    }

    public static Board getBoard() {
        if (TheBoard == null)
            TheBoard = new Board();
        return TheBoard;
    }

    public Tile[][] getTiles() {
        Tile[][] cpBoard = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (TheBoard.matBoard[i][j] == null)
                    cpBoard[i][j] = null;
                else
                    cpBoard[i][j] = TheBoard.matBoard[i][j];
            }
        }
        return cpBoard;
    }

    public boolean WordPlaceLeagal(Word a) {
        if (a.isVertical()) {
            for (int i = 0; i < a.getTiles().length; i++) {
                if (TheBoard.matBoard[a.getRow() + i][a.getCol()] == null)
                    continue;
                else if (a.getTiles()[i] != null) {
                    return false;
                }
            }
        } else {
            for (int j = 0; j < a.getTiles().length; j++) {
                if (TheBoard.matBoard[a.getRow()][a.getCol() + j] != a.getTiles()[j] && TheBoard.matBoard[a.getRow()][a.getCol() + j] != null) {
                    if (TheBoard.matBoard[a.getRow()][a.getCol() + j] == null)
                        continue;
                    else if (a.getTiles()[j] != null)
                        return false;
                }
            }
        }
        return true;
    }

    public boolean isWithin(Word a) {
        if (a.getCol() >= 0 && a.getCol() <= 14 && a.getRow() >= 0 && a.getRow() <= 14) {
            if (a.isVertical()) {
                return !(a.getRow() + a.getTiles().length > 14);
            } else {
                return 14 >= a.getCol() + a.getTiles().length;
            }
        }
        return false;
    }

    public boolean isFirst(Word a) {
        Tile Middle = TheBoard.matBoard[7][7];
        if (Middle == null) {
            if (a.isVertical()) {
                if (a.getCol() == 7) {
                    for (int i = a.getRow(); i < a.getRow() + a.getTiles().length; i++) {
                        if (i == 7)
                            return true;
                    }
                } else return false;
            } else {
                if (a.getRow() == 7) {
                    for (int i = a.getCol(); i < a.getCol() + a.getTiles().length; i++) {
                        if (i == 7)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkSmushed(Word a) {
        int len = a.getTiles().length;
        if (a.isVertical()) {
            for (int i = 0; i < len; i++) {
                if (a.getRow() + i + 1 <= 14 && TheBoard.matBoard[a.getRow() + i + 1][a.getCol()] != null)
                    return true;
                else if (a.getCol() + 1 <= 14 && TheBoard.matBoard[a.getRow() + i][a.getCol() + 1] != null)
                    return true;
                else if (a.getCol() - 1 >= 0 && TheBoard.matBoard[a.getRow() + i][a.getCol() - 1] != null)
                    return true;
                else if (a.getRow() + i - 1 >= 0 && TheBoard.matBoard[a.getRow() + i - 1][a.getCol()] != null)
                    return true;
            }
        } else if (!a.isVertical()) {
            for (int j = 0; j < len; j++) {
                if (a.getCol() + 1 <= 14 && TheBoard.matBoard[a.getRow()][a.getCol() + 1 + j] != null)
                    return true;
                else if (a.getRow() + 1 <= 14 && TheBoard.matBoard[a.getRow() + 1][a.getCol() + j] != null)
                    return true;
                else if (a.getRow() + j - 1 >= 0 && TheBoard.matBoard[a.getRow() - 1][a.getCol() + j] != null)
                    return true;
                else if (a.getCol() - 1 >= 0 && TheBoard.matBoard[a.getRow()][a.getCol() + j - 1] != null)
                    return true;
            }
        }
        return false;
    }

    public boolean boardLegal(Word a) {
        if (a.getTiles() != null) {
            if (isFirst(a)) {
                return isWithin(a) && WordPlaceLeagal(a);
            }
            return isWithin(a) && WordPlaceLeagal(a) && checkSmushed(a);
        }
        return false;
    }

    public boolean dictionaryLegal(Word word) {
        return true;
    }

    public boolean isValidSingalWord(Tile[] tile) {
        int amount = 0;
        for (int i = 0; i < tile.length; i++)
            if (tile[i] != null)
                amount++;
        return amount >= 2;
    }

    public boolean isSingleWordLegal(Word newWord) {
        if (isValidSingalWord(newWord.getTiles()) == false)
            return false;
        if (dictionaryLegal(newWord)) {
            for (int i = 0; i < existWordsList.size(); i++) {
                if (newWord.equals(existWordsList.get(i))) {
                    return false;
                }
            }
        }
        existWordsList.add(newWord);
        return true;
    }

    public Word getVerticalWordWhenAddHorizontalWord(int column, int row) {
        int endPoint = row;
        int startingPoint = row;
        Word newWord = null;
        Tile[] tileVector = new Tile[15];
        while ((endPoint < 15 - 1) && TheBoard.matBoard[endPoint + 1][column] != null) {
            endPoint++;
        }
        while ((startingPoint >= 0 + 1) && TheBoard.matBoard[startingPoint - 1][column] != null) {
            startingPoint--;
        }
        for (int i = 0; i <= endPoint - startingPoint; i++) {
            tileVector[i] = (TheBoard.matBoard[startingPoint + i][column]);
        }
        int checkrealWord = 0;
        while (tileVector[checkrealWord] != null)
            checkrealWord++;
        Tile[] realWord = new Tile[checkrealWord];
        for (int i = 0; i < checkrealWord; i++) {
            realWord[i] = tileVector[i];
        }
        newWord = new Word(realWord, startingPoint, column, true);
        if (isSingleWordLegal(newWord))
            return newWord;
        else
            return null;
    }

    public Word getHorizontalWordWhenAddVerticalWord(int column, int row) {
        int startColumn = column, endColumn = column;
        Word newWord = null;
        Tile[] tileVector = new Tile[15];
        while ((endColumn < 15 - 1) && TheBoard.matBoard[row][endColumn + 1] != null) {
            endColumn++;
        }

        while ((startColumn >= 0 + 1) && TheBoard.matBoard[row][startColumn - 1] != null) {
            startColumn--;
        }
        for (int i = 0; i <= endColumn - startColumn; i++) {
            tileVector[i] = (TheBoard.matBoard[row][startColumn + i]);
        }
        int checkrealWord = 0;
        while (tileVector[checkrealWord] != null)
            checkrealWord++;
        Tile[] realWord = new Tile[checkrealWord];
        for (int i = 0; i < checkrealWord; i++) {
            realWord[i] = tileVector[i];
        }
        newWord = new Word(realWord, row, startColumn, false);
        if (isSingleWordLegal(newWord))
            return newWord;
        else
            return null;
    }

    public ArrayList < Word > getWords(Word a) {
        ArrayList < Word > wordsAdded = new ArrayList < Word > ();
        int columnNumber = a.getCol();
        int rowNumber = a.getRow();
        if (a.isVertical() != true) {
            // get length for each column
            for (int i = 0; i < a.getTiles().length; i++) {
                Word checkedWord = getVerticalWordWhenAddHorizontalWord(i + columnNumber, rowNumber);
                if (checkedWord != null) {
                    wordsAdded.add(checkedWord);
                }
            }
            Word checkedWord = getHorizontalWordWhenAddVerticalWord(a.getCol(), a.getRow());
            if (checkedWord != null) {
                wordsAdded.add(checkedWord);
            }

        } else // is vertical
        {
            for (int i = 0; i < a.getTiles().length; i++) {
                Word checkedWord = getHorizontalWordWhenAddVerticalWord(columnNumber, i + rowNumber);
                if (checkedWord != null) {
                    wordsAdded.add(checkedWord);
                }
            }
            Word checkedWord = getVerticalWordWhenAddHorizontalWord(a.getCol(), a.getRow());
            if (checkedWord != null) {
                wordsAdded.add(checkedWord);
            }
        }
        return wordsAdded;
    }

    public int getScore(Word word) {
        int amount = 0;
        if (word.isVertical() == true) {
            int j = word.getCol();
            int flag = 1;
            for (int i = 0; i < word.getTiles().length; i++) {
                String str1 = Integer.toString(i + word.getRow());
                String str2 = Integer.toString(j);
                String str3 = str1 + ',' + str2;
                if (BonousScore.containsKey(str3)); {
                    String Bonous = new String();
                    Bonous = BonousScore.get(str3);
                    if (Bonous == null) {
                        amount += TheBoard.matBoard[word.getRow() + i][word.getCol()].score;
                        continue;
                    }
                    switch (Bonous) {
                        case "star":
                            {
                                if (isStar == false) {
                                    isStar = true;
                                    flag *= 2;
                                }
                                amount += TheBoard.matBoard[word.getRow() + i][word.getCol()].score;
                            }
                            break;
                        case "DoubleLettScor":
                            {
                                amount += 2 * TheBoard.matBoard[word.getRow() + i][word.getCol()].score;
                            }
                            break;
                        case "TripelLettScor":
                            {
                                amount += 3 * TheBoard.matBoard[word.getRow() + i][word.getCol()].score;
                            }
                            break;
                        case "DoubleWorScore":
                            {
                                flag *= 2;
                                amount += TheBoard.matBoard[word.getRow() + i][word.getCol()].score;
                            }
                            break;
                        case "TripelWorScor":
                            {
                                flag *= 3;
                                amount += TheBoard.matBoard[word.getRow() + i][word.getCol()].score;
                            }
                            break;
                    }
                }
            }
            if (flag != 1) {
                amount *= flag;
            }
            return amount;
        } else // not vertical
        {
            int i = word.getRow();
            int flag = 1;
            for (int j = 0; j < word.getTiles().length; j++) {
                String str1 = Integer.toString(i);
                String str2 = Integer.toString(word.getCol() + j);
                String str3 = str1 + ',' + str2;
                if (BonousScore.containsKey(str3)); {
                    String Bonous = new String();
                    Bonous = BonousScore.get(str3);
                    if (Bonous == null) {
                        amount += TheBoard.matBoard[word.getRow()][word.getCol() + j].score;
                        continue;
                    }
                    switch (Bonous) {
                        case "star":
                            {
                                if (isStar == false) {
                                    isStar = true;
                                    flag *= 2;
                                }
                                amount += TheBoard.matBoard[word.getRow()][word.getCol() + j].score;
                            }
                            break;
                        case "DoubleLettScor":
                            {
                                amount += 2 * TheBoard.matBoard[word.getRow()][word.getCol() + j].score;
                            }
                            break;
                        case "TripelLettScor":
                            {
                                amount += 3 * TheBoard.matBoard[word.getRow()][word.getCol() + j].score;
                            }
                            break;
                        case "DoubleWorScore":
                            {
                                flag *= 2;
                                amount += TheBoard.matBoard[word.getRow()][word.getCol() + j].score;
                            }
                            break;
                        case "TripelWorScor":
                            {
                                flag *= 3;
                                amount += TheBoard.matBoard[word.getRow()][word.getCol() + j].score;
                            }
                            break;
                    }

                }
            }
            if (flag != 1) {
                amount *= flag;
            }
            return amount;
        }
    }

    public int tryPlaceWord(Word word) {
        int amount = 0;
        if (dictionaryLegal(word) == true) {
            if (boardLegal(word) == true) {
                if (word.isVertical() == true) {
                    for (int i = 0; i < word.getTiles().length; i++) // initialize on bord
                    {
                        if (word.getTiles()[i] != null)
                            TheBoard.matBoard[word.getRow() + i][word.getCol()] = word.getTiles()[i];
                    }
                } else {
                    for (int i = 0; i < word.getTiles().length; i++) {
                        if (word.getTiles()[i] != null)
                            TheBoard.matBoard[word.getRow()][word.getCol() + i] = word.getTiles()[i];
                    }
                }
                ArrayList < Word > ArrayTemp = new ArrayList < Word > ();
                ArrayTemp = getWords(word);
                for (int i = 0; i < ArrayTemp.size(); i++) {
                    if (dictionaryLegal(ArrayTemp.get(i)) == true) {
                        amount += getScore(ArrayTemp.get(i));
                    } else amount *= 0;
                }
            }
        }
        return amount;
    }
}