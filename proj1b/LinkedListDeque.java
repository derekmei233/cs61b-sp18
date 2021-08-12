public class LinkedListDeque<Item> implements Deque<Item> {
    private class DNode {
        private DNode prev;
        private Item val;
        private DNode next;
        DNode(DNode p, Item x, DNode n) {
            setPrev(p);
            setVal(x);
            setNext(n);
        }

        public DNode getPrev() {
            return prev;
        }

        public void setPrev(DNode prev) {
            this.prev = prev;
        }

        public Item getVal() {
            return val;
        }

        public void setVal(Item val) {
            this.val = val;
        }

        public DNode getNext() {
            return next;
        }

        public void setNext(DNode next) {
            this.next = next;
        }
    }
    private DNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new DNode(null, null, null);
        size = 0;
    }
    private void initLLD(Item x) {
        sentinel.setNext(new DNode(sentinel, x, sentinel));
        sentinel.setPrev(sentinel.getNext());
        size += 1;
    }
    public LinkedListDeque(Item x) {
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
            while (ptr.getNext() != other.sentinel) {
                addLast(ptr.getNext().getVal());
                size += 1;
                ptr = ptr.getNext();
            }
        }
    }
    @Override
    public void addFirst(Item item) {
        if (isEmpty()) {
            initLLD(item);
        } else {
            DNode ptr = sentinel.getNext();
            sentinel.setNext(new DNode(sentinel, item, sentinel.getNext()));
            ptr.setPrev(sentinel.getNext());
            size += 1;
        }
    }
    @Override
    public void addLast(Item item) {
        if (isEmpty()) {
            initLLD(item);
        } else {
            DNode ptr = sentinel.getPrev();
            sentinel.setPrev(new DNode(sentinel.getPrev(), item, sentinel));
            ptr.setNext(sentinel.getPrev());
            size += 1;
        }
    }
    @Override
    public int size() {
        return size;
    }
    public void printDeque() {
        if (!isEmpty()) {
            DNode ptr = sentinel.getNext();
            while (ptr != sentinel) {
                System.out.print(ptr.getVal());
                System.out.print(' ');
                ptr = ptr.getNext();
            }
        }
        System.out.println();
    }
    private void delLLD() {
        sentinel = new DNode(null, null, null);
        size = 0;
    }
    @Override
    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Item result = sentinel.getNext().getVal();
        if (size == 1) {
            delLLD();
        } else {
            sentinel.setNext(sentinel.getNext().getNext());
            sentinel.getNext().setPrev(sentinel);
            size -= 1;
        }
        return result;
    }
    @Override
    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }
        Item result = sentinel.getPrev().getVal();
        if (size == 1) {
            delLLD();
        } else {
            sentinel.setPrev(sentinel.getPrev().getPrev());
            sentinel.getPrev().setNext(sentinel);
            size -= 1;
        }
        return result;
    }
    @Override
    public Item get(int index) {
        int endDistance = size - 1 - index;
        int startDistance = index - 0;
        DNode ptr = sentinel;
        if (startDistance <= endDistance) {
            ptr = ptr.getNext();
            while (startDistance != 0) {
                ptr = ptr.getNext();
                startDistance -= 1;
            }
        } else {
            ptr = ptr.getPrev();
            while (endDistance != 0) {
                ptr = ptr.getPrev();
                endDistance -= 1;
            }
        }
        return ptr.getVal();
    }
    private Item getrecirsiveHelper(int reside, DNode n, boolean rev) {
        if (reside == 0) {
            return n.getVal();
        } else {
            if (rev) {
                n = n.getPrev();
            } else {
                n = n.getNext();
            }
            return getrecirsiveHelper(reside - 1, n, rev);
        }
    }
    @Override
    public Item getRecursive(int index) {
        int endDistance = size - 1 - index;
        int startDistance = index - 0;
        DNode ptr = sentinel;
        if (startDistance <= endDistance) {
            ptr = ptr.getNext();
            return getrecirsiveHelper(startDistance, ptr, false);
        } else {
            ptr = ptr.getPrev();
            return getrecirsiveHelper(endDistance, ptr, true);
        }
    }
}

