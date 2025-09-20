package ds;

public class Stack<T> {
    private final T[] data;
    private int top = -1;

    @SuppressWarnings("unchecked")
    public Stack(int capacity) {
        this.data = (T[]) new Object[capacity];
    }

    public void push(T item) {
        if (isFull()) {
            throw new RuntimeException("A pilha está cheia!");
        }
        top++;
        data[top] = item;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("A pilha está vazia!");
        }
        T item = data[top];
        top--;
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            return null; 
        }
        return data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == data.length - 1;
    }
}