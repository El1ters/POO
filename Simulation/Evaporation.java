package Simulation;

import ACO.Pheromone;
/**
 *Evaporation:
 *Classe que extende a classe 'Event'
 *Representa o evento de evaporação de feromonas
*/
public class Evaporation extends Event{

    Pheromone edge;
    /**
     * Construtor
     * @param ts ts
     * @param edge referenciador para pheromona
     * @param sim  referenciador para sim
     */
    public Evaporation(float ts, Pheromone edge, Sim sim) {
        super(ts, sim);
        this.edge = edge;
    }
    /**
     * Método que é chamado quando há trigger de uma evento de evaporação de feromonas
     * @return timestamp associado ao evento
     */
    public float trigger() {
        sim.e_increase();
        if(edge.update() == -1) return -1;
        return super.get_time_stamp() + edge.update(); 
    }
    /**
     * Método que retorna a aresta associada ao movimento
     * @return edge
     */
    public Pheromone get_edge() {
    	return this.edge;
    }
}
