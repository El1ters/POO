package Graph;

import java.util.ArrayList;
import java.util.List;

public class Graph implements GI {
    protected int n_nodes;

    public Graph(int n_nodes){
        this.n_nodes = n_nodes;
    }
    public int get_weight(int n,int edges){

        //alterar a funçao para o necessario
        return 0;
    }

    public List<int[]> get_neighbours(int i){
        List<int[]> neighbours = new ArrayList<>();

        return neighbours;
    }
}
