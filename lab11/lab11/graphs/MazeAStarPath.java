package lab11.graphs;

import static java.lang.Integer.MAX_VALUE;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(t) - maze.toX(v)) + Math.abs(maze.toY(t) - maze.toY(v));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked(int s) {
        int MinimumUnmarked = 0;
        int MinimumUnmarkedDis = MAX_VALUE;
        for (int w : maze.adj(s)) {
            if (!marked[w]) {
                if (h(w) < MinimumUnmarkedDis) {
                    MinimumUnmarked = w;
                    MinimumUnmarkedDis = h(w);
                }
            }
        }
        return MinimumUnmarked;
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        if (s == t)
            return;
        marked[s] = true;
        int next = findMinimumUnmarked(s);
        marked[next] = true;
        distTo[next] = distTo[s] + 1;
        edgeTo[next] = s;
        announce();
        if (next == t)
            return;
        astar(next);
    }

    @Override
    public void solve() {
        astar(s);
    }

}

