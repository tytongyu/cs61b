import java.util.ArrayList;
import java.util.List;

public class Boggle {
    
    // File path of dictionary dicFile
    static String dictPath = "trivial_words.txt";

    /**
     * Solves a Boggle puzzle.
     * 
     * //@param k The maximum number of words to return.
     * //@param boardFilePath The dicFile path to Boggle board dicFile.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */

    public static void main(String[] args) {
        List<String> a = solve(20, "exampleBoard2.txt");
        for (String x : a) {
            System.out.println(x);
        }
    }

    public static List<String> solve(int k, String boardFilePath) {
        Tria dictionary = new Tria();
        In dicFile = new In(dictPath);
        int maxWordLength = 1;
        if (dicFile.exists()) {
            while (dicFile.hasNextLine()) {
                String curWord = dicFile.readLine();
                if (curWord.length() > maxWordLength) {
                    maxWordLength = curWord.length();
                }
                dictionary.addWord(curWord);
            }
            dicFile.close();
        } else {
            throw new IllegalArgumentException();
        }

        In boardFile = new In(boardFilePath);
        List<String> boardStr = new ArrayList<>();
        if (boardFile.exists()) {
            while (boardFile.hasNextLine()) {
                String curWord = boardFile.readLine();
                boardStr.add(curWord);
            }
            boardFile.close();
        } else {
            throw new IllegalArgumentException();
        }

        int a = boardStr.get(0).length();
        for (String x : boardStr) {
            if (x.length() != a) {
                throw new IllegalArgumentException();
            }
        }
        char[][] boardChar = new char[boardStr.size()][boardStr.get(0).length()];
        for (int i = 0; i < boardStr.size(); i++) {
            for (int j = 0; j < boardStr.get(0).length(); j++) {
                boardChar[i][j] = boardStr.get(i).charAt(j);
            }
        }
        List<String> allArrays = new ArrayList<>();
        for (int i = 0; i < boardChar.length; i++) {
            for (int j = 0; j < boardChar[0].length; j++) {
                int[][] isOccpied = new int[boardStr.size()][boardStr.get(0).length()];
                String start = String.valueOf(boardChar[i][j]);
                allArrays.add(start);
                List<String> newMem = getAdjacent(start, maxWordLength, boardChar, isOccpied, i, j);
                allArrays.addAll(newMem);
            }
        }
        List<String> allWords = new ArrayList<>();
        for (String x : allArrays) {
            if (dictionary.isWord(x) && !allWords.contains(x)) {
                allWords.add(x);
            }
        }

        //sort
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 7; i ++) {
            int maxNum = 0;
            for (int j = 1; j < allWords.size(); j ++) {
                if (aIsBig (allWords.get(j), allWords.get(maxNum))) {
                    maxNum = j;
                }
            }
            res.add(allWords.get(maxNum));
            allWords.remove(maxNum);
            if (allWords.isEmpty()) {
                break;
            }
        }
        return res;
    }

    // get list of adjacent words(k characters) in char[][] arrays at(i,j)
    private static List<String> getAdjacent (String pre, int k, char[][] arrays, int[][] isOccpied, int i, int j) {
        int [][] CurIsOccpied = new int[isOccpied.length][isOccpied[0].length];
        for (int x = 0; x < isOccpied.length; x++) {
            for (int y = 0; y < isOccpied[0].length; y++) {
                CurIsOccpied[x][y] = isOccpied[x][y];
            }
        }
        CurIsOccpied[i][j] = 1;
        List<int[]> curAdjacent = adjacent (CurIsOccpied,arrays.length - 1, arrays[0].length - 1, i, j);
        List<String> res = new ArrayList<>();
        if (curAdjacent.isEmpty()) {
            return res;
        }

        for (int[] x : curAdjacent) {
            String cur = pre + arrays[x[0]][x[1]];
            res.add(cur);
            List<String> newMem = getAdjacent(cur, k - 1, arrays, CurIsOccpied, x[0], x[1]);
            res.addAll(newMem);
        }
        return res;
    }

    private static boolean aIsBig (String a, String b) {
        if (a.length() < b.length()) {
            return false;
        }
        if (a.length() == b.length()) {
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i) - b.charAt(i) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<int[]> adjacent (int[][] isOccpied, int x, int y, int i, int j) {
        List<int[]> res = new ArrayList<>();
        if (i - 1 >= 0 && isOccpied[i - 1][j] != 1) {
            int[] left = {i - 1, j};
            res.add(left);
        }
        if (j - 1 >= 0 && isOccpied[i][j - 1] != 1) {
            int[] up = {i, j - 1};
            res.add(up);
        }
        if (i + 1 <= x && isOccpied[i + 1][j] != 1) {
            int[] right = {i + 1, j};
            res.add(right);
        }
        if (j + 1 <= y && isOccpied[i][j + 1] != 1) {
            int[] down = {i, j + 1};
            res.add(down);
        }
        if (i - 1 >= 0 && j - 1 >= 0 && isOccpied[i - 1][j - 1] != 1) {
            int[] leftup = {i - 1, j - 1};
            res.add(leftup);
        }
        if (i + 1 <= x && j - 1 >= 0 && isOccpied[i + 1][j - 1] != 1) {
            int[] rightup = {i + 1, j - 1};
            res.add(rightup);
        }
        if (i - 1 >= 0 && j + 1 <= y && isOccpied[i - 1][j + 1] != 1) {
            int[] leftdown = {i - 1, j + 1};
            res.add(leftdown);
        }
        if (i + 1 <= x && j + 1 <= y && isOccpied[i + 1][j + 1] != 1) {
            int[] rightdown = {i + 1, j + 1};
            res.add(rightdown);
        }
        return res;
    }

}
