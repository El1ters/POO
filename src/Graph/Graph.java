package Graph;
import java.util.List;

public abstract class Graph{
    protected int n_nodes;

    public Graph(int n_nodes){
        this.n_nodes = n_nodes;
    }
    public abstract int getWeight(int n,int edges);
    public abstract List<Integer> getNeighbours(int i);
}
