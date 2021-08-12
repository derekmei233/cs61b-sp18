import org.junit.Test;
import static org.junit.Assert.*;
public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    // Your tests go here.
    static CharacterComparator offByOne  = new OffByOne();

    @Test
    public void testequalChars() {
        char tp1 = 'q';
        char tq1 = 'p';
        assertTrue(offByOne .equalChars(tp1, tq1));
        char tp2 = 'q';
        char tq2 = 'r';
        assertTrue(offByOne .equalChars(tp2, tq2));
        char tp3 = 'q';
        char tq3 = 's';
        assertFalse(offByOne .equalChars(tp3, tq3));
        char tp4 = 'Q';
        char tq4 = 'P';
        assertTrue(offByOne .equalChars(tp4, tq4));
        char tp5 = 'Q';
        char tq5 = 'T';
        assertFalse(offByOne .equalChars(tp5, tq5));
        char tp6 = '*';
        char tq6 = '(';
        assertFalse(offByOne .equalChars(tp6, tq6));
        char tp7 = '(';
        char tq7 = ')';
        assertTrue(offByOne .equalChars(tp7, tq7));
        char tp8 = 't';
        char tq8 = 'S';
        assertFalse(offByOne .equalChars(tp8, tq8));
    }
}
