import java.util.HashMap;

public class Tria {

    private Node root;

    private class Node {
        private char val;
        private boolean hasChildren;
        private boolean isWORD;
        private HashMap<Character, Node> sons;

        public Node(char val) {
            this.val = val;
            hasChildren = false;
            isWORD = false;
            sons = new HashMap<>();
        }
    }

    public Tria() {
        root = new Node('a');
    }

    public void addWord(String word) {
        Node cur = root;
        for (char a : word.toCharArray()) {
            if (cur.hasChildren == false || !cur.sons.containsKey(a)) {
                cur.hasChildren = true;
                cur.sons.put(a, new Node(a));
            }
            cur = cur.sons.get(a);
        }
        cur.isWORD = true;
    }

    public boolean hasPre(String word) {
        Node cur = root;
        for (char a : word.toCharArray()) {
            if (cur.hasChildren == false || !cur.sons.containsKey(a)) {
                return false;
            }
            cur = cur.sons.get(a);
        }
        return true;
    }

    public boolean isWord(String word) {
        Node cur = root;
        for (char a : word.toCharArray()) {
            if (cur.hasChildren == false || !cur.sons.containsKey(a)) {
                return false;
            }
            cur = cur.sons.get(a);
        }
        return cur.isWORD;
    }
}
