package ACO;

import java.util.*;

import File.ReadFile;
import Graph.Graph;
import Simulation.Sim;


public class OptimizerACO{
    //atributes
    Comparator<OptimizerSolution> comparator = new PathWeightComparator();
    PriorityQueue<OptimizerSolution> Best_paths = new PriorityQueue<OptimizerSolution>(comparator);
    private ArrayList<Pheromone> pheromones = new ArrayList<Pheromone>();
    private ArrayList<Ant> colony = new ArrayList<Ant>();
    Sim Simulation;
    Graph graph;
    ReadFile file;
    private int colony_size;
    private int nest;
    private float gama; //lay pheromones parameters
    private float rho; //evaporate pheromones parameter
    private float eta; //calculate next evaporation time parameter

    //metodos
    public OptimizerACO(ReadFile file, Graph graph){
        this.colony_size = file.getColony_size();
        this.nest = file.getNest();
        this.graph = graph;
        this.file = file;
        
        gama = file.getGama();
        rho = file.getRho();
        eta = file.getEta();

        //init pheromones (rever metodo)

        for (int i = 1; i <= file.getNodes(); i++) {
            for(int j = i; j <= file.getNodes(); j++) {
                if(i != j) {
                    if(graph.getWeight(i,j) != 0){
                        Pheromone edge = new Pheromone(this, i, j, 0);
                        pheromones.add(edge);
                    }
                }
            }
        }
        
//        System.out.println("edges:" );
//        for (Pheromone edge : pheromones) {
//        	System.out.println("level: " + edge.get_ph());
//        }

        //init colony
        for (int i = 1; i <= file.getColony_size(); i++){
            Ant ant = new Ant(i, file.getAlpha(), file.getBeta(),file.getDelta(), graph,this);
            colony.add(ant);
        }
    }
    public float calcTime(){
        float mean = eta;
        Random random = new Random();
        return (float) (-mean * Math.log(1 - random.nextFloat()));
    }
    public void lay_pheromones(ArrayList<Integer> path, float path_sumw){
        float amount;
        amount = gama * path_sumw / graph.getSumWeight();
//      System. out. println("lay pheromones: amount = " + amount);
        /*for(int i : path) {
            System.out.print(i + "-");
        }
        System.out.println();*/
        int node1, node2;
        System.out.println(path);
        for(int k = 0;k < path.size() - 1;k++){
            node1 = path.get(k);
            node2 = path.get(k + 1);
            Pheromone edge = get_edge(node1, node2);
            assert edge != null;
            edge.addPH(amount);
        }
    }
    public void ProcessPath (ArrayList<Integer> path) {
//        System. out. println("ProcessPath");
        //sum weights in path
        float path_sumw = 0;
;       for (int i=0; i<path.size()-1; i++) {
            path_sumw += graph.getWeight(path.get(i), path.get(i+1));
        }
//		System. out. println("path_sumw: " + path_sumw);
		
        this.lay_pheromones(path, path_sumw);
        
        for(int i = 0; i<path.size()-1; i++) {
        	Pheromone edge = get_edge(path.get(i), path.get(i + 1));
        	Simulation.insert_evaporation_event(edge, calcTime());
        }
        for(Pheromone i: pheromones){
            System.out.print(i.get_ph()+" ");
        }
        System.out.println();
        //compare with stored solutions
        //check if its better than any one
        if(Best_paths.size() < 6) {
            ArrayList<Integer> path_copy = new ArrayList<>(path);
            if(isNewPath(path_copy)) {
                OptimizerSolution new_candidate = new OptimizerSolution(path_copy, path_sumw);
                Best_paths.add(new_candidate);
            }
        }
        /*else {
            for(OptimizerSolution p : Best_paths) {
//    			OptimizerSolution aux = peek(p);
                if(path_sumw < p.get_wsum()) {
                    OptimizerSolution new_candidate = new OptimizerSolution(path, path_sumw);
                    Best_paths.add(new_candidate);
                    Best_paths = remove_tail(Best_paths);
                    break;
                }
            }
        }*/
    }
    private boolean isNewPath(ArrayList<Integer> path){
        for(OptimizerSolution i:Best_paths){
            ArrayList<Integer> aux = i.get_path();
            if(aux.equals(path)){
                return false;
            }
        }
        return true;
    }
	public PriorityQueue<OptimizerSolution> remove_tail(PriorityQueue<OptimizerSolution> queue) {
		//ver como criar para tipo generico
		int counter = 0;
		Iterator<OptimizerSolution> itr = queue.iterator();
	    while (itr.hasNext()){
            System.out.print("a");
	//  	OptimizerSolution temp = itr.next();
	//    	counter++;
	//	   	if (counter == queue.size()) {
	//	       queue.remove(temp);
	//	    }
//	    	itr = itr.next();
	    }
	    queue.remove(itr.next());
	    return queue;
	}

    /*public static <T> PriorityQueue<T> remove_tail(PriorityQueue<T> queue){
        Iterator<T> iterator = queue.iterator();
        T lastElement = null;
        while (iterator.hasNext()) {
            lastElement = iterator.next();
        }
        if (lastElement != null) {
            queue.remove(lastElement);
        }
        return queue;
    }*/

    public PriorityQueue<OptimizerSolution> get_Best_paths(){
        return Best_paths;
    }

    private Pheromone get_edge(int i, int j) {
        for (Pheromone edge : pheromones) {
            if(edge.is_edge(i, j)) {
                return edge;
            }
        }
        return null;
    }
    
    
    public float get_pheromone(int i, int j) {
        return get_edge(i, j).get_ph();
    }

    public int get_n_edges() {
        return graph.getEdges();
    }

    public int getNodes() {
    	return this.file.getNodes();
    }
    
    public int get_colony_size() {
        return this.colony_size;
    }

    public int getNest(){
        return this.nest;
    }

    public float get_rho() {
    	return this.rho;
    }

    //
    public ArrayList<Pheromone> get_pheromones(){
    	return pheromones;
    }

    public Ant get_ant(int id){
        for (Ant ant : colony) {
            if(ant.getAntID() == id) return ant;
        }
        return null;
    }
    
    public void setSim(Sim sim) {
    	this.Simulation = sim;
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