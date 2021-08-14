package myMap61B;

public class ExceptionDemo {
    public static void main(String[] args) {
        Map61B<String, Integer> am = new ArrayMap<String, Integer>();
        am.put("hello", 5);
        System.out.println(am.get("yolp"));
        // trigger implicit ArrayIndexOutOfBoundsException

    }
}
/**
 * use try:
 * catch () mode!
 *
 * Also, for unchecked Exception we need either provide a catch or specify (throws e) to handle it.
 */
