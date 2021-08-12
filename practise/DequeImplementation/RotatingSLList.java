/** SLList, but with additional roatateRight operation. */
public class RotatingSLList<T> extends SLList<T> {
    /** note: it is not default initialized because this class just inherit construcuer from
     * parent class which will not initialize this deletedItems.
     * hint: We may need a new constructer which can initialize deletedItem and construct 
     * partent class as well.
     */
    private SLList<T> deletedItems;
    public RotatingSLList() {
        deletedItems = new SLList<T>();
    }

    public void printLostItems() {
        deletedItems.print();
        // we can use super.print() not this.print()
    }


    /** one big thing in java: private is absolutely private, even to inherit subclasses */
    @Override
    public T removeLast() {
        T x = super.removeLast();
        deletedItems.addLast(x);
        return x;
    }
    public void rotateRight() {
        addFirst(getLast());
        removeLast();
    }

    public static void main(String[] args) {
        RotatingSLList<Integer> rs1 = new RotatingSLList<>();
        /* create SLList [10, 11, 12, 13] */
        rs1.addLast(10);
        rs1.addLast(11);
        rs1.addLast(12);
        rs1.addLast(13);

        rs1.rotateRight();
        rs1.print();
        rs1.printLostItems();
    }
}