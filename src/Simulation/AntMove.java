package Simulation;

import ACO.Ant;

public class AntMove extends Event{

    Ant ant;

    public AntMove(int ts, Ant ant, Sim sim){
        super(ts, sim);
        this.ant = ant;
    }
    public float trigger() {
        sim.m_increase();
        return super.get_time_stamp() + ant.update();
    }
}
