public interface List61B<T>{
    public void addFirst(T x);
    public void addLast(T x);
    public T getFirst();
    public T getLast();
    public T removeLast();
    public T get(int i);
    public void insert(T x, int pos);
    public int size();

    default public void print(){
        for (int i = 0; i < this.size(); i+= 1){
            System.out.print(this.get(i) + " ");
        }
        System.out.println();
    }
}