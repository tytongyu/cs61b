import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class trie {
    private int size = 26;
    private TrieNode root;

    private class TrieNode {
        private boolean isEnd;
        private boolean hasSon;
        private char val;
        private int sum;
        private Map<Character, TrieNode> sons;

        TrieNode() {
            isEnd = false;
            hasSon = false;
            sum = 1;
            sons = new HashMap<>();
        }
    }

    public trie() {
        root = new TrieNode();
    }

    public void insert(String s) {
        if (s == null || s.length() == 0) {
            return;
        }
        char[] charS = s.toCharArray();
        TrieNode curNode = root;
        int diff;
        for (char a : charS) {
            if (curNode.sons.get(a) == null) {
                curNode.hasSon = true;
                curNode.sons.put(a, new TrieNode());
                curNode.sons.get(a).val = a;
            } else {
                curNode.sons.get(a).sum++;
            }
            curNode = curNode.sons.get(a);
        }
        curNode.isEnd = true;
    }

    public List<String> prefixStr(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        TrieNode curNode = root;
        char[] charStr = str.toCharArray();
        for (char a : charStr) {
            if (curNode.sons.get(a) == null) {
                return null;
            } else {
                curNode = curNode.sons.get(a);
            }
        }
        return prefixStr(curNode, str);
    }

    private List<String> prefixStr(TrieNode node, String str) {
        List<String> res = new ArrayList<>();
        TrieNode curNode = node;
        if (curNode.isEnd) {
            res.add(str);
        }
        for (char a : curNode.sons.keySet()) {
            if (curNode.sons.get(a) != null) {
                res.addAll(prefixStr(curNode.sons.get(a), str + curNode.sons.get(a).val));
            }
        }
        return res;
    }

    public boolean has (String str) {
        if(str == null || str.length() == 0)
        {
            return false;
        }
        TrieNode curNode = root;
        char[] letters = str.toCharArray();
        for(char a : letters)
        {
            if(curNode.sons.get(a)!=null)
            {
                curNode= curNode.sons.get(a);
            }
            else
            {
                return false;
            }
        }
        return curNode.isEnd;
    }


}
