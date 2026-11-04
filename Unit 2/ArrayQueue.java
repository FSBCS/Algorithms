import java.util.NoSuchElementException;
import java.util.Iterator;

public class ArrayQueue<T> implements Queue<T> {
    private static final int INITIAL_CAPACITY = 8;
    private T[] data;
    private int n;      // Number of elements in Queue
    private int first;  // Index of first element in the Queue
    private int last;   // Index of the next open slot
    
    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        data = (T[]) new Object[INITIAL_CAPACITY];
        n = 0;
        first = 0;
        last = 0;
    }

    @SuppressWarnings("unchecked")
    private void resize(int sz) {
        assert sz >= n;

        T[] temp = (T[]) new Object[sz];
        for (int i = 0; i < n; i++) {
            temp[i] = data[(first + i) % data.length];
        }

        data = temp;
        first = 0;
        last = n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public void enqueue(T item) {
        if (n == data.length) resize(data.length * 2);
        data[last++] = item;
        if (last == data.length) last = 0;
        n++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue Underflow: cannot dequeue from empty queue");
        T temp = data[first];
        data[first++] = null;
        n--;
        if (first == data.length) first = 0;
        if (n >= INITIAL_CAPACITY * 2 && n == data.length / 4) resize(data.length /2);

        return temp;
    }

    private class ArrayQueueIterator implements Iterator<T> {
        private int i;

        ArrayQueueIterator() {
            i = 0;
        }

        public boolean hasNext() {
            return i < n;
        }

        public T next() {
            T item = data[(first + i) % data.length];
            i++;
            return item;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayQueueIterator();
    }
}
