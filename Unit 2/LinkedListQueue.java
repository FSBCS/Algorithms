import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Scanner;

public class LinkedListQueue<T> implements Queue<T> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        private T val;
        private Node next = null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(T item) {
        Node oldLast = last;
        last = new Node();
        last.val = item;

        if (isEmpty())  first = last;
        else            oldLast.next = last;

        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue Underflow: cannot dequeue from an empty queue.");
        }

        Node oldFirst = first;
        first = first.next;

        if (isEmpty()) last = null;
        size--;

        return oldFirst.val;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue Underflow: cannot dequeue from an empty queue.");
        }

        return first.val;
    }

    public int size() {
        return size;
    }

    private class LinkedQueueIterator implements Iterator<T> {
        private Node current;

        private LinkedQueueIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            T val = current.val;
            current = current.next;
            return val;
        }

    }

    public Iterator<T> iterator() {
        return new LinkedQueueIterator();
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for(T item : this) {
            sb.append(item);
            sb.append(" -> ");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Queue<String> q = new LinkedListQueue<>();

        while (input.hasNext()) {
            String s = input.next();
            if (s.equals("-")) System.out.println(q.dequeue());
            else if (s.equals("^")) break;
            else q.enqueue(s);
        }

        System.out.println(q.size() + " items remain on the queue");
        System.out.println(q);

        input.close();
    }
}
