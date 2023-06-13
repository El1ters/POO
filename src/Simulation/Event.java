package Simulation;


public abstract class Event{
    //atributos
    Sim sim;
    private float time_stamp;

    //metodos
    public Event (int ts, Sim sim){
        time_stamp = ts;
//    	this.update = update;
    }

    public abstract float trigger();


    public float get_time_stamp(){
        return time_stamp;
    }

    public void update_time_stamp(int ts) {
        this.time_stamp=ts;
        return;
    }
}