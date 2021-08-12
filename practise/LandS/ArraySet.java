import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
public class ArraySet<T> implements Iterable<T>{
    private T[] items;
    private int size;
    private class ArraySetIterator implements Iterator<T> {
        private int wizPos;
        public ArraySetIterator() {
            wizPos = 0;
        }
        public boolean hasNext() {
            return wizPos < size;
        }
        public T next() {
            T returnItem = items[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }
    /**
    @Override
    public String toString() {
        // very inefficient because string is immutable.
        StringBuilder returnStringSB = new StringBuilder("{");
        for (int i = 0; i < this.size - 1; i++) {
            returnStringSB.append(items[i].toString());
            returnStringSB.append(", ");
        }
        returnStringSB.append(items[size - 1]);
        returnStringSB.append("}");
        return returnStringSB.toString();
    }
     */
    @Override
    public String toString() {
        List<String> listofItems = new ArrayList<>();
        for (T x: this) {
            listofItems.add(x.toString());
        }

        return "{" + String.join(", ", listofItems) + "}";
    }

    @Override
    public boolean equals(Object other) {
        // use Object here not T
        if (other == null) { return false; }
        if (other == this) { return true; }
        if ((other.getClass()) != this.getClass()) { return false; }
        ArraySet<T> o = (ArraySet<T>) other;
        if (o.size() != this.size()) {
            return false;
        }
        for (T item: this) {
            if (!o.contains(item)) {
                return false;
            }
            //size checked, so we can do match check from only one side
        }
        return true;
    }

    public ArraySet() {
        items = (T[]) new Object[100];
        size = 0;
    }
    public boolean contains(T x) {
        for (int i = 0; i< size; i++) {
            // == compares address of two items.
            if (x.equals(items[i])) {
                return true;
            }
        }
        return false;
    }
    public void add(T x) {
        if (x == null) {
            // deal with problems -> end program.
            throw new IllegalArgumentException("Cannot add null");
            // in real world, we catch Exceptions
        }
        if (contains(x)) {
            return;
        }
        items[size] = x;
        size += 1;
    }
    public int size() {
        return size;
    }
    public static <temp> ArraySet<temp> of(temp... stuff) {
        // variable number of args
        ArraySet<temp> returnSet = new ArraySet<temp>();
        for (temp x: stuff) {
            returnSet.add(x);
        }
        return returnSet;
    }

    public static void main(String[] args) {
        ArraySet<String> s = new ArraySet<>();
        s.add("horse");
        s.add("fish");
        s.add("house");
        s.add("fish");

        ArraySet<String> s2 = new ArraySet<>();
        s2.add("fish");
        s2.add("house");
        s2.add("horse");
        for (String sp: s) {
            System.out.print(sp + ' ');
        }
        System.out.println();
        System.out.println(s);
        System.out.println(s.equals(s2));

        ArraySet<String> asetof = ArraySet.of("hi", " I'm", "here");
        System.out.println(asetof);
    }
}
