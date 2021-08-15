package myadt;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestAltList {
    @Test
    public void testpairsSwapped() {
        AltList<Integer, String> list =
                new AltList<Integer, String>(5,
                        new AltList<String, Integer>("cat",
                                new AltList<Integer, String>(10,
                                        new AltList<String, Integer>("dog",
                                                new AltList<Integer, String>(15,
                                                new AltList<String, Integer>("bird", null))))));
        AltList<String, Integer> actuallist = list.pairsSwapped();
        assertEquals("cat", actuallist.get());
        assertEquals((Integer) 5, actuallist.next().get());
        assertEquals("dog", actuallist.next().next().get());
        assertEquals((Integer) 10, actuallist.next().next().next().get());
        assertEquals("bird", actuallist.next().next().next().next().get());
        assertEquals((Integer) 15, actuallist.next().next().next().next().next().get());
    }
}
