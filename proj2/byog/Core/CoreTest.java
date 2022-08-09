package byog.Core;
import org.junit.Test;
import static org.junit.Assert.*;

public class CoreTest {
    @Test
    public void RoomTest() {
        Position x1 = new Position(1,2);
        Position x2 = new Position(3,4);
        Position x3 = new Position(1,4);
        Position x4 = new Position(2,3);
        Position x5 = new Position(4,6);
        Position x6 = new Position(10,10);
        Room a = new Room(x1,x2);
        assertEquals(2,a.getwidth(),10e-3);
        assertEquals(2,a.getheight(),10e-3);
        Room b = new Room(x1,x4);
        assertEquals(1,b.getwidth(),10e-3);
        assertEquals(1,b.getheight(),10e-3);
        Room c = new Room(x1,x3);
        assertTrue(a.isLegal());
        assertFalse(c.isLegal());
        assertEquals(2,a.middlePosition().getxPosition(),10e-3);
        assertEquals(3,a.middlePosition().getyPosition(),10e-3);
        Room d = new Room(x1,x4);
        Room e = new Room(x2,x5);
        assertFalse(d.isOverlapped(e));
        Room f = new Room(x1,x5);
        Room g = new Room(x2,x6);
        assertTrue(f.isOverlapped(g));
    }

}
