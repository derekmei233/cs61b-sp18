public class TestLinkedListDeque {
    public static void testConstructers() {
        LinkedListDeque<String> dDLD = new LinkedListDeque<>();
        System.out.println("finished default constructor");
        LinkedListDeque<String> dDLT = new LinkedListDeque<>("first");
        System.out.println("finished init_T constructor");
    }
    public static void testaddFirst() {
        LinkedListDeque<String> test1 = new LinkedListDeque<>();
        System.out.println("start addFirst");
        test1.addFirst("first");
        test1.addFirst("second");
        test1.addFirst("third");
        System.out.println("finished addFirst");
    }
    public static void testaddLast() {
        System.out.println("start addLast");
        LinkedListDeque<String> test1 = new LinkedListDeque<>();
        test1.addLast("first");
        test1.addLast("second");
        test1.addLast("third");
        System.out.println("finished addLast");
    }
    public static void testCopyConstructer() {
        System.out.println("start copy constructure");
        LinkedListDeque<String> source1 = new LinkedListDeque<>();
        LinkedListDeque<String> dest1 = new LinkedListDeque<>(source1);
        System.out.println("finished copy constructure test1");

        LinkedListDeque<String> source2 = new LinkedListDeque<>("first");
        LinkedListDeque<String> dest2 = new LinkedListDeque<>(source2);
        System.out.println("finished copy constructure test2");

        LinkedListDeque<String> source3 = new LinkedListDeque<>("first");
        source3.addFirst("second");
        source3.addFirst("third");
        source3.addFirst("fourth");
        LinkedListDeque<String> dest3 = new LinkedListDeque<>(source3);
        System.out.println("finished copy constructure test3");
    }
    public static void testremoveFirst() {
        System.out.println("start removeFirst test");
        LinkedListDeque<String> test1 = new LinkedListDeque<>("first");
        String result1 = test1.removeFirst();
        System.out.println("finished removeFirst test1");

        LinkedListDeque<String> test2 = new LinkedListDeque<>("first");
        test2.addFirst("second");
        String result2 = test2.removeFirst();
        System.out.println("finished removeFirst test2");

        LinkedListDeque<String> test3 = new LinkedListDeque<>("first");
        test3.addFirst("second");
        test3.addFirst("third");
        test3.addFirst("fourth");
        String result3 = test3.removeFirst();
        System.out.println("finished removeFirst test3");
    }
    public static void testremoveLast() {
        System.out.println("start removeLast test");
        LinkedListDeque<String> test1 = new LinkedListDeque<>("first");
        String result1 = test1.removeLast();
        System.out.println("finished removeLast test1");

        LinkedListDeque<String> test2 = new LinkedListDeque<>("first");
        test2.addFirst("second");
        String result2 = test2.removeLast();
        System.out.println("finished removeLast test2");

        LinkedListDeque<String> test3 = new LinkedListDeque<>("first");
        test3.addFirst("second");
        test3.addFirst("third");
        test3.addFirst("fourth");
        String result3 = test3.removeLast();
        System.out.println("finished removeLast test3");
    }
    public static void testget() {
        System.out.println("start test get");
        LinkedListDeque<String> test1 = new LinkedListDeque<>("first");
        System.out.println(test1.get(0));
        System.out.println("finished get test1");

        LinkedListDeque<String> test2 = new LinkedListDeque<>("0");
        test2.addLast("1");
        test2.addLast("2");
        test2.addLast("3");
        test2.addLast("4");
        test2.addLast("5");
        test2.addLast("6");
        test2.addLast("7");
        test2.addLast("8");
        System.out.println(test2.get(0));
        System.out.println(test2.get(2));
        System.out.println(test2.get(4));
        System.out.println(test2.get(8));
        test2.get(7);
        System.out.println("finished get test2");
    }
    public static void testgetRecursive() {
        System.out.println("start test getRecursive");
        LinkedListDeque<String> test1 = new LinkedListDeque<>("first");
        System.out.println(test1.getRecursive(0));
        System.out.println("finished getRecursive test1");

        LinkedListDeque<String> test2 = new LinkedListDeque<>("0");
        test2.addLast("1");
        test2.addLast("2");
        test2.addLast("3");
        test2.addLast("4");
        test2.addLast("5");
        test2.addLast("6");
        test2.addLast("7");
        test2.addLast("8");
        System.out.println(test2.getRecursive(0));
        System.out.println(test2.getRecursive(2));
        System.out.println(test2.getRecursive(4));
        System.out.println(test2.getRecursive(8));
        test2.getRecursive(7);
        System.out.println("finished getRecursive test2");
    }
    public static void testPrintDeque() {
        System.out.println("start test printDeque");
        LinkedListDeque<String> test = new LinkedListDeque<>("0");
        test.addLast("1");
        test.addLast("2");
        test.addLast("3");
        test.addLast("4");
        test.addLast("5");
        test.addLast("6");
        test.addLast("7");
        test.addLast("8");
        test.printDeque();
        System.out.println("finished test printDeque");
    }
    public static void main(String[] args) {
        testConstructers();
        testaddFirst();
        testaddLast();
        testCopyConstructer();
        testremoveFirst();
        testremoveLast();
        testget();
        testgetRecursive();
        testPrintDeque();
    }
}
