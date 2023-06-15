package Graph;
import java.util.List;


/** ---------------------------------------------------------------------------------------------------------
 * Graph:
 * Classe abstrata que representa o grafo
 ----------------------------------------------------------------------------------------------------------*/
public abstract class Graph{
    protected int n_nodes;    //Número de nós no grafo
    protected int n_edges = 0;    //Número de arestas
    protected int max_weight = 0;    //Peso máximo das arestas

    //Contrutor do grafo
    public Graph(int n_nodes){
        this.n_nodes = n_nodes;
    }

    //Método abstrado que vai buscar o peso de uma aresta dado o seu índice
    public abstract int getWeight(int n,int edges);

    //Método abstrato que cria lista dos visinhos do nó
    public abstract List<Integer> getNeighbours(int i);

    //Método protegido que define os atributos do grafo
    protected void setAttributes(int n_edges,int max_weight){
        this.max_weight = max_weight;    //Definir peso máximo do grafo
        this.n_edges = n_edges;    //Definir número de arestas do grafo
    }

    //Método que retorna o número de arestas do grafo
    public int getEdges(){
        return this.n_edges;
    }

    //Método que retorna o peso máximo do grafo
    public int getSumWeight(){
        return this.max_weight;
    }
}
