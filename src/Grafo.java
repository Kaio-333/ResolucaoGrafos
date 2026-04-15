import estruturaDeBusca.Queue;
import estruturaDeBusca.Stack;
import listaEncadeada.ListaEncadeada;
import listaEncadeada.Node;

public class Grafo {
    // atributos para o dijkstra
    private static final boolean MEMBRO = true;
    private static final boolean NAOMEMBRO = false;
    private static final int INFINITO = Integer.MAX_VALUE;
    private int[] caminho;

    private ListaEncadeada[] list;
    private String[] vetor;
    public int tamanho;

    // busca Em Profundidade
    Stack[] stack;

    //busca Em largura
    Queue[] queue;

    public Grafo(int tamanho) {
        list   = new ListaEncadeada[tamanho];
        vetor  = new String[tamanho];
        this.tamanho = tamanho;
        this.caminho = new int[tamanho];
        this.stack = new Stack[tamanho];
        this.queue = new Queue[tamanho];

        for (int i = 0; i < tamanho; i++)
            list[i] = new ListaEncadeada();
    }

    public void cria_adjacencia(int i, int j, double peso) {
        list[i].addLista(j, peso);
    }

    public void remove_adjacencia(int i, int j) {
        list[i].removerLista(j);
    }

    public void seta_informacao(int i, String v) {
        vetor[i] = v;
    }

    public int adjacentes(int i, int[] adj) {
        Node temp = list[i].getLista();
        int cont = 0;
        while (temp != null) {
            adj[cont++] = temp.getId();
            temp = temp.getProximo();
        }
        return cont;
    }

