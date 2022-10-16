import java.util.List;

public class test {
    public static void main(String[] args) {
        String a = "abc";
        String b = "abcd";
        String c = "abce";
        String d = "abcde";
        String e = "abcdf";
        String f = "abcfd";
        trie tse = new trie();
        System.out.println(tse.has("a"));
        tse.insert(a);
        tse.insert(b);
        tse.insert(c);
        tse.insert(d);
        tse.insert(e);
        tse.insert(f);
        System.out.println(tse.has("a"));
        System.out.println(tse.has("abc"));
        System.out.println(tse.has("abcde"));
        System.out.println(tse.has("abced"));

        List<String> aa = tse.prefixStr("a");
        List<String> bb = tse.prefixStr("abcd");
        List<String> cc = tse.prefixStr("abcf");
        List<String> dd = tse.prefixStr("abcff");

    }
}
