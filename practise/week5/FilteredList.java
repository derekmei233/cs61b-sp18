import java.util.*;
import java.util.function.Predicate;

public class FilteredList<T> implements Iterable<T> {

    private class FilteredListIterator implements Iterator<T> {
        List<T> list;
        Predicate<T> pred;
        int index;
        public FilteredListIterator(List<T> pl, Predicate<T> pp) {
            list = pl;
            pred = pp;
            index = 0;
        }
        @Override
        public boolean hasNext() {
            while (index < list.size() && !pred.test(list.get(index))) {
                index += 1;
            }
            return index < list.size();
        }
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T result = list.get(index);
            index += 1;
            return result;
        }
    }

    public List<T> list;
    public Predicate<T> pred;
    public FilteredList(List<T> L, Predicate<T> filter) {
        list = L;
        pred = filter;
    }
    @Override
    public Iterator<T> iterator() {
        return new FilteredListIterator(list, pred);
    }
}
