package pracADT;
import java.util.Stack;
public class DoubleStackQueue<T> implements StackQueue<T> {
    private Stack<T> stk;
    private Stack<T> temp;
    public DoubleStackQueue() {
        stk = new Stack<T>();
        temp = new Stack<T>();
    }
    @Override
    public void push(T x) {
        stk.push(x);
    }
    private T pollHelper() {
        while (!stk.empty()) {
            temp.push(stk.pop());
        }
        return temp.pop();
    }
    @Override
    public T poll() {
        if (size() == 0) {
            return null;
        }
        if (!temp.empty()) {
            return temp.pop();
        } else {
            return pollHelper();
        }
    }
    public int size() {
        return temp.size() + stk.size();
    }
}