    public void imprimir() {
        System.out.println("=== Lista de Adjacência ===");
        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%d (%s) -> ", i, vetor[i]);
            Node temp = list[i].getLista();
            while (temp != null) {
                System.out.printf("(%d, %.1f) ", temp.getId(), temp.getPesso());
                temp = temp.getProximo();
            }
            System.out.println();
        }
    }

    // -------------------------------
    //  TDE 2
    // -------------------------------

    public boolean[][] warshall() {
        // codigo do menor caminho
        // Monta a matriz de adjacência  a partirda lista
        boolean[][] encontrarCaminho = new boolean[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++) {
            Node temp = list[i].getLista();
            while (temp != null) {
                encontrarCaminho[i][temp.getId()] = true;
                temp = temp.getProximo();
            }
        }

        for (int k = 0; k < tamanho; k++)
            for (int i = 0; i < tamanho; i++)
                if (encontrarCaminho[i][k]) // se ambos forem true
                    for (int j = 0; j < tamanho; j++)
                        encontrarCaminho[i][j] = encontrarCaminho[i][j] || encontrarCaminho[k][j]; // aqui faz a conferencia se existe caminho

        return encontrarCaminho;
    }

    public void imprimirAlcancabilidade() {
        boolean[][] caminho = warshall();

        System.out.println("\n=== Matriz de Alcançabilidade (Warshall) ===");
        System.out.printf("%-10s", " ");
        for (int i = 0; i < tamanho; i++)
            System.out.printf("%-6s", vetor[i]);
        System.out.println();

        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%-10s", vetor[i]);
            for (int j = 0; j < tamanho; j++)
                System.out.printf("%-6s", caminho[i][j] ? "1" : "0");
            System.out.println();
        }

        System.out.println("\n=== Relatório de Alcançabilidade ===");
        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%-6s alcança: ", vetor[i]);
            boolean algum = false;
            for (int j = 0; j < tamanho; j++) {
                if (caminho[i][j] && i != j) {
                    System.out.print(vetor[j] + " ");
                    algum = true;
                }
            }
            if (!algum) System.out.print("(nenhum outro vértice)");
            System.out.println();
        }
    }

    private double dijkstra(int s, int t) {
        double[]  distancia = new double[tamanho];
        boolean[] perm      = new boolean[tamanho];
        int corrente, k = s;
        double menordist, novadist;

        // 1) Inicialização
        for (int i = 0; i < tamanho; i++) {
            perm[i]      = NAOMEMBRO;
            distancia[i] = INFINITO;
            caminho[i]   = -1;
        }
        perm[s]      = MEMBRO;  // True
        distancia[s] = 0;
        corrente     = s;
        while (corrente != t) {
            menordist = INFINITO;
            double dc = distancia[corrente]; // na primeira ex = 0

            // Percorre a lista de adjacência do vértice corrente
            Node temp = list[corrente].getLista();
            while (temp != null) {
                int    vizinho = temp.getId();
                double peso    = temp.getPesso();

                if (!perm[vizinho]) {
                    novadist = dc + peso;
                    if (novadist < distancia[vizinho]) { // alteracao na distancia
                        distancia[vizinho] = novadist;
                        caminho[vizinho]   = corrente;
                    }
                }
                temp = temp.getProximo();
            }

            // Escolhe o vértice não permanente com menor distância
            for (int i = 0; i < tamanho; i++) {
                if (!perm[i] && distancia[i] < menordist) {
                    menordist = distancia[i];
                    k = i;
                }
            }
            // Se todos os restantes são INFINITO, não há caminho
            if (menordist == INFINITO) break;
            corrente       = k;
            perm[corrente] = MEMBRO;
        }

        return distancia[t];
    }

    public void imprimriDijkstra(int s, int t) {
        double dist = dijkstra(s, t);

        System.out.println("\n=== Dijkstra: Menor Caminho ===");
        if (dist == INFINITO) {
            System.out.printf("Não existe caminho de %s até %s.%n",
                    vetor[s], vetor[t]);
            return;
        }

        System.out.printf("Distância mínima de %s até %s: %.1f%n",
                vetor[s], vetor[t], dist);

        // Reconstrói o caminho de trás para frente
        int[] rota  = new int[tamanho];
        int   idx   = 0;
        int   atual = t;
        while (atual != -1) {
            rota[idx++] = atual;
            atual = caminho[atual];
        }

        System.out.print("Rota: ");
        for (int i = idx - 1; i >= 0; i--) {
            System.out.print(vetor[rota[i]]);
            if (i > 0) System.out.print(" -> ");
        }
        System.out.println();
    }

    // ================
    //     TDE 4
    // ================


    public Stack BuscarProfundidade(int O, int D, Stack visitados) {
        // Se O == D, adiciona ao visitados e retorna
        if (O == D) {
            visitados.push(O);
            return visitados;
        }

        // Verifica se O já foi visitado
        if (!contemVertice(visitados, O)) {
            visitados.push(O);

            // Para cada vértice A adjacente a O
            int[] adj = new int[tamanho];
            int qtd = adjacentes(O, adj);

            for (int i = 0; i < qtd; i++) {
                int A = adj[i];
                Stack resultado = BuscarProfundidade(A, D, visitados);
                if (resultado != null) {
                    return resultado;
                }
            }
        }

        return null;
    }

    private boolean contemVertice(Stack visitados, int vertice) {
        // Copia os elementos para verificar sem destruir a pilha original
        Stack temp = new Stack();
        boolean encontrou = false;

        // Desempilha tudo para verificar
        while (!visitados.isEmpty()) {
            Object val = visitados.pop();
            temp.push(val);
            if ((int) val == vertice) {
                encontrou = true;
            }
        }

        // Restaura a pilha original
        while (!temp.isEmpty()) {
            visitados.push(temp.pop());
        }

        return encontrou;
    }

    public void imprimirBuscaProfundidade(int origem, int destino) {
        Stack visitados = new Stack();
        Stack resultado = BuscarProfundidade(origem, destino, visitados);

        System.out.println("\n=== Busca em Profundidade ===");
        System.out.printf("De: %s  Até: %s%n", vetor[origem], vetor[destino]);

        if (resultado == null) {
            System.out.println("Caminho não encontrado.");
            return;
        }

        // A pilha está em ordem inversa (último visitado no topo)
        // Inverte para exibir da origem ao destino
        Stack invertida = new Stack();
        while (!resultado.isEmpty()) {
            invertida.push(resultado.pop());
        }

        System.out.print("Caminho: ");
        while (!invertida.isEmpty()) {
            int v = (int) invertida.pop();
            System.out.print(vetor[v]);
            if (!invertida.isEmpty()) System.out.print(" -> ");
        }
        System.out.println();
    }


    // Buscar Em largura
}
