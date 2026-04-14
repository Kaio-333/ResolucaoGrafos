package utils;


public class Stack<T> {
    private Node<T> topo;

    public Stack() {
        this.topo = null;
    }

    public void push(T e) {
        Node<T> novo = new Node<>(e);
        novo.next = topo;
        topo = novo;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("A pilha esta vazia");
        }
        T valor = topo.data;
        topo = topo.next;
        return valor;
    }

    public void clear() {
        topo = null;
    }

    public boolean isEmpty() {
        return topo == null;
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("A pilha esta vazia");
        }
        return topo.data;
    }

    public static void main(String[] args) {
        Stack<String> pilha = new Stack<>();

        pilha.push("Kaio");
        pilha.push("Angelo");
        pilha.push("Eduardo");

        System.out.println("Topo: " + pilha.peek());
        System.out.println("Pop: " + pilha.pop());
        System.out.println("Novo topo: " + pilha.peek());

        System.out.println("Está vazia? " + pilha.isEmpty());
        pilha.clear();
        System.out.println("Está vazia após clear? " + pilha.isEmpty());
    }
}