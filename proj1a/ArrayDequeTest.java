public class ArrayDequeTest {
    public static void testConstructer() {
        System.out.println("start Constructer test");
        ArrayDeque<String> aDD = new ArrayDeque<>();
        System.out.println("finished Default Constructer");
    }
    private static ArrayDeque creatTestArray(int size, boolean inc) {
        ArrayDeque<String> tAD = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            String j = String.valueOf(i);
            if (inc) {
                tAD.addLast(j);
            } else {
                tAD.addFirst(j);
            }
        }
        return tAD;
    }
    public static void testcopyConstructer() {
        System.out.println("start copy constructer");
        ArrayDeque<String> source = creatTestArray(17, false);
        ArrayDeque<String> dest = new ArrayDeque<>(source);
        System.out.println("finished copy constructer");
    }
    public static void testaddFirst() {
        creatTestArray(17, false);
    }
    public static void testaddLast() {
        creatTestArray(17, true);
    }
    public static void testremoveFirst() {
        ArrayDeque<String> test = creatTestArray(21, false);
        int times = 21;
        while (times !=  -1) {
            test.removeFirst();
            times -= 1;
        }
    }
    public static void testremoveLast() {
        ArrayDeque<String> test = creatTestArray(21, false);
        int times = 21;
        while (times !=  -1) {
            test.removeLast();
            times -= 1;
        }
    }
    public static void testget() {
        ArrayDeque<String> test = creatTestArray(21, false);
        int times = 20;
        String result;
        while (times !=  -1) {
            result = test.get(times);
            times -= 1;
            System.out.print(result);
            System.out.print(' ');
        }
    }

    public static void main(String[] args) {
        testConstructer();
        testaddFirst();
        testaddLast();
        testcopyConstructer();
        testremoveLast();
        testget();
    }
}
