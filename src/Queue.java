//enqueue → adiciona no final da fila (last)
//
//dequeue → remove do início da fila (first)
//
//clear → remove todos os elementos de uma vez
//
//isEmpty → verifica se não há elementos

public class Queue {
    private Node first;
    private Node last;

    public Queue() {
        this.first = null;
        this.last = null;
    }

    public void enqueue(Object e) {
        Node novo = new Node(e);
        if (isEmpty()) {
            first = last = novo;
        } else {
            last.next = novo;
            last = novo;
        }
    }

    public Object dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Fila esta vazia");
        }
        Object valor = first.data;
        first = first.next;
        if (first == null) {
            last = null;
        }
        return valor;
    }

    public void clear() {
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new RuntimeException("Fila esta vazia");
        }
        return first.data;
    }

    public static void main(String[] args) {
        Queue fila = new Queue();

        fila.enqueue("Kaio");
        fila.enqueue("Angelo");
        fila.enqueue("Eduardo");

        System.out.println("Primeiro: " + fila.peek());
        System.out.println("Dequeue: " + fila.dequeue());
        System.out.println("Novo primeiro: " + fila.peek());

        System.out.println("Está vazia? " + fila.isEmpty());
        fila.clear();
        System.out.println("Está vazia? " + fila.isEmpty());
    }

}