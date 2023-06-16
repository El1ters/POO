package Simulation;

/**
 *Event:
 *Classe abstrata que representa um evento no simulador
*/
public abstract class Event{
    
    Sim sim;
    private float time_stamp;

    /**
    * Construtor da classe
     * @param ts ts
     * @param sim referenciador para sim
    */
    public Event (float ts, Sim sim){
        time_stamp = ts;
        this.sim = sim;
    }

    /**
     * Método abstrato que é chamado quando um evento a chamado
     * @return timestamp associado ao evento
     */
    public abstract float trigger();
    /**
     * Método que retorna o time_stamp do evento
     * @return time stamp associado
     */
    public float get_time_stamp(){
        return time_stamp;
    }
    /**
     * Método que atualiza o time_stamp do evento
     * @param ts ts
     */
    public void update_time_stamp(float ts) {
        this.time_stamp=ts;
    }
}
