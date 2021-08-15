package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(10);
        arb.enqueue("a");
        arb.enqueue("b");
        arb.enqueue("c");
        arb.enqueue("d");
        arb.enqueue("e");
        assertEquals("a", arb.peek());
        assertEquals("a", arb.dequeue());
        assertEquals("b", arb.peek());
        arb.enqueue("f");
        arb.enqueue("g");
        arb.enqueue("h");
        for (String s: arb) {
            System.out.println(s);
        }
        assertEquals("b", arb.dequeue());
        assertEquals("c", arb.dequeue());
        assertEquals("d", arb.dequeue());
        assertEquals("e", arb.dequeue());
        assertEquals("f", arb.dequeue());
        assertEquals("g", arb.dequeue());
        assertEquals("h", arb.dequeue());
        arb.enqueue("i");
        assertEquals("i", arb.peek());

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
