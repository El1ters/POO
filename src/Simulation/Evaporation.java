package Simulation;

import ACO.Pheromone;

public class Evaporation extends Event{

    Pheromone edge;

    public Evaporation(float ts, Pheromone edge, Sim sim) {
        super(ts, sim);
        this.edge = edge;
    }

    public float trigger() {
//    	System. out. println("Evaporation");
        sim.e_increase();
        if(edge.update() == -1) return -1;
        return super.get_time_stamp() + edge.update(); 
    }
    
    public Pheromone get_edge() {
    	return this.edge;
    }
}
