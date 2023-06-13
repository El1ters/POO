package ACO;
import File.ReadFile;
import Graph.Graph;

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
    public Ant(int index,float a,float b,float d,Graph graph,OptimizerACO aco){
        this.n_ant = index;
        this.alpha = a;
        this.beta = b;
        this.delta = d;
        this.graph = graph;
        this.aco = aco;
        this.curr_path.add(aco.getNest());
        this.J = new ArrayList<>();
    }
    private void move(){
        int curr_node = curr_path.get(curr_path.size() - 1);
        //Lista dos vizinhos
        List<Integer> neighbour = graph.getNeighbours(curr_node);
        float ci;
        ci = getJ(neighbour,curr_node);//primeiro elemento é o no destino e o segundo é o Cijk
        //System.out.println("Ci:" + ci);
        //printCijk();
        if(ci == 0){
            int next;
            next = uniformDist(neighbour);
            System.out.println("escolhido: " + next);
            this.curr_path.add(next);
        }else{
            List<float[]> P = getP(ci);//primeiro elemento é o nó destino e o segundo é prob comulativa
            //printP(P);
            int next = next_node(P);
            System.out.println("Escolhido: " + next);
            this.curr_path.add(next);
        }
    }
    public float update(){
        move();
        //Chamar deteta hamilton
        if(hamiltonDetection() == 1){
            System.out.println("Hamiltoniano");
            //aco.ProcessPath(curr_path);
        }
        printPath();
        int curr_node = curr_path.get(curr_path.size() - 1);
        int last_seen = curr_path.get(curr_path.size() - 2);
        float time = calcTime(this.delta,graph.getWeight(last_seen,curr_node));
        updatePath();
        printPath();
        return time;
    }
    private int hamiltonDetection(){
        int nest = curr_path.get(curr_path.size() - 1);
        return (curr_path.size() == aco.getNodes() + 1 && nest == aco.getNest()) ? 1:0;
    }
    public float calcTime(float delta,int weight){
        float mean = delta * weight;
        Random random = new Random();
        return (float) (-mean * Math.log(1 - random.nextFloat()));
    }
    private void updatePath(){
        int next = curr_path.get(curr_path.size() - 1);
        int first = curr_path.indexOf(next);
        int last = curr_path.lastIndexOf(next);
        if (first != -1 && last != -1) {
            curr_path.subList(first, last).clear();
        }
    }
    private int next_node(List<float[]> P){
        Random random = new Random();
        float prob = random.nextFloat();
        for(float[] i : P){
            if(prob < i[1])
                return (int) i[0];
        }
        return 0;
    }
    private int uniformDist(List<Integer> neighbour){
        Random random = new Random();
        //Escolher um indice random para percorrer a lista e escolher um
        int i = random.nextInt(neighbour.size());
        int aux = 0;
        for(int next : neighbour){
            //System.out.println("no: " + next);
            if(aux == i)
                return next;
            aux++;
        }
        return 0;
    }
    private List<float[]> getP(float ci){
        List<float[]> P = new ArrayList<>();
        float ptotal,Pijk;
        float aux = 0;
        for(float[] i: J){
            Pijk = i[1] / ci;
            ptotal = i[1] / ci + aux;
            aux += i[1] / ci;
            //System.out.println("Pijk:"+Pijk);
            P.add(new float[]{i[0],ptotal});
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
                cijk = (this.alpha/*+get_pheromones()*/) / (this.beta + graph.getWeight(curr_node,i));
                J.add(new float[]{i,cijk});
                ci += cijk;
            }
        }
        return ci;
    }
    private void printCijk(){ //Dar print dos Cijk associados aos caminhos
        boolean isFirst;
        for(float[] k : this.J) {
            isFirst = true;
            for (float value : k) {
                System.out.println(isFirst ? "nó: " + value: "Cijk: " + value);
                isFirst = false;
            }
        }
    }
    private void printPath(){
        for(int i : this.curr_path)
            System.out.print(i+"-");
        System.out.println();
    }
    private void printP(List<float[]> P){ //Dar print das prob comulativas
        boolean isFirst;
        for(float[] k : P) {
            isFirst = true;
            for (float value : k) {
                System.out.println(isFirst ? "nó: " + value: "P: " + value);
                isFirst = false;
            }
        }
    }
}