package hw4.puzzle;

import java.util.ArrayDeque;
import java.util.Queue;

public class Board implements WorldState {
    private int N;
    private int[][] grids;

    public Board(int[][] tiles) {
        N = tiles.length;
        grids = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grids[i][j] = tiles[i][j];
            }
        }
    }

    private int[] goal() {
        int[] goal = new int[N * N];
        for (int i = 0; i < N * N - 1; i++) {
            goal[i] = i + 1;
        }
        goal[N * N - 1] = 0;
        return goal;
    }

    public int tileAt(int i, int j) {
        if (i > -1 && i < N && j > -1 && j < N) {
            return grids[i][j];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public int size() {
        return N;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new ArrayDeque<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.add(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int hammingDis = 0;
        int[] goalGrid = goal();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grids[i][j] == 0) {
                    continue;
                } else {
                    if (grids[i][j] != goalGrid[N * i + j]) {
                        hammingDis++;
                    }
                }
            }
        }
        return hammingDis;
    }

    public int manhattan() {
        int manhattanDis = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grids[i][j] == 0) {
                    continue;
                } else {
                    manhattanDis += Math.abs((grids[i][j] - 1) / N - i) + Math.abs((grids[i][j] - 1) % N - j);
                }
            }
        }
        return manhattanDis;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board yy = (Board) y;

        if (N != yy.N) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grids[i][j] != yy.grids[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int a = size();
        s.append(a + "\n");
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
