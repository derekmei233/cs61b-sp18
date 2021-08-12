package MyList;

/** MyList.SLList is a list of integers, which hide detials of how it contains
 * data
 */
public class SLList{
    /** nested class */
    private static class IntNode{
        /** static means cannot access an of the outer class's stuff */
        int item;
        IntNode next;
        IntNode(int i, IntNode n){
            item = i;
            next = n;
        }
    }
    //private IntNode first; we use sentinel here(never be changed )
    private IntNode sentinel;
    private int size = 0;
    /** we need some consistencies to make empty list act like normal ones */
    public SLList(){
        sentinel = new IntNode(-5, null);
        size = 0;
    }
    public SLList(int x){
        sentinel = new IntNode(-5, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    /**add x to the front of the list */
    public void addFirst(int x){
        sentinel.next = new IntNode(x, sentinel.next);
        size += 1;
    }
    /** add x to the back of the list */
    public void addLast(int x){
        IntNode p = sentinel;
        while (p.next != null){
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    /** two slow */
    public int size(){
       //  return _size(first); # now we make it as information 
       return size;
    }
    public int getFirst(){
        return sentinel.next.item;
    }

    public static SLList square(SLList L){
        IntNode ptr = L.sentinel;
        while (ptr.next != null) {
            ptr = ptr.next;
            ptr.item *= ptr.item;
        }
        return L;
    }



    public static void main(String[] args){
        SLList L = new SLList(15);
        L.addFirst(10);
        L.addFirst(5);
        L.addLast(20);
        System.out.println(L.getFirst());
        SLList L2 = new SLList();
        L2.addLast(4);
        System.out.println(L2.getFirst());
    }

}