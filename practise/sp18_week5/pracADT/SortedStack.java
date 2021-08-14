package pracADT;

import java.util.Stack;
// T extends Comparable<T> means T should be subclass of Comparable and implemented Compare or CompareTo methods
public class SortedStack<T extends Comparable<T>> {
    private Stack<T> largerstk;
    private Stack<T> smallerstk;
    public SortedStack() {
        // don't need T below
        largerstk = new Stack<>();
        smallerstk = new Stack<>();
    }
    private void pushHelper(T t) {
        if ((largerstk.empty() || largerstk.peek().compareTo(t) >= 0) &&
                (smallerstk.empty() || smallerstk.peek().compareTo(t) <= 0)) {
            largerstk.push(t);
        } else if (!largerstk.empty() && largerstk.peek().compareTo(t) < 0) {
            smallerstk.push(largerstk.pop());
            pushHelper(t);
        } else if (!smallerstk.empty() && smallerstk.peek().compareTo(t) > 0) {
            largerstk.push(smallerstk.pop());
            pushHelper(t);
        }
    }
    public void push(T t) {
        pushHelper(t);
    }
    public T poll() {
        if (smallerstk.empty() && largerstk.empty()) {
            return null;
        }
        while (!smallerstk.empty()) {
            largerstk.push(smallerstk.pop());
        }
        return largerstk.pop();
    }
}
