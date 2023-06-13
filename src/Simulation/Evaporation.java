package Simulation;
import ACO.Pheromone;

public class Evaporation extends Event{

    Pheromone edge;

    public Evaporation(int ts, Pheromone edge, Sim sim) {
        super(ts, sim);
        this.edge = edge;
    }

    public float trigger() {
        sim.e_increase();
        if (edge.get_ph() == 0) return;
        else {
            return super.get_time_stamp() + edge.update();
        }

    }


}
