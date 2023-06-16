package ACO;

import java.util.*;

import File.ReadFile;
import Graph.Graph;
import Simulation.Sim;


/** ------------------------------------------------------------------------------------------------------------------
* OptimizerACO:
* Classe que representa uma colóna de formigas
* A formiga guarda o seu caminho percorrido agenda o seu próximo movimento
------------------------------------------------------------------------------------------------------------------- */
public class OptimizerACO{
    //Atributos da classe
    Comparator<OptimizerSolution> comparator = new PathWeightComparator();
    PriorityQueue<OptimizerSolution> Best_paths = new PriorityQueue<OptimizerSolution>(comparator);    
    private ArrayList<Pheromone> pheromones = new ArrayList<Pheromone>();    //Array das feromonas na colónia
    private ArrayList<Ant> colony = new ArrayList<Ant>();    //Array das formigas na colónia
    Sim Simulation;
    Graph graph;
    ReadFile file;
    private int colony_size;
    private int nest;
    private float gama; //Parâmetro para adicionar feromonas
    private float rho; //Parâmetro para evaporar feromonas
    private float eta; //Parâmetro para calcular a prócima evaporação

    // Método
    public OptimizerACO(ReadFile file, Graph graph){
        this.colony_size = file.getColony_size();
        this.nest = file.getNest();
        this.graph = graph;
        this.file = file;

        //Obter parâmetros necessários
        gama = file.getGama();
        rho = file.getRho();
        eta = file.getEta();

        //Inicializar feromonas
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

        //Inicializar colónia
        for (int i = 1; i <= file.getColony_size(); i++){
            Ant ant = new Ant(i, file.getAlpha(), file.getBeta(),file.getDelta(), graph,this);
            colony.add(ant);
        }
    }

    /**
    *Método que gera número aleatório e calcula o tempo com base na distribuição exponencial
    */
    public float calcTime(){
        float mean = eta;
        Random random = new Random();
        return (float) (-mean * Math.log(1 - random.nextFloat()));
    }

    /**
    * Método que coloca feromonas
    */
    public void lay_pheromones(ArrayList<Integer> path, float path_sumw){
        float amount;
        //Calcular quantidade de feromonas a serem inseridas no caminho
        amount = gama * path_sumw / graph.getSumWeight();
//      System. out. println("lay pheromones: amount = " + amount);
        /*for(int i : path) {
            System.out.print(i + "-");
        }
        System.out.println();*/
        int node1, node2;
        //System.out.println(path);
        for(int k = 0;k < path.size() - 1;k++){
            node1 = path.get(k);
            node2 = path.get(k + 1);
            //Obter a feromona corresponte à aresta entre o nó 1 e 2
            Pheromone edge = get_edge(node1, node2);
            assert edge != null;
            //Adicionar o número de feromonas obtidas ao caminho
            edge.addPH(amount);
        }
    }

    /** 
    * Método para processar o caminho descrito pela formiga 
    */
    public void ProcessPath (ArrayList<Integer> path) {
//        System. out. println("ProcessPath");
        //sum weights in path
        float path_sumw = 0;    //Inicializar o peso do caminho a 0
        //Calcular o peso do caminho
;       for (int i=0; i<path.size()-1; i++) {
            path_sumw += graph.getWeight(path.get(i), path.get(i+1));
        }
//		System. out. println("path_sumw: " + path_sumw);

        ArrayList<Integer> path_copy = new ArrayList<>(path);    //Cópia do array do caminho
        //Adicionar feromonas ao caminho
        this.lay_pheromones(path, path_sumw);

        //Inserir eventos de evaporação em cada aresta
        for(int i = 0; i<path.size()-1; i++) {
        	Pheromone edge = get_edge(path.get(i), path.get(i + 1));
        	Simulation.insert_evaporation_event(edge, calcTime());
        }
        //Comparar o caminho obtido com o guardado no melhor e substituir caso um novo melhor seja encontrado
        if(Best_paths.size() < 6) {
            //Caso o caminho seja novo adicioanar à lista dos melhores caminhos caso ainda haja espaço
            if(isNewPath(path_copy)) {
                OptimizerSolution new_candidate = new OptimizerSolution(path_copy, path_sumw);
                Best_paths.add(new_candidate);
            }
        }
        else {
            //Caso a lista de melhores caminhos esteja cheia
            for(OptimizerSolution p : Best_paths) {
//    			OptimizerSolution aux = peek(p);
                //Se o novo caminho tiver um peso menor que um caminho guardado na lista
                if(path_sumw < p.get_wsum()) {
                    if(isNewPath(p.get_path())) {
//                		System. out. println("\tshorter path found -> insert");
                        //Caso o caminho seja novo
                        OptimizerSolution new_candidate = new OptimizerSolution(path_copy, path_sumw);
                        Best_paths.add(new_candidate);
                        //Adicionar o novo caminho e remover a tail da lista
                        Best_paths = remove_tail(Best_paths);
                    }
//                    System. out. println("\ttail removed");
                    break;
                }
            }
        }
    }

    /**
    * Método para verificar se o caminho é novo entre os melhores caminhos
    */
    private boolean isNewPath(ArrayList<Integer> path){
        for(OptimizerSolution i:Best_paths){
            ArrayList<Integer> aux = i.get_path();
            //Caso o caminho já exista
            if(aux.equals(path)){
                return false;
            }
        }
        return true;
    }

    /**
    * Método para remover a tail (melhor caminho) da lista de dos melhores caminhos
    */
    public PriorityQueue<OptimizerSolution> remove_tail(PriorityQueue<OptimizerSolution> queue) {
        PriorityQueue<OptimizerSolution> copy = new PriorityQueue<>(queue);
        //Corre a copia da lista a procurar o maior
        OptimizerSolution ref = copy.remove();
        while(!copy.isEmpty()) {
            OptimizerSolution obj = copy.remove();
            //Substituir o obj com ref caso obj tenha maior peso
            if(obj.get_wsum() >= ref.get_wsum()) {
                obj = ref;
            }
        }
        //Remover a referencia do caminho da lista original
        queue.remove(ref);

        //		//reverse copy
//		PriorityQueue<OptimizerSolution> rev_queue = new PriorityQueue<>(queue);
//
//
//
//		OptimizerSolution obj = rev_queue.peek();
//		queue.remove(obj);
        return queue;
    }

    /**
    * Método que vai buscar a priority queu dos melhores caminhos
    */
    public PriorityQueue<OptimizerSolution> get_Best_paths(){
        return Best_paths;
    }

    /**
    * Método que retorna a feromona na aresta entre os nós 'i' e 'j' 
    */
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

    /**
    * Método que retorna  alista de feromonas
    */
    public ArrayList<Pheromone> get_pheromones(){
    	return pheromones;
    }

    /**
    * Método que torna a formiga com o ID indicado
    */
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

/**
* Método que compara duas soluções do OptimmizerSolution com base no seu peso
*/
class PathWeightComparator implements Comparator<OptimizerSolution> {
    public int compare(OptimizerSolution a, OptimizerSolution b) {
        if ((a.get_wsum() - b.get_wsum()) < 0){
            return -1;
        }
        if ((a.get_wsum() - b.get_wsum()) > 0){
            return 1;
        }
        else
            return 0;
    }
}
