package ACO;

import java.util.ArrayList;

import File.ReadFile;
import Graph.Graph;
public class OptimizerACO {

    //Best best;                              //relação direcional com classe Best
    Graph graph;
    ReadFile file;
    private int colony_size;
    private int nest;
    //private ArrayList<Pheromone> pheromones;
    private ArrayList<Ant> colony;
    private float gama;
    private float rho;
    private float eta;

    //metodos
    public OptimizerACO(ReadFile file,Graph graph){
        this.colony_size = file.getColony_size();
        this.nest = file.getNest();
        this.graph = graph;
        this.file = file;
        //init pheromones
        /*for (Pheromone edge: pheromones) {
            if(graph.get_edge(i,j)!=-1){
                edge.i=i;
                edge.j=j;
                edge.ph=0;
            };
        }*/
        Ant ant = new Ant(1,1, 1, 3,graph,this);
        int next;
        ant.update();
        ant.update();
        ant.update();
        ant.update();
        ant.update();

        //init colony
        /*for (int i = 1; i <= n; i++){
            Ant ant = new Ant(i,alpha, beta, delta,graph,this);
            colony.add(ant);
        }*/
    }


    /*public ArrayList<String> update_pheromones(pheromones){
        return pheromones;
    }*/

    /*public void get_pheromones(pheromones){

    }*/
    public int getNodes(){
        return this.file.getNodes();
    }
    public int getNest(){
        return this.nest;
    }
    public int sum_weights(){
        return 0;
    }

}
