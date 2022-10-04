package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;


public class Solver {
    private int times;
    private List listSolution = new ArrayList<>();


    private class SearchNode implements Comparable {
        private WorldState world;
        private int movesNum;
        private SearchNode previous;

        @Override
        public int compareTo(Object o) {
            SearchNode p = (SearchNode) o;
            return movesNum + getEdtg(this) - getEdtg(p) - p.movesNum;
        }

        public SearchNode(WorldState a, int b, SearchNode c) {
            world = a;
            movesNum = b;
            previous = c;
        }

        private int getEdtg(SearchNode sn) {
            if (!edtgCaches.containsKey(sn.world)) {
                edtgCaches.put(sn.world, sn.world.estimatedDistanceToGoal());
            }
            return edtgCaches.get(sn.world);
        }

    }

    private Map<WorldState, Integer> edtgCaches = new HashMap<>();

    public Solver(WorldState initial) {
        SearchNode initialNode = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> a = new MinPQ<SearchNode>();
        a.insert(initialNode);
        SearchNode cur = null;
        while (!a.isEmpty()) {
            cur = (SearchNode) a.delMin();
            if (cur.world.isGoal()) {
                break;
            }
            for (WorldState x : cur.world.neighbors()) {
                if (cur.previous == null) {
                    a.insert(new SearchNode(x, cur.movesNum + 1, cur));
                } else {
                    if (!x.equals(cur.previous.world)) {
                        a.insert(new SearchNode(x, cur.movesNum + 1, cur));
                    }
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
            listSolution.add(reverse.pop());
        }
    }
    public int moves() {
        return times;
    }
    public Iterable<WorldState> solution() {
        return listSolution;
    }
}
