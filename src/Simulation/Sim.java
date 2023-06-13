package Simulation;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import ACO.Ant;
import ACO.OptimizerACO;
import ACO.OptimizerSolution;

public class Sim  implements Comparator<Event> {
    //atributos
    OptimizerACO ACO_;    //               //relação direcional com classe aco
    Comparator<Event> comparator = new EventTimeComparator();
    PriorityQueue<Event> PEC;
    Evaporation evap;
    private float final_instant;
    private float instant = 0;
    int observation_number = 0;
    private int mevents = 0;
    private int eevents = 0;


    public int Sim(int final_inst, OptimizerACO ACO_) {
        this.final_instant = final_inst;
        int size_queue = ACO_.get_colony_size() + ACO_.get_n_edges();
        PEC = new PriorityQueue<Event>(size_queue, comparator);
    }

    //metodos
    public void PEC_init() {
        for (int i = 0; i < ACO_.get_colony_size(); i++) {
            Ant ant = ACO_.get_ant(i);
            AntMove ant_move = new AntMove(0, ant,this);
            PEC.add(ant_move);
        }

        for (int i = 0; i < ACO_.get_n_edges(); i++) {
            evap = new Evaporation(ACO_.calcTime());
            PEC.add(evap);
        }
    }

    public void m_increase() {
        mevents++;
    }

    public void e_increase() {
        eevents++;
    }

    public void RunSim() {
        float observation_instant = final_instant / 20;


        for (Event ev : PEC) {
            //se o evento que se vai executar passar o observation_instant, interromper e
            //imprimir imediatamente o estado atual antes continuar a simulação
//    		instant = ev.get_time_stamp();
            if (ev.get_time_stamp() >= observation_instant) {
                print_observations(observation_instant);
                observation_instant += observation_instant;
                observation_number++;
            }
            instant = ev.get_time_stamp(); //atualizar o instant antes ou depois de verificar o observation instant (ver caderno)?
            PEC.remove();
            new_ts = trigger();
            ev.update_time_stamp(new_ts);
            PEC.add(ev);

        }
    }

    private void print_observations(float observation_instant) {
        System.out.println("Observation " + observation_number + ":");
        System.out.println("Present instant: " + /*instant*/ observation_instant);
        System.out.println("Number of move events: " + mevents);
        System.out.println("Number of evaporation events: " + eevents);
        System.out.println("Top candidate cycles: ");
        ArrayList<OptimizerSolution> cycles = ACO_.get_Best_cycles();
        OptimizerSolution best = cycles.remove();
        while (cycles.size() != 0) {
            System.out.println("Best Hamiltonian cycle: " + best);

        }

    }
}
public class EventTimeComparator implements Comparator<Event> {
    public int EventTimeCompare(Event a, Event b)
    {
        return a.time_stamp.compareTo(b.time_stamp);
    }
}
