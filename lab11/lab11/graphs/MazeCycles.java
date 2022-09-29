package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        distTo[0] = 0;
        edgeTo[0] = 0;
        dfs(0);
    }

    public void dfs(int s) {
        marked[s] = true;
        announce();

        for (int a : maze.adj(s)) {
            if (marked[a] && edgeTo[s] != a) {
                announce();
                return;
            }
            if (!marked[a]) {
                distTo[a] = distTo[s] + 1;
                marked[a] = true;
                edgeTo[a] = s;
                announce();
                dfs(a);
            }

        }
    }
}

