public class AList<T> implements List61B<T> {
    private T[] items;
    private int size;
    private static int RFACTOR = 2;

    public AList(){
        /** we cannot new array in java */
        items = (T[]) new Object[2];
        size = 0; 
    }
    private void _resize(){
        T[] newItems = (T[]) new Object[items.length * RFACTOR];
        System.arraycopy(items, 0, newItems, 0, items.length);
        items = newItems;
    }
    @Override
    public void addFirst(T x){
        if (items.length == size){
            _resize();
        }
        T[] newItems = (T[]) new Object[items.length];
        System.arraycopy(items, 0, newItems, 1, size);
        newItems[0] = x;
        items = newItems;
        size += 1;
    }
    @Override
    public void addLast(T x){
        if (items.length == size){
            _resize();
        }
        items[size] = x;
        size += 1;
    }
    @Override
    public T getFirst(){
        return items[0];
    }
    @Override
    public T getLast(){
        return items[size - 1];
    }
    @Override
    public T removeLast(){
        T last = items[ size - 1];
        items[ size - 1] = null;
        size -= 1;
        return last;
    }
    @Override
    public T get(int i){
        return items[ i ];
    }
    @Override
    public void insert(T x, int pos){
        if ( items.length == size){
            _resize();
        }
        T[] newItems = (T[]) new Object[items.length];
        if (pos == 0){
            addFirst(x);
        } else {
            System.arraycopy(items, 0, newItems, 0, pos);
            newItems[pos] = x;
            System.arraycopy(items, pos, newItems, pos + 1, size - pos);
            items = newItems;
            size += 1;
        }
    }
    @Override
    public int size(){
        return size;
    }
    
}