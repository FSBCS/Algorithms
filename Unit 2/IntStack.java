public class IntStack {
    int count;
    Node top;

    private class Node {
        int data;
        Node next;
    }

    public void push(int x) {
        Node oldTop = top;
        top = new Node();
        top.data = x;
        top.next = oldTop;
        count++;
    }

    public int pop() {
        Node oldTop = top;
        top = top.next;
        count--;
        return oldTop.data;
    }

    public int peek() {
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return count;
    }

    public static void main(String[] args) {
        IntStack istack = new IntStack();
        istack.push(1);
        istack.push(2);
        istack.push(3);
        istack.push(4);
        istack.push(5);

        while (!istack.isEmpty()) {
            System.out.println(istack.pop());
        }
    }
}
