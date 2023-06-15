package Simulation;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import ACO.Ant;
import ACO.OptimizerACO;
import ACO.OptimizerSolution;
import ACO.Pheromone;

public class Sim {

    //atributos
    OptimizerACO ACO_;    //               //relação direcional com classe aco
    Comparator<Event> comparator = new EventTimeComparator();
    PriorityQueue<Event> PEC;
    ArrayList<Evaporation> event_standby;
    Evaporation evap;
    private float final_instant;
    private float instant = 0;
    private int observation_number = 1;
    private int mevents = 0;
    private int eevents = 0;


    public Sim(float final_inst, OptimizerACO ACO_) {
        this.ACO_ = ACO_;
        ACO_.setSim(this);
        this.final_instant = final_inst;
        int size_queue = ACO_.get_colony_size() + ACO_.get_n_edges();
        PEC = new PriorityQueue<Event>(size_queue, comparator);
        event_standby = new ArrayList<Evaporation>(ACO_.get_n_edges());
    }

    //metodos
    private void PEC_init(){
        for (int i=1; i<=ACO_.get_colony_size(); i++){
            Ant ant = ACO_.get_ant(i);
            AntMove ant_move = new AntMove(0, ant, this);
            PEC.add(ant_move);
        }
    }

    private void Standby_init() {
        ArrayList<Pheromone> pheromones = ACO_.get_pheromones();
        for (Pheromone edge : pheromones) {
            evap = new Evaporation(-1, edge, this);
            event_standby.add(evap);
        }
    }


    public void insert_evaporation_event(Pheromone edge, float add_time){
        //search evaporation event by edge
        for (Evaporation evap_event : event_standby) {
            Pheromone aux_edge = evap_event.get_edge();
            if (aux_edge.equals(edge)) {
                //remove from standby
                event_standby.remove(evap_event);
                //update time = this.instant + new_time
                float new_ts = this.instant + add_time;
                ((Event)evap_event).update_time_stamp(new_ts);
                //insert in PEC
                PEC.add(evap_event);
                break;
            }
        }
    }



    public void RunSim(){
        float observation_period = final_instant/20;
        float observation_instant = observation_period;

//        System. out. println("Observation instant: " + observation_instant);
//        System. out. println("Observation period: " + observation_period);

        PEC_init();
        Standby_init();

//        System. out. println("PEC: " + PEC);
//        System. out. println("Standby: " + event_standby);

        while (instant <= final_instant){
            Event aux = PEC.remove();
//        	System. out. println("Event pop -> time_stamp: " + aux.get_time_stamp());
//            System. out. println("Number of move events: " + mevents);
//            System. out. println("Number of evaporation events: " + eevents);
            //atualizar o instant antes ou depois de verificar o observation instant (ver caderno)?
            instant = aux.get_time_stamp();
            //se o evento que se vai executar passar o observation_instant, interromper e
            //imprimir imediatamente o estado atual antes continuar a simulação

            if (aux.get_time_stamp() >= observation_instant) {
                print_observations(observation_instant);
                observation_instant += observation_period;
                observation_number ++;
            }
            float new_ts = aux.trigger();
            if(new_ts == -1) {
                aux.update_time_stamp(new_ts);
                event_standby.add((Evaporation)aux);  //downcast, so Evaporation pode retornar new_ts = -1
            }
            else {
                aux.update_time_stamp(new_ts);
                PEC.add(aux);
            }
            for(Event i: PEC)
                System.out.print(i.get_time_stamp()+ " ");
            System.out.println();
        }
    }

    private void print_observations(float observation_instant) {

        System. out. println("\n\nObservation " + observation_number + ":");
        System. out. println("\tPresent instant: " + /*instant*/ observation_instant);
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

    public void m_increase() {
        mevents ++;
    }

    public void e_increase() {
        eevents ++;
    }

}

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