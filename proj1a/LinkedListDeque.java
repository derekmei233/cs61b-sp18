public class LinkedListDeque<T> {
    private class DNode {
        DNode prev;
        T val;
        DNode next;
        DNode(DNode p, T x, DNode n) {
            prev = p;
            val = x;
            next = n;
        }
    }
    private DNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new DNode(null, null, null);
        size = 0;
    }
    private void initLLD(T x) {
        sentinel.next = new DNode(sentinel, x, sentinel);
        sentinel.prev = sentinel.next;
        size += 1;
    }
    public LinkedListDeque(T x) {
        sentinel = new DNode(null, null, null);
        initLLD(x);
    }

    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new DNode(null, null, null);
        // don't assign size. because it will effect addLast method when taking size != 0.
        //Actually it is 0 at this time point !!!
        DNode ptr = other.sentinel;
        if (other.size == 0) {
            return;
        } else {
            // ptr should point to its sentinel(other.sentinel) not this.sentinel
            while ( ptr.next != other.sentinel) {
                addLast(ptr.next.val);
                size += 1;
                ptr = ptr.next;
            }
        }
    }
    public void addFirst(T item) {
        if (isEmpty()) {
            initLLD(item);
        } else {
            DNode ptr = sentinel.next;
            sentinel.next = new DNode(sentinel, item, sentinel.next);
            ptr.prev = sentinel.next;
            size += 1;
        }
    }
    public void addLast(T item) {
        if (isEmpty()) {
            initLLD(item);
        } else {
            DNode ptr = sentinel.prev;
            sentinel.prev = new DNode(sentinel.prev, item, sentinel);
            ptr.next = sentinel.prev;
            size += 1;
        }
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        if (isEmpty()) {
            System.out.println();
        } else {
            DNode ptr = sentinel.next;
            while (ptr != sentinel) {
                System.out.print(ptr.val);
                System.out.print(' ');
                ptr = ptr.next;
            }
            System.out.println();
        }
    }
    private void delLLD() {
        sentinel = new DNode(null, null, null);
        size = 0;
    }
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            T result = sentinel.next.val;
            delLLD();
            return result;
        } else {
            T result = sentinel.next.val;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return result;
        }
    }
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            T result = sentinel.prev.val;
            delLLD();
            return result;
        } else {
            T result = sentinel.prev.val;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return result;
        }
    }
    public T get(int index) {
        int endDistance = size - 1 - index;
        int startDistance = index - 0;
        DNode ptr = sentinel;
        if (startDistance <= endDistance) {
            ptr = ptr.next;
            while (startDistance != 0) {
                ptr = ptr.next;
                startDistance -= 1;
            }
            return ptr.val;
        } else {
            ptr = ptr.prev;
            while (endDistance != 0) {
                ptr = ptr.prev;
                endDistance -= 1;
            }
            return ptr.val;
        }
    }
    private T getrecirsiveHelper(int reside, DNode n, boolean rev) {
        if (reside == 0) {
            return n.val;
        } else {
            if (rev) {
                n = n.prev;
            } else {
                n = n.next;
            }
            return getrecirsiveHelper(reside - 1, n, rev);
        }
    }
    public T getRecursive(int index) {
        int endDistance = size - 1 - index;
        int startDistance = index - 0;
        DNode ptr = sentinel;
        if (startDistance <= endDistance) {
            ptr = ptr.next;
            return getrecirsiveHelper(startDistance, ptr, false);
        } else {
            ptr = ptr.prev;
            return getrecirsiveHelper(endDistance, ptr, true);
        }
    }
}
