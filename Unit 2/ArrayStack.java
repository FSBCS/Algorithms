import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayStack<T> implements Stack<T> {
    private T[] data;
    private int n;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        data = (T[]) new Object[8];
        n = 0;
    }

    private class ArrayStackIterator implements Iterator<T> {
        private int i;
        // Non-static inner class has access to data array.
        // Do not maintain array pointer (or array will loiter).
        
        ArrayStackIterator() {
            i = n - 1;
        }

        public T next() {
            if (i < 0) throw new NoSuchElementException("Iterator underflow");
            return data[i--];
        }

        public boolean hasNext() {
            return i >= 0;
        }
    }

    private void resize(int size) {
        assert size >= n;

        @SuppressWarnings("unchecked")
        T[] newData = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }

    public void push(T e) {
        if (n == data.length) resize (data.length * 2);
        data[n++] = e;
    }

    public T peek() {
        if (n == 0) {
            throw new NoSuchElementException("Underflow: Stack is empty.");
        }

        return data[n];
    }

    public T pop() {
        if (n == 0) {
            throw new NoSuchElementException("Underflow: Stack is empty.");
        }

        T e = data[--n];
        data[n] = null;  // No loitering

        if (n > 2 && n == data.length / 4) resize(data.length / 2);

        return e;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n != 0;
    }

    public Iterator<T> iterator() {
        return new ArrayStackIterator();
    }
}
