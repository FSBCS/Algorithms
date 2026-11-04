public class StringStack {
    int count;
    Node top;

    private class Node {
        String data;
        Node next;
    }

    public void push(String x) {
        Node oldTop = top;
        top = new Node();
        top.data = x;
        top.next = oldTop;
        count++;
    }

    public String pop() {
        Node oldTop = top;
        top = top.next;
        count--;
        return oldTop.data;
    }

    public String peek() {
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return count;
    }

    public static void main(String[] args) {
        StringStack ss = new StringStack();

        ss.push("it");
        ss.push("was");
        ss.push("the");
        ss.push("best");
        ss.push("of");
        ss.push("times");

        while (!ss.isEmpty()) {
            System.out.println(ss.pop());
        }
    }
}
