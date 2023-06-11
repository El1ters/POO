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
        //System.out.println(ci);
        //printCijk();
        List<float[]> P = getP(ci);
        printP(P);
        int next = next_node(P);
        System.out.println(next);
        return 0;
    }
    private int next_node(List<float[]> P){
        int chosen;
        Random random = new Random();
        float prob = random.nextFloat();
        System.out.println("calhas: " + prob);
        for(float[] i : P){
            if(prob < i[1])
                return (int) i[0];
        }
        return 0;
    }
    private List<float[]> getP(float ci){
        List<float[]> P = new ArrayList<>();
        float ptotal,Pijk;
        int k = 0;
        float aux = 0;
        for(float[] i: J){
            Pijk = i[1] / ci;
            ptotal = i[1] / ci + aux;
            aux += i[1] / ci;
            P.add(new float[]{i[0],ptotal});
            System.out.println(Pijk);
        }
        return P;
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
    private void printP(List<float[]> P){
        boolean isFirst;
        for(float[] k : P) {
            isFirst = true;
            for (float value : k) {
                System.out.println(isFirst ? "nó: " + value: "P: " + value);
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
