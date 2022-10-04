package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.*;

public class Solver {
    int times;
    List ListSolution = new ArrayList<>();


    private class searchNode implements Comparable{
        private WorldState world;
        private int movesNum;
        private searchNode previous;

        @Override
        public int compareTo (Object o) {
            searchNode p = (searchNode)o;
            return movesNum + getEdtg(this) - getEdtg(p) - p.movesNum;
        }

        public searchNode (WorldState a, int b, searchNode c) {
            world = a;
            movesNum = b;
            previous = c;
        }

        private int getEdtg(searchNode sn) {
            if (!edtgCaches.containsKey(sn.world)) {
                edtgCaches.put(sn.world, sn.world.estimatedDistanceToGoal());
            }
            return edtgCaches.get(sn.world);
        }

    }

    private Map<WorldState, Integer> edtgCaches = new HashMap<>();

    public Solver(WorldState initial) {
        searchNode initialNode = new searchNode(initial, 0, null);
        MinPQ<searchNode> a = new MinPQ<searchNode>();
        a.insert(initialNode);
        searchNode cur = null;
        while (!a.isEmpty()) {
            cur = (searchNode)a.delMin();
            if (cur.world.isGoal()) break;
            for (WorldState x : cur.world.neighbors()) {
                if (cur.previous == null) {
                    a.insert(new searchNode(x, cur.movesNum + 1, cur));
                } else {
                    if (!x.equals(cur.previous.world))
                        a.insert(new searchNode(x, cur.movesNum + 1, cur));
                }
            }
        }
        times = cur.movesNum;
        Stack<WorldState> reverse = new Stack<>();
        while (cur != null) {
            reverse.add(cur.world);
            cur = cur.previous;
        }
        while (!reverse.isEmpty()) {
            ListSolution.add(reverse.pop());
        }
    }
    public int moves() {
        return times;
    }
    public Iterable<WorldState> solution() {
        return ListSolution;
    }
}
