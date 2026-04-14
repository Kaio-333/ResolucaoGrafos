package ListaEncadeada;

public class ListaEncadeada
{
    Node lista;

    public Node getLista(){
        return lista;
    }

    public ListaEncadeada(){
        this.lista = null;
    }
    // Metodos Alciliares
    private boolean vazio(){
        return lista == null;
    }

    // Meto de imprimir
    public void imprimirLista(){
        if(vazio()){
            System.out.println("Lista vazia");
        }
        Node temp = lista;
        while(temp!= null){
            System.out.print("(" + temp.getId() +","+ temp.getPesso()+ ")" +";");
            temp = temp.getProximo();
        }
        System.out.print("\n");
    }

    // Metodo Buscar
    public boolean buscar(int id){
        if (vazio()){
            return false;
        }
        if(buscarRecurso(id, lista))return true;
        return false;
    }

    private boolean buscarRecurso(int id, Node temp){
        if (temp == null){
            return false;
        }
        if (temp.getId() != id){
            return buscarRecurso(id, temp.getProximo());
        }
        return true;
    }

    // Metodos CRUD
    public void addLista(int id, double pesso){
        Node novoNo  = new Node(id, pesso);
        if (vazio()){
            lista = novoNo;
        }else{
            Node temp = lista;
            while (temp.getProximo()!= null){
                temp = temp.getProximo();
            }
            temp.setProximo(novoNo);
        }
        System.out.println("Lista adicionada com sucesso");
    }

    public void removerLista(int id){
        if (vazio()){
            System.out.println("A Lista está Fazia");
            return;
        }
        if (buscar(id)){
            if (lista.getId() != id){
                recRemover(id, lista, lista.getProximo());
            }else{
                lista = lista.getProximo();
            }
        }
        System.out.println("Lista removida com sucesso do id: "+ id);
    }
    private Node recRemover (int id, Node anterio, Node atul)
    {
        if (atul.getId() == id){
            anterio.setProximo(atul.getProximo());
            return null;
        }
        return recRemover(id, atul, atul.getProximo());
    }

    public void updateLista(int id,  double pesso){
        if (vazio() || !buscar(id)){
            System.out.println("Lista vazia ou o Item Não existe");
            return;
        }
        recUpdate(id, pesso, lista);
        System.out.println("Lista atualizado com sucesso");
    }

    private Node recUpdate(int id, double pesso, Node temp) {
        if (temp == null) return null;

        if (temp.getId() == id) {
            temp.setPesso(pesso);
            return temp;
        }
        return recUpdate(id, pesso, temp.getProximo());
    }
}
