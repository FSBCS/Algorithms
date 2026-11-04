import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LinkedListStack<T> implements Stack<T> {
    private Node<T> first = null;
    private int size = 0;

    private class Node<Item> {
        Item item;
        Node<Item> next;
    }

    private class LinkedStackIterator implements Iterator<T> {
        private Node<T> current;

        private LinkedStackIterator(Node<T> n) {
            this.current = n;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            T item = current.item;
            current = current.next;
            return item;
        }
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(T e) {
        Node<T> oldFirst = first;
        first = new Node<>();
        first.item = e;
        first.next = oldFirst;

        size++;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Underflow: Stack is empty");
        }
        return first.item;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Underflow: Stack is empty");
        }

        size--;

        Node<T> oldFirst = first;
        first = first.next;
        return oldFirst.item;
    }

    public int size() {
        return size;
    }

    public Iterator<T> iterator() {
        return new LinkedStackIterator(first);
    }

    public static void main(String[] args) {
        Stack<String> stack = new LinkedListStack<>();
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String s = input.nextLine();
            if (!s.equals("-"))
                stack.push(s);
            else if (!stack.isEmpty()) {
                System.out.println(stack.pop());
            }
        }

        input.close();
    }
}
