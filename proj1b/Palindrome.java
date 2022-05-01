public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> a = new ArrayDeque<>();
        if (word == null) {
            return a;
        }
        for (int i = 0; i < word.length(); i++) {
            a.addLast(word.charAt(i));
        }
        return a;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> a = wordToDeque(word);
        return isPalindromehelper(a);
    }

    private boolean isPalindromehelper(Deque<Character> a) {
        if (a.size() < 2) {
            return true;
        } else if (a.get(0) != a.get(a.size() - 1)) {
            return false;
        } else {
            a.removeFirst();
            a.removeLast();
            return isPalindromehelper(a);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() < 2) {
            return true;
        }
        for (int i = 0; i < word.length() / 2; i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(word.length() - 1 - i))) {
                return false;
            }
        }
        return true;
    }
}
