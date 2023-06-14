package Simulation;

import ACO.Ant;

public class AntMove extends Event{

    Ant ant;

    public AntMove(float ts, Ant ant, Sim sim){
        super(ts, sim);
        this.ant = ant;
    }
    
    public float trigger() {
//    	System. out. println("Ant move");
        sim.m_increase();
        return super.get_time_stamp() + ant.update();
    }
}
