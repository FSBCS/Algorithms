public interface Queue<T> extends Iterable<T> {
    public T dequeue();
    public void enqueue(T item);
    public T peek();
    public boolean isEmpty();
    public int size();
}
