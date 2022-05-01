import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator a = new OffByN(5);
    @Test
    public void testOffByN() {
        assertTrue(a.equalChars('a', 'f'));
        assertTrue(a.equalChars('f', 'a'));
        assertFalse(a.equalChars('s', 'f'));
    }
}
