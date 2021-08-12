public interface OurComparable<T> {
    /** Reture -1 if this < o
     *  Reture 0 if this equals o
     *  Reture 1 if this > o
     * @param o
     * @return
     */
    public int compareTo(T o);
    // or easier substract directly.
}
