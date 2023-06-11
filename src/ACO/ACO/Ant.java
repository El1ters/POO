package ACO;
import Graph.Graph;

import java.security.cert.TrustAnchor;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ant {
    Graph graph;
    OptimizerACO aco;
    private int n_ant;
    private List<Integer> curr_path = new ArrayList<>();
    private List<float[]> J;
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
        this.J = new ArrayList<>();
    }

    public int move(int curr_node){
        //Lista dos vizinhos
        List<Integer> neighbour = graph.getNeighbours(curr_node);
        float ci;
        ci = getJ(neighbour,curr_node);//primeiro elemento é o no destino e o segundo é o cijk
        System.out.println(ci);
        printCijk();
        float[] Pijk = getPijk(ci);
        Random random = new Random();
        float prob = random.nextFloat();
        return 0;
    }
    private float[] getPijk(float ci){
        float[] Pijk = new float[J.size()];
        int k = 0;
        float aux = 0;
        for(float[] i: J){
            Pijk[k] = i[1] / ci + aux;
            aux += i[1] / ci;
            System.out.println("Pijk: " + Pijk[k]);
            System.out.println("p: " + i[1]/ci);
        }
        return Pijk;
    }
    private float getJ(List<Integer> neighbour,int curr_node){
        this.J.clear();
        float cijk;
        float ci = 0;
        //Percorrer a lista ver se a formiga já passou por esse nó e adicionar à lista de nos nao percorridos
        for(Integer i : neighbour){
            if(!curr_path.contains(i)){
                cijk = (this.alpha/*+pheromone_level*/) / (this.beta + graph.getWeight(curr_node,i));
                J.add(new float[]{i,cijk});
                ci += cijk;
            }
        }
        return ci;
    }
    private void printCijk(){
        boolean isFirst;
        for(float[] k : this.J) {
            isFirst = true;
            for (float value : k) {
                System.out.println(isFirst ? "nó: " + value: "Cijk: " + value);
                isFirst = false;
            }
        }
    }
    private float ciCalculate(List<float[]> to_travel){
        float ci = 0;
        for(float[] j : to_travel) {
            ci += j[1];
        }
        return ci;
    }
}
