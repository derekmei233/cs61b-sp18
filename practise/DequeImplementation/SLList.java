public class  SLList<T> implements List61B<T> {
    private class Node {
        /** don't need Node take generic template T. T has already been declared in SLList class */
        T first;
        Node rest;
        Node(T p, Node n){
            first = p;
            rest = n;
        }
    }
    private Node sentinel;
    private int size;
    public SLList() {
        sentinel = new Node(null, null);
        size = 0;
    }
    public SLList(T x) {
        sentinel = new Node(null, null);
        sentinel.rest = new Node(x, null);
        size = 1;
    }
    @Override
    public void addFirst(T x) {
        sentinel.rest = new Node(x, sentinel.rest);
        size += 1;
    }
    @Override
    public void addLast(T x) {
        Node ptr = sentinel;
        while (ptr.rest != null) {
            ptr = ptr.rest;
        }
        ptr.rest = new Node(x, null);
        size += 1;
    }
    @Override
    public T getFirst() {
        return sentinel.rest.first;
    }
    @Override
    public T getLast(){
        Node ptr = sentinel;
        while (ptr.rest != null) {
            ptr = ptr.rest;
        }
        return ptr.first;
    }
    @Override
    public T removeLast() {
        Node ptr = sentinel;
        while(ptr.rest != null) {
            ptr = ptr.rest;
        }
        Node last = ptr;
        ptr = null;
        size -= 1;
        return last.first;
    }
    @Override
    public T get(int i) {
        Node ptr = sentinel;
        while ( i != 0) {
            ptr = ptr.rest;
            i -= 1;
        }
        return ptr.rest.first;
    }
    /**
     * if pos greater than the boundary index, insert at end
     * */
    @Override
    public void insert(T x, int pos) {
        if ( pos >= size){
            addLast(x);
            return;
        }
        Node ptr = sentinel;
        while ( pos != 0 ) {
            ptr = ptr.rest;
            pos -= 1;
        }
        /** don't need new a new Node here. could be assigned by ptr.rest directly */
        ptr.rest = new Node(x, ptr.rest);
        size += 1;
    }
    @Override
    public int size(){
        return size;
    }
    /**
     * use iterative method reverse SLList structure
     * */
    public void iter_reverse_inplace(){
        // bc1
        if (size == 0 || size == 1){
            return;
        }
        Node ptr = sentinel.rest;
        Node temp = ptr.rest;
        Node ptr_reside = ptr;
        ptr.rest = null;
        ptr = temp;
        while (ptr.rest != null){
            temp = ptr.rest;
            ptr.rest = ptr_reside;
            ptr_reside = ptr;
            ptr = temp;
        }
        ptr.rest = ptr_reside;
        sentinel.rest = ptr;
    }

    /**
     * recursive version is more straight forward in this task. since we need use overridden variable later.
     * recursive can pass this variable into function first.
     * */
    private void _reverseHelper(Node b, Node n){
        if (n.rest == null){
            sentinel.rest = n;
            n.rest = b;
            return;
        }
        // use what we need first, then override it.(save one variable)
        _reverseHelper(b.rest, n.rest);
        n.rest = b;
    }
    public void recur_reverse_inplace(){
        if (size == 0 || size == 1){
            return;
        }
        Node ptrb = sentinel.rest;
        Node ptrn = ptrb.rest;
        _reverseHelper(ptrb, ptrn);
    }

}