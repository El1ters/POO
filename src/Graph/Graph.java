package Graph;
import java.util.List;

public abstract class Graph{
    protected int n_nodes;    //Número de nós no grafo
    protected int n_edges = 0;    //Número de arestas
    protected int max_weight = 0;    //Peso máximo das arestas

    //Contrutor
    public Graph(int n_nodes){
        this.n_nodes = n_nodes;
    }
    public abstract int getWeight(int n,int edges);
    public abstract List<Integer> getNeighbours(int i);
    protected void setAttributes(int n_edges,int max_weight){
        this.max_weight = max_weight;
        this.n_edges = n_edges;
    }
    public int getEdges(){
        return this.n_edges;
    }
    public int getSumWeight(){
        return this.max_weight;
    }
}
