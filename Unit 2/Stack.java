public interface Stack<T> extends Iterable<T> {
    public void push(T o);
    public T peek();
    public T pop();
    public boolean isEmpty();
    public int size();
}