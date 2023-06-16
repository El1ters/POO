package Graph;
import java.util.List;

/**
 Graph:
 Classe abstrata que representa o grafo
*/
public abstract class Graph{
    /**
     * Número de nós no grafo
     */
    protected int n_nodes;
    /**
     * Número de arestas
     */
    protected int n_edges = 0;
    /**
     * Peso máximo das arestas
     */
    protected int max_weight = 0;

    /**
     * Contrutor
     * @param n_nodes número de nós no grafo
     */
    public Graph(int n_nodes){
        this.n_nodes = n_nodes;
    }
    /**
     * Método abstrato para obter peso de uma aresta
     * @param n1 nó 1
     * @param n2 nó 2
     * @return peso da aresta
     */
    public abstract int getWeight(int n1,int n2);
    /**
     * Método abstrato para obter peso de uma aresta
     * @param i obter a lista de vizinhos do nó i
     * @return lista dos nós adjacentes
     * */
    public abstract List<Integer> getNeighbours(int i);
    /**
     * Método protegido para definir os atributos de número de arestas e do seu peso
     * @param n_edges número de arestas
     * @param max_weight peso máximo do grafo
     */
    protected void setAttributes(int n_edges,int max_weight){
        this.max_weight = max_weight;
        this.n_edges = n_edges;
    }
    /**
     * Método para obter o número de arestas
     * @return número de arestas
     */
    public int getEdges(){
        return this.n_edges;
    }
    /**
     * Método para obter a soma do peso das arestas do grafo
     * @return peso maximo do grafo
     */
    public int getSumWeight(){
        return this.max_weight;
    }
}