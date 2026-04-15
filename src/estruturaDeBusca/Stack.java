package estruturaDeBusca;

public class Stack {
    private Node topo;

    public Stack() {
        this.topo = null;
    }

    public void push(Object e) {
        Node novo = new Node(e);
        novo.next = topo;
        topo = novo;
    }

    public Object pop() {
        if (isEmpty()) {
            throw new RuntimeException("A pilha esta vazia");
        }
        Object valor = topo.data;
        topo = topo.next;
        return valor;
    }

    public void clear() {
        topo = null;
    }

    public boolean isEmpty() {
        return topo == null;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new RuntimeException("A pilha esta vazia");
        }
        return topo.data;
    }

    public static void main(String[] args) {
        Stack pilha = new Stack();

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