package ACO;
import java.util.ArrayList;
import Graph.Graph;
public class OptimizerACO {

    //Best best;                              //relação direcional com classe Best
    Graph graph;
    Ant ant;
    private int colony_size;
    private int nest;
    //private ArrayList<Pheromone> pheromones;
    private ArrayList<Ant> colony;
    private float gama;
    private float rho;
    private float eta;

    //metodos
    public int OptimizerACO(int n, int alpha, int beta, int delta, int gama, int rho, int eta,int nest){
        this.colony_size = n;
        this.nest = nest;
        //init pheromones
        /*for (Pheromone edge: pheromones) {
            if(graph.get_edge(i,j)!=-1){
                edge.i=i;
                edge.j=j;
                edge.ph=0;
            };
        }*/
        Ant ant = new Ant(1,1, 2, 3,graph,this);
        //init colony
        /*for (int i = 1; i <= n; i++){
            Ant ant = new Ant(i,alpha, beta, delta,graph,this);
            colony.add(ant);
        }*/
        return 0;
    }


    /*public ArrayList<String> update_pheromones(pheromones){
        return pheromones;
    }*/

    /*public void get_pheromones(pheromones){

    }*/
    public int getNest(){
        return this.nest;
    }
    public int sum_weights(){
        return 0;
    }

}
