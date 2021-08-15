package myadt;

public class AltList<X, Y> {
    private X item;
    private AltList<Y, X> next;
    public AltList(X px, AltList<Y, X> pnext) {
        item = px;
        next = pnext;
    }
    public X get() {
        return item;
    }
    public AltList<Y, X> next() {
        return next;
    }
    // above is given
    public AltList<Y, X> pairsSwapped() {
        // we assumed legitimate AltList must have even entries.
        AltList<Y, X> result = new AltList<Y, X>(next.item, new AltList<X, Y>(item, null));
        AltList<X, Y> ptr = next.next;
        // don't point to null ptr.
        // We need to record .next and assign new AltList to .next.next again
        // to replace null at .next.next
        AltList<X, Y> resultptr = result.next;
        while (ptr != null) {
            resultptr.next = new AltList<Y, X>(ptr.next.item, new AltList<X, Y>(ptr.item, null));
            ptr = ptr.next.next;
            resultptr = resultptr.next.next;
        }
        return result;
    }
}
