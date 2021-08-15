package synthesizer;


import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;
    private class ArrayRingBufferIterator implements Iterator<T> {
        private int iterated = 0;
        private int pos = 0;
        ArrayRingBufferIterator() {
            iterated = 0;
            pos = 0;
        }
        public boolean hasNext() {
            return iterated < fillCount;
        }
        public T next() {
            if (iterated == 0) {
                pos = first;
            } else {
                pos = pinnext(pos);
            }
            iterated += 1;
            return rb[pos];
        }
    }
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }
    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.fillCount = 0;
    }
    private int pinnext(int position) {
        if (position == capacity - 1) {
            return 0;
        } else {
            return position + 1;
        }
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) throws RuntimeException {
        try {
            if (isFull()) {
                throw new RuntimeException("Ring Buffer Overflow");
            }
        } catch (RuntimeException e) {
            return;
        }
        rb[last] = x;
        last = pinnext(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T result = rb[first];
        first = pinnext(first);
        fillCount -= 1;
        return result;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        return rb[first];
    }

}
