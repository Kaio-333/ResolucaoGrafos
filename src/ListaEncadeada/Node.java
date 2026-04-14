package ListaEncadeada;

public class Node{
    private int id;
    private double pesso;
    private Node proximo;

    Node (int id, double pesso){
        this.id = id;
        this.pesso = pesso;
    }
    // Metodos Get
    public int getId(){
        return id;
    }
    public double getPesso(){
        return pesso;
    }
    public Node getProximo(){
        return proximo;
    }

    public void setProximo(Node proximo){
        this.proximo = proximo;
    }
    public void  setPesso(double pesso){
        this.pesso = pesso;
    }
    public void  setId(int id){
        this.id = id;
    }
}
