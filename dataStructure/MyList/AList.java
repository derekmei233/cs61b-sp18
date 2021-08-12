package MyList;

public class AList<T>{
    private int size;
    private T[] items;
    /**
     * construct empty list
     */
    public AList(){
        /**
         * generic arraies are not allowed
         * use cast to solve
         */
        items = (T[]) new Object[100];
        size = 0;
    }
    public void addLast(T x){
        if (size == items.length){
            _resize();
        }
        items[size] = x;
        size += 1;
    }
    public T getLast(){
        return items[size - 1];
    }
    public int size(){
        return size;
    }
    public T get(int i){
        return items[i];
    }
    public T removeLasdt(){
        T x = items[size];
        /**
         * for garbage collector mechanism in referance
         */
        items[size - 1] = null;
        size -= 1;
        return x;
    }
    private void _resize(){
        T[] a = (T[]) new Object[ size *2 ];
        System.arraycopy(this,0, a,0, size);
        items = a;
    }
}