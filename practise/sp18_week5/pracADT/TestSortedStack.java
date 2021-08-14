package pracADT;

import org.junit.Test;
import static org.junit.Assert.*;
public class TestSortedStack {
    @Test
    public void Testsortstack() {
        SortedStack<Integer> ss = new SortedStack<>();
        ss.push(1);
        ss.push(2);
        ss.push(3);
        assertEquals((Integer) 1, ss.poll());
        ss.push(4);
        ss.push(2);
        assertEquals((Integer) 2, ss.poll());
        assertEquals((Integer) 2, ss.poll());
        ss.push(6);
        ss.push(5);
        ss.push(0);
        ss.push(9);
        assertEquals((Integer) 0, ss.poll());
        assertEquals((Integer) 3, ss.poll());
        assertEquals((Integer) 4, ss.poll());
        assertEquals((Integer) 5, ss.poll());
        assertEquals((Integer) 6, ss.poll());
        assertEquals((Integer) 9, ss.poll());
        assertNull(ss.poll());
    }
}
