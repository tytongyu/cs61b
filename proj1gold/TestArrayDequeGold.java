import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test
    public void testArrayDeque() {
        ArrayDequeSolution<Integer> expected= new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> actual= new StudentArrayDeque<>();
        StringBuilder s = new StringBuilder();
        int i = 0;
        expected.addFirst(3);
        expected.addLast(2);
        expected.addFirst(1);
        expected.addLast(4);
        actual.addFirst(3);
        actual.addLast(2);
        actual.addFirst(1);
        actual.addLast(4);
        while (i < 100) {
            double a = StdRandom.uniform(0,1);
            if (i % 5 == 0) {
                s.append("size()\n");
                assertEquals(s.toString(), expected.size(), actual.size());
            }
            if(a < 0.5) {
                expected.addFirst(i);
                expected.addFirst(i);
                s.append("addFirst(" + i +")\n");
                /*assertEquals(s.toString(), expected.get(0), actual.get(0));*/
                assertEquals(s.toString(), expected.get(0), actual.get(0));
            } else {
                expected.addLast(i);
                actual.addLast(i);
                s.append("addLast(" + i +")\n");
                assertEquals(s.toString(), expected.get((expected.size())-1), actual.get((expected.size())-1));
            }
            while (expected.isEmpty()) {
                s.append("isEmpty()\n");
                assertTrue(s.toString(), actual.isEmpty());
            }
            while (!expected.isEmpty()) {
                s.append("isEmpty()\n");
                assertFalse(s.toString(), actual.isEmpty());

                s.append("removeFirst()\t" + expected.removeFirst()+"\n");
                /*assertEquals(s.toString(), expected.removeFirst(), actual.removeFirst());*/
                assertEquals(s.toString(), expected.removeFirst(), actual.removeFirst());
            }
            while (!expected.isEmpty()) {
                s.append("isEmpty()\n");
                assertFalse(s.toString(), actual.isEmpty());

                s.append("removeLast()\t" + expected.removeLast()+"\n");
                assertEquals(s.toString(), expected.removeLast(), actual.removeLast());
            }
            i++;
        }
    }
}
