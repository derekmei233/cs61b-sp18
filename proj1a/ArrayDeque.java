public class ArrayDeque<T> {
    private static int initSize = 8;
    private static int rFactor = 2;
    private static int rRateReci = 4;
    private int size;
    private T[] arrayT;
    private int begin;

    /**
     * end shoud be 1 greater than the position of last elements(cpp style)
     */
    private int end;
    public ArrayDeque() {
        size = 0;
        arrayT = (T[]) new Object[initSize];
        begin = 0;
        end = size;
    }
    public ArrayDeque(ArrayDeque other) {
        T[] newAD = (T[]) new Object[other.arrayT.length];
        copyHelper(other, newAD);
        arrayT = newAD;
        size = other.size;
        begin = 0;
        end = size;
    }
    private void copyHelper(ArrayDeque source, T[] a) {
        if (source.end <= source.arrayT.length) {
            System.arraycopy(source.arrayT, source.begin, a, 0, source.end - source.begin);
        } else {
            System.arraycopy(source.arrayT, source.begin, a, 0,
                    source.arrayT.length - source.begin);
            System.arraycopy(source.arrayT, 0, a, source.arrayT.length - source.begin,
                source.end - source.arrayT.length);
        }
    }
    /** still need attention */
    private void reSize(boolean inc) {
        if (inc) {
            T[] newAD = (T[]) new Object[ arrayT.length * rFactor];
            copyHelper(this, newAD);
            arrayT = newAD;
            begin = 0;
            end = size;
        } else {
            if (arrayT.length == 8) {
                return;
            }
            T[] newAD = (T[]) new Object[ arrayT.length / rRateReci];
            copyHelper(this, newAD);
            arrayT = newAD;
            begin = 0;
            end = size;
        }
    }
    /**
     * discribe how begin and end pointer works here.
     * rule1: begin < 0   ->   begin = arrayT.length - 1
     * rule2: end = begin + size (cpp stryle)
     * hint1: begin == end  -> empty or full -> then check size
     * hint2: really index = pos - begin
     */
    private int modHelper(int x) {
        if (x < 0) {
            return x + arrayT.length;
        } else {
            return x % arrayT.length;
        }
    }
    public void addFirst(T x) {
        if (size == arrayT.length) {
            reSize(true);
        }
        begin = modHelper(begin - 1);
        arrayT[begin] = x;
        size += 1;
        end = begin + size;
    }
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T result = arrayT[begin];
        arrayT[begin] = null;
        begin = modHelper(begin + 1);
        size -= 1;
        end = begin + size;
        if (size < arrayT.length / rRateReci) {
            reSize(false);
        }
        return result;
    }
    public void addLast(T x) {
        if (size == arrayT.length) {
            reSize(true);
        }
        end += 1;
        arrayT[modHelper(end - 1)] = x;
        size += 1;
    }
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T result = arrayT[modHelper(end - 1)];
        arrayT[modHelper(end - 1)] = null;
        end -= 1;
        size -= 1;
        if (size < arrayT.length / rRateReci) {
            reSize(false);
        }
        return result;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
    public T get(int pos) {
        int realpos = modHelper(begin + pos);
        return arrayT[realpos];
    }
    public void printDeque() {
        int pos = 0;
        while (pos < size - 1) {
            System.out.print(get(pos));
            System.out.print(' ');
        }
        System.out.println();
    }
}
