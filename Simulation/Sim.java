package Simulation;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import ACO.Ant;
import ACO.OptimizerACO;
import ACO.OptimizerSolution;
import ACO.Pheromone;

/**
 *Sim:
 *Classe representa a simulação
*/
public class Sim {

    /**
     *Atributos da classe
     */
    OptimizerACO ACO_; 
    Comparator<Event> comparator = new EventTimeComparator();
    PriorityQueue<Event> PEC;
    ArrayList<Evaporation> event_standby;
    Evaporation evap;
    private final float final_instant;
    private float instant = 0;
    private int observation_number = 1;
    private int mevents = 0;
    private int eevents = 0;


    /**
     * Construtor da classe Sim
     * @param final_inst instante final
     * @param ACO_ referenciador para OptimizerACO
     */
    public Sim(float final_inst, OptimizerACO ACO_) {
        this.ACO_ = ACO_;
        ACO_.setSim(this);
        this.final_instant = final_inst;
        int size_queue = ACO_.get_colony_size() + ACO_.get_n_edges();
        PEC = new PriorityQueue<>(size_queue, comparator);
        event_standby = new ArrayList<>(ACO_.get_n_edges());
    }

    /**
     * Método privado que a inicializa a PEC com eventos de movimentos de formigas
     */
    private void PEC_init(){
        for (int i=1; i<=ACO_.get_colony_size(); i++){
            Ant ant = ACO_.get_ant(i);
            AntMove ant_move = new AntMove(0, ant, this);
            PEC.add(ant_move);
        }
    }
    /**
     * Método que inicializa a lista de eventos de evaporização de feromonas
     */
    private void Standby_init() {
        ArrayList<Pheromone> pheromones = ACO_.get_pheromones();
        for (Pheromone edge : pheromones) {
            evap = new Evaporation(-1, edge, this);
            event_standby.add(evap);
        }
    }

    /**
     * Método que adiciona eventos de evaporização de feromonas na PEC
     *Procura o evento de evaporização na aresta
     *Remover evento da lista de espera e atualiza o tempo
     *Insere evento na PEC
     * @param edge aresta
     * @param add_time tempo a ser adicionado ao evento de evaporação
     */
    public void insert_evaporation_event(Pheromone edge, float add_time){
        for (Evaporation evap_event : event_standby) {
            Pheromone aux_edge = evap_event.get_edge();
            if (aux_edge.equals(edge)) {
                event_standby.remove(evap_event);
                float new_ts = this.instant + add_time;
                evap_event.update_time_stamp(new_ts);
                PEC.add(evap_event);
                break;
            }
        }
    }
    /**
     * Método que executa a simulação
     */
    public void RunSim(){
        float observation_period = final_instant/20;
        float observation_instant = observation_period;

        PEC_init();
        Standby_init();

        while (instant <= final_instant){
            Event aux = PEC.remove();
            instant = aux.get_time_stamp();

             /*
             *Se o evento que se vai executar passar o observation_instant, interromper e
             *Imprimir imediatamente o estado atual antes continuar a simulação
             */
            if (aux.get_time_stamp() >= observation_instant) {
                print_observations(observation_instant);
                observation_instant += observation_period;
                observation_number ++;
            }
            float new_ts = aux.trigger();
            if(new_ts == -1) {
                aux.update_time_stamp(new_ts);
                /**
                *Downcast, so Evaporation pode retornar new_ts = -1
                */
                event_standby.add((Evaporation)aux);  
            }
            else {
                aux.update_time_stamp(new_ts);
                PEC.add(aux);
            }
        }
    }

    /**
     * Método que dá print das observacnoes da simulação
     */
    private void print_observations(float observation_instant) {
        System. out. println("\n\nObservation " + observation_number + ":");
        System. out. println("\tPresent instant: " + observation_instant);
        System. out. println("\tNumber of move events: " + mevents);
        System. out. println("\tNumber of evaporation events: " + eevents);
        System. out. println("\tTop candidate cycles: ");

        PriorityQueue<OptimizerSolution> cycles = ACO_.get_Best_paths();
        PriorityQueue<OptimizerSolution> copiedQueue = new PriorityQueue<>(cycles);
        if(cycles.size() == 0) {
            System. out. println("\tBest Hamiltonian cycle: {}");
        }
        else {
            OptimizerSolution best = copiedQueue.remove();
            while(copiedQueue.size() != 0){
                OptimizerSolution aux = copiedQueue.remove();
                System. out. println("\t" + aux.get_path().toString()
                        .replace("[", "{")
                        .replace("]", "}")
                        + ":" + aux.get_wsum());
            }

            System. out. println("\tBest Hamiltonian cycle: " + best.get_path().toString()
                    .replace("[", "{")
                    .replace("]", "}")
                    + ":" + best.get_wsum());
        }

    }

    /**
     * Método que incrementa o contador de evento de movimento das formigas da simulação
     */
    public void m_increase() {
        mevents ++;
    }

    /**
     * Método que incremente o contador de eventos de evaporização de feromonas da simulação
     */
    public void e_increase() {
        eevents ++;
    }

}

 /**
 * Método que compara o time_stamp de eventos
 */
class EventTimeComparator implements Comparator<Event> {
    public int compare(Event a, Event b)
    {
        if ((a.get_time_stamp() - b.get_time_stamp()) < 0){
            return -1;
        }
        if ((a.get_time_stamp() - b.get_time_stamp()) > 0){
            return 1;
        }
        else
            return 0;

    }
}
