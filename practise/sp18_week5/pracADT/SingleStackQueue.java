package pracADT;

import java.util.Stack;

public class SingleStackQueue<T> implements StackQueue<T> {
    private Stack<T> stk;
    public SingleStackQueue() {
        stk = new Stack<T>();
    }
    @Override
    public void push(T x) {
        stk.push(x);
    }
    private T pollHelper(T curr) {
        if (stk.empty()) {
            return curr;
        }
        T trig = stk.pop();
        T result = pollHelper(trig);
        stk.push(curr);
        return result;
    }
    @Override
    public T poll() {
        if (stk.empty()) {
            return null;
        }
        return pollHelper(stk.pop());
    }
}
