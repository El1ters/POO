package ACO;

import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Random;

import File.ReadFile;
import Graph.Graph;
import Simulation.Sim;
import Simulation.Event;


public class OptimizerACO{
    //atributes
    Comparator<OptimizerSolution> comparator = new PathWeightComparator();
    PriorityQueue<OptimizerSolution> Best_paths = new PriorityQueue<OptimizerSolution>(0, comparator);
    private ArrayList<Pheromone> pheromones;
    private ArrayList<Ant> colony;
    Sim Simulation;
    Graph graph;
    ReadFile file;
    private int colony_size;
    private int nest;
    private float gama;
    private float rho;
    private float eta;


    //metodos
    public OptimizerACO(ReadFile file, Graph graph){
        this.colony_size = file.getColony_size();
        this.nest = file.getNest();
        this.graph = graph;
        this.file = file;

        //init pheromones (rever metodo)
        for (int i = 0; i < graph.get_size(); i++) {
            for(int j = 0; j < graph.get_size(); j++) {
                if(i != j) {
                    if(graph.getWeight(i,j)!=0){
                        Pheromone edge = new Pheromone(this, i, j, 0);
                        pheromones.add(edge);
                    }
                }
            }
        }

        /*
        Ant ant = new Ant(1,1, 1, 3,graph,this);
        int next;
        next = ant.move(file.getNest());
        next = ant.move(next);
        next = ant.move(next);
        next = ant.move(next);
        next = ant.move(next);
        */

        //init colony
        for (int i = 1; i <= file.getColony_size(); i++){
            Ant ant = new Ant(i, file.getAlpha(), file.getBeta(),file.getDelta(), graph,this);
            colony.add(ant);
        }
    }


    public int update() {
        get_edge(i, j);
        update_pheromones(0, edge);
        return calc_time(this.eta);
    }


    public float calcTime(float eta){
        float mean = eta * weight;
        Random random = new Random();
        return (float) (-mean * Math.log(1 - random.nextFloat()));
    }


    public void lay_pheromones(float path_sumw){
        float amount;
        //action: 1 lay pheromones; 0 evaporate pheromones
        //evaporation
        amount = this.rho;
        if(edge_.get_ph()==0){
            edge_.evaporate(amount);
        }

    		/*for(Pheromone edge : pheromones) {
    			if(edge.ph != 0) {h
    				edge.evaporate(amount);
    			}
    		}*/


        //lay pheromones
        amount = delta * path_sumw / graph.getSumWeight();
        for(Pheromone edge : pheromones) {
            edge.add(amount);
        }
    }



    private Pheromone get_edge(int i, int j) {
        for (Pheromone edge : pheromones) {
            if(edge.is_edge(i, j) == 1) {
                return edge;
            }
        }
        return;
    }

    public void ProcessPath (ArrayList path) {
        float path_sumw = 0;

        //sum weights in path
        Iterator<OptimizerSolution> itr = path.iterator();
        while (itr.hasNext()) {
            int x = (Integer)itr.next();
            path_sumw += graph.getWeight(itr, x);
        }

        this.update_pheromones(1, path_sum);

        //compare with stored solutions
        //check if its better than any one
        if(Best_paths.size() < 6) {
            OptimizerSolution new_candidate = new OptimizerSolution(path, path_sumw);
            Best_paths.add(new_candidate);
            break;
        }
        else {
            for(OptimizerSolution p : Best_paths) {
//    			OptimizerSolution aux = peek(p);
                if(path_sumw < p.wsum) {
                    OptimizerSolution new_candidate = new OptimizerSolution(path, path_sumw);
                    Best_paths.add(new_candidate);
                    Lists list_manipulation = new Lists();
                    Best_paths = list_manipulation.remove_tail<ArrayList>(Best_paths);
                    break;
                }
            }
        }

    }




    public PriorityQueue<OptimizerSolution> get_Best_paths(){
        return Best_paths;
    }

    public float get_pheromone(int i, int j) {
        return get_edge(i, j).get_ph();
    }


    public int get_n_edges() {
        return graph.getEdges();
    }

    public int get_colony_size() {
        return this.colony_size;
    }

    public int getNest(){
        return this.nest;
    }


    public int getNodes() {
        return this.file.getNodes();
    }


    public Ant get_ant(int a){
        for (Ant ant : colony) {
            if(ant.get_ant_id() == a) return ant;
        }
        return null;
    }

    public PriorityQueue<OptimizerSolution> remove_tail(PriorityQueue<OptimizerSolution> queue) {
//		int counter = 0;
        Iterator<OptimizerSolution> itr = queue.iterator();
        while (itr.hasNext()) {
//          	OptimizerSolution temp = itr.next();
//        	counter++;
//    	   	if (counter == queue.size()) {
//    	       queue.remove(temp);
//    	    }
//        	itr = itr.next();
        }
        queue.remove(itr.next());
        return queue;
    }
}



//
//public class Lists {
//	public PriorityQueue<E> remove_tail(E queue) {
//		//ver como criar para tipo generico
//		int counter = 0;
//		Iterator<E> itr = queue.iterator();
//        while (itr.hasNext()) {
////      	OptimizerSolution temp = itr.next();
////        	counter++;
////    	   	if (counter == queue.size()) {
////    	       queue.remove(temp);
////    	    }
//        	itr = itr.next();
//        }
//        queue.remove(itr);
//        return queue;
//	}
//}

class PathWeightComparator implements Comparator<OptimizerSolution> {
    public int compare(OptimizerSolution a, OptimizerSolution b) {
        float dif = a.get_wsum() - b.get_wsum();
        return (int)dif;
    }
}