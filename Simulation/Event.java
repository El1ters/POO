package Simulation;


public abstract class Event{
    //atributos
    Sim sim;
    private float time_stamp;

    //metodos
    public Event (float ts, Sim sim){
        time_stamp = ts;
        this.sim = sim;
//    	this.update = update;
    }

    public abstract float trigger();


    public float get_time_stamp(){
        return time_stamp;
    }

    public void update_time_stamp(float ts) {
        this.time_stamp=ts;
        return;
    }
}