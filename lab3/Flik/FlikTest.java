import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest{
    @Test
    public void FlikTest(){
        assertTrue( Flik.isSameNumber(5, 5));
        assertFalse(Flik.isSameNumber(5, 6));
        assertTrue( Flik.isSameNumber(11, 11));
        assertFalse(Flik.isSameNumber(11, 12));
        assertTrue( Flik.isSameNumber(26, 26));
        assertFalse(Flik.isSameNumber(26, 27));
        assertTrue( Flik.isSameNumber(100, 100));
        assertFalse(Flik.isSameNumber(105, 106));

        assertFalse(Flik.isSameNumber(128, 127));
        assertTrue(Flik.isSameNumber(499, 499));
        assertFalse(Flik.isSameNumber(499, 500));
    }
}
