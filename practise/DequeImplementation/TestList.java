public class TestList{
    private static void TestGeneralFuntions(){
        System.out.println("Test SLList: ");
        SLList<String> slist = new SLList<>();
        slist.addFirst("first");
        slist.addFirst("second");
        slist.addLast("third");
        slist.insert("forth", 1);
        System.out.println(slist.size());
        slist.print();
        System.out.println(slist.get(1));
        System.out.println(slist.getFirst());
        System.out.println(slist.getLast());
        System.out.println(slist.removeLast());
        slist.print();

        System.out.println("Test AList: ");
        AList<String> alist = new AList<>();
        alist.addFirst("first");
        alist.addFirst("second");
        alist.addLast("third");
        alist.insert("forth", 1);
        alist.print();
        System.out.println(alist.get(1));
        System.out.println(alist.getFirst());
        System.out.println(alist.getLast());
        System.out.println(alist.removeLast());
        alist.print();
    }
    private static void TestReverse(){
        SLList<String> slist = new SLList<>();
        slist.addFirst("first");
        slist.addFirst("second");
        slist.addLast("third");
        slist.insert("forth", 1);
        slist.insert("fifth", 10);
        slist.print();
        slist.iter_reverse_inplace();
        slist.print();
        slist.recur_reverse_inplace();
        slist.print();
    }
    public static void main(String[] args){
        //TestGeneralFuntions();
        TestReverse();
    }
    
}