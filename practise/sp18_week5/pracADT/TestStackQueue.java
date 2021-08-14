package pracADT;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStackQueue {
    public static void constructQueue(StackQueue<String> dsq) {
        dsq.push("first");
        dsq.push("second");
        dsq.push("third");
        dsq.push("fourth");
        dsq.push("fifth");
    }
    @Test
    public void testDoubleStackQueue() {
        DoubleStackQueue<String> dsq = new DoubleStackQueue<>();
        constructQueue(dsq);
        String result = dsq.poll();
        assertEquals("first", result);
        result = dsq.poll();
        assertEquals("second", result);
        result = dsq.poll();
        assertEquals("third", result);
        // test push new elements
        dsq.push("sixth");
        dsq.push("seventh");
        result = dsq.poll();
        assertEquals("fourth", result);
        result = dsq.poll();
        assertEquals("fifth", result);
        result = dsq.poll();
        assertEquals("sixth", result);
        result = dsq.poll();
        assertEquals("seventh", result);
    }
    @Test
    public void testSingleStackQueue() {
        SingleStackQueue<String> ssq = new SingleStackQueue<>();
        constructQueue(ssq);
        String result = ssq.poll();
        assertEquals("first", result);
        result = ssq.poll();
        assertEquals("second", result);
        result = ssq.poll();
        assertEquals("third", result);
        // test push new elements
        ssq.push("sixth");
        ssq.push("seventh");
        result = ssq.poll();
        assertEquals("fourth", result);
        result = ssq.poll();
        assertEquals("fifth", result);
        result = ssq.poll();
        assertEquals("sixth", result);
        result = ssq.poll();
        assertEquals("seventh", result);
    }
}
