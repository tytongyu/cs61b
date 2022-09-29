package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import static java.lang.Integer.MAX_VALUE;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        s = m.xyTo1D(sourceX, sourceY);
        t = m.xyTo1D(targetX, targetY);

        maze = m;

        marked = new boolean[m.V()];
        distTo = new int[m.V()];
        edgeTo = new int[m.V()];
        for (int i = 0; i < m.V(); i++)
            distTo[i] = MAX_VALUE;
        distTo[s] = 0;
        edgeTo[s] = s;
        marked[s] = true;
        bfs(m, s);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(Maze m, int v) {
        Queue<Integer> a = new Queue<Integer>();
        if (v == t)
            return;
        a.enqueue(v);
        announce();

        while (!a.isEmpty()) {
            int s = a.dequeue();
            for (int w : maze.adj(s)) {
                if (!marked[w]) {
                    distTo[w] = distTo[s] +1;
                    edgeTo[w] = s;
                    announce();
                    marked[w] = true;
                    if (w == t) return;
                    a.enqueue(w);
                }
            }
        }
    }

    private void bfs(Maze m, Iterable<Integer> sources) {
        Queue<Integer> a = new Queue<Integer>();
        announce();
        for (int v : sources) {
            marked[v] = true;
            distTo[v] = 0;
            a.enqueue(v);
        }
        while (!a.isEmpty()) {
            int s = a.dequeue();
            for (int w : maze.adj(s)) {
                if (!marked[w]) {
                    distTo[w] = distTo[s] +1;
                    edgeTo[w] = s;
                    announce();
                    marked[w] = true;
                    if (w == t) return;
                    a.enqueue(w);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(maze, s);
    }
}

