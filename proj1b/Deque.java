public interface Deque<Item> {
    void addFirst(Item x);
    void addLast(Item x);
    Item removeFirst();
    Item removeLast();
    Item get(int pos);
    Item getRecursive(int pos);
    int size();
    void printDeque();
    default boolean isEmpty() {
        return size() == 0;
    }
}
