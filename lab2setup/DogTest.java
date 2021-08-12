import static org.junit.Assert.*;
import org.junit.Test;

public class DogTest {    
    @Test
    public void testSmall() {
        Dog d = new Dog(3);
        assertEquals("yip", d.noise());
        Dog d1 = new Dog(4);
        assertEquals("yip", d1.noise());
    }

    @Test
    public void testLarge() {
        Dog d = new Dog(20);
        assertEquals("bark", d.noise());
    }
}
