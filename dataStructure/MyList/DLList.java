package MyList;

public class DLList{
    public static class IntNode{
        int item;
        IntNode next;
        IntNode prev;
        IntNode(IntNode p, int x, IntNode n){
            prev = p;
            item = x;
            next = n;
        }
    }
    private IntNode sentinel;
    private int size;

    public DLList(){
        sentinel = new IntNode(sentinel, -1, sentinel);
        size = 0;
    }
    public DLList(int x){
        sentinel = new IntNode(sentinel, -1, sentinel.next);
        sentinel.next = new IntNode(sentinel, x, sentinel.next);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    public int size(){
        return size;
    }
    public void addFirst(int x){
        sentinel.next = new IntNode(sentinel, x, sentinel.next);
        if (size == 0){
            sentinel.prev = sentinel.next;
        }
        size += 1;
    }
    public void addFLast(int x){
        sentinel.prev = new IntNode(sentinel.prev, x, sentinel);
        if (size == 0){
            sentinel.next = sentinel.prev;
        }
        size += 1;
    }
    

}