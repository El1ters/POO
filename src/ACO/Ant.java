package ACO;
import Graph.Graph;

import javax.swing.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Ant {
    Graph graph;
    OptimizerACO aco;
    private int n_ant;
    private List<Integer> curr_path = new ArrayList<>();
    private float alpha;
    private float beta;
    private float delta;
    public Ant(int index,int a,int b,int d,Graph graph,OptimizerACO aco){
        this.n_ant = index;
        this.alpha = a;
        this.beta = b;
        this.delta = d;
        this.graph = graph;
        this.aco = aco;
        this.curr_path.add(aco.getNest());
    }

    public int move(int curr_node){
        //Lista dos vizinhos
        List<Integer> neighbour = graph.getNeighbours(curr_node);
        List<Integer> to_travel = new ArrayList<>();
        //Percorrer a lista ver se a formiga já passou por esse nó e adicionar à lista de nos nao percorridos
        for(Integer i : neighbour){
            if(neighbour.contains(i)){
                break;
            }else{
                neighbour.add(i);
            }
        }
        return 0;
    }

}
