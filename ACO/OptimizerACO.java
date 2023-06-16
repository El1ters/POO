package ACO;

import java.util.*;

import File.Read;
import Graph.Graph;
import Simulation.Sim;

/**
 * OptimizerACO:
 * Classe que representa uma colóna de formigas
 * A formiga guarda o seu caminho percorrido agenda o seu próximo movimento
*/
public class OptimizerACO{
    /**
     * Utilizado para a priority queue
     */
    Comparator<OptimizerSolution> comparator = new PathWeightComparator();
    /**
     * lista dos melhores caminhos
     */
    PriorityQueue<OptimizerSolution> Best_paths = new PriorityQueue<OptimizerSolution>(comparator);
    /**
     * Lista das pheromones
     */
    private final ArrayList<Pheromone> pheromones = new ArrayList<Pheromone>();
    /**
     * Lista da colonia das formigas
     */
    private final ArrayList<Ant> colony = new ArrayList<Ant>();    //Array das formigas na colónia
    Sim Simulation;
    Graph graph;
    Read file;
    private final int colony_size;
    private final int nest;
    /**
    *Respetivamente, parametro para adicionar feromonas, para evaporar feromonas e para calcular a prócima evaporação
    */
    private final float gama;
    private final float rho;
    private final float eta; 
 
    /**
     * Método que obtêm os parâmetros necessários e inicializa as feromonas e a colónia
     * @param file referencidor para Read
     * @param graph referenciador para grafo
     */
    public OptimizerACO(Read file, Graph graph){
        this.colony_size = file.getColony_size();
        this.nest = file.getNest();
        this.graph = graph;
        this.file = file;
        
        gama = file.getGama();
        rho = file.getRho();
        eta = file.getEta();

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
        for (int i = 1; i <= file.getColony_size(); i++){
            Ant ant = new Ant(i, file.getAlpha(), file.getBeta(),file.getDelta(), graph,this);
            colony.add(ant);
        }
    }
    /**
     *Método que gera número aleatório e calcula o tempo com base na distribuição exponencial
     * @return tempo de distribuiçao exponencial
     */
    public float calcTime(){
        Random random = new Random();
        return (float) (-eta * Math.log(1 - random.nextFloat()));
    }
    /**
     * Método que coloca feromonas
     * @param path lista do caminho efetuado
     * @param path_sumw peso do caminho
     */
    public void lay_pheromones(ArrayList<Integer> path, float path_sumw){
        float amount;
        amount = gama * path_sumw / graph.getSumWeight();
        int node1, node2;
        for(int k = 0;k < path.size() - 1;k++){
            node1 = path.get(k);
            node2 = path.get(k + 1);
            Pheromone edge = get_edge(node1, node2);
            assert edge != null;
            edge.addPH(amount);
        }
    }

    /**
    * Método wque processa o caminho da formiga
    *Calcula o peso do caminho
    *Adiciona feromonas ao caminho
    *Insere eventos de evaporação em cada aresta
     * @param path lista do caminho efetuado pela formiga
    */
    public void ProcessPath (ArrayList<Integer> path) {
        float path_sumw = 0;
;       for (int i=0; i<path.size()-1; i++) {
            path_sumw += graph.getWeight(path.get(i), path.get(i+1));
        }
        ArrayList<Integer> path_copy = new ArrayList<>(path);
        this.lay_pheromones(path, path_sumw);

        for(int i = 0; i<path.size()-1; i++) {
        	Pheromone edge = get_edge(path.get(i), path.get(i + 1));
        	Simulation.insert_evaporation_event(edge, calcTime());
        }
        /*
        *Comparar o caminho obtido com o guardado no melhor e substituir caso um novo melhor seja encontrado
        *Caso o caminho seja novo adicioanar à lista dos melhores caminhos caso ainda haja espaço
        */
        if(Best_paths.size() < 6) {
            if(isNewPath(path_copy)) {
                OptimizerSolution new_candidate = new OptimizerSolution(path_copy, path_sumw);
                Best_paths.add(new_candidate);
            }
        }
        else {
            for(OptimizerSolution p : Best_paths) {
                if(path_sumw < p.get_wsum()) {
                    if(isNewPath(p.get_path())) {
                        OptimizerSolution new_candidate = new OptimizerSolution(path_copy, path_sumw);
                        Best_paths.add(new_candidate);
                        Best_paths = remove_tail(Best_paths);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Método para verificar se o caminho é novo entre os melhores caminhos
     * @param path caminho efetuado pela formiga
     */
    private boolean isNewPath(ArrayList<Integer> path){
        for(OptimizerSolution i:Best_paths){
            ArrayList<Integer> aux = i.get_path();
            if(aux.equals(path)){
                return false;
            }
        }
        return true;
    }
    /**
     * Método para remover a tail (melhor caminho) da lista de dos melhores caminhos
     *Corre a copia da lista a procurar o maior
     * @param queue priority queue
     * @return queue atualizada
     */
    public PriorityQueue<OptimizerSolution> remove_tail(PriorityQueue<OptimizerSolution> queue) {
        PriorityQueue<OptimizerSolution> copy = new PriorityQueue<>(queue);
       
        OptimizerSolution ref = copy.remove();
        while(!copy.isEmpty()) {
            OptimizerSolution obj = copy.remove();
            if(obj.get_wsum() >= ref.get_wsum()) {
                ref = obj;
            }
        }
        queue.remove(ref);
        return queue;
    }
    /**
     * Método que vai buscar a priority queu dos melhores caminhos
     * @return priority queue dos melhores caminhos
     */
    public PriorityQueue<OptimizerSolution> get_Best_paths(){
        return Best_paths;
    }

    /**
     * Método que retorna a feromona na aresta entre os nós 'i' e 'j'
     * @param i nó i
     * @param j nó j
     * @return edge
     */
    private Pheromone get_edge(int i, int j) {
        for (Pheromone edge : pheromones) {
            if(edge.is_edge(i, j)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Função que dá a intensidade da pheromona entre o nó i e j
     * @param i nó i
     * @param j nó j
     * @return intensiadade da pheromona
     */
    public float get_pheromone(int i, int j) {
        return get_edge(i, j).get_ph();
    }

    /**
     * getter para o número de edges
     * @return número de edges
     */
    public int get_n_edges() {
        return graph.getEdges();
    }

    /**
     * getter para o número de nós
     * @return número número de nós
     */
    public int getNodes() {
    	return this.file.getNodes();
    }

    /**
     * getter para o tamanho da colónia
     * @return tamanho da colónia
     */
    public int get_colony_size() {
        return this.colony_size;
    }

    /**
     * getter para o indíce do nó
     * @return indíce no nó
     */
    public int getNest(){
        return this.nest;
    }

    /**
     * getter para rho
     * @return rho
     */
    public float get_rho() {
    	return this.rho;
    }

    /**
     * Método que retorna  alista de feromonas
     * @return pheromones
     */
    public ArrayList<Pheromone> get_pheromones(){
    	return pheromones;
    }

    /**
     * getter para a formiga associada ao ID
     * @param id id da formiag
     * @return ant
     */
    public Ant get_ant(int id){
        for (Ant ant : colony) {
            if(ant.getAntID() == id) return ant;
        }
        return null;
    }

    /**
     * setter para sim
     * @param sim referenciador de sim
     *
     */
    public void setSim(Sim sim) {
    	this.Simulation = sim;
    }
}

/**
 * Comparator utilizado para odernar com Priority Queue com base no peso do caminho
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
