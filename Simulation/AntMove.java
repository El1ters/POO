package Simulation;

import ACO.Ant;

/**
 AntMove:
 Classe que extende a classe 'Event'
 Representa o evento do movimento de uma formiga
*/
public class AntMove extends Event{
    Ant ant;
    /**
     * Construtor da classe
     * @param ts ts
     * @param ant referenciador para formiga
     * @param sim referenciador para sim
     */
    public AntMove(float ts, Ant ant, Sim sim){
        super(ts, sim);
        this.ant = ant;
    }
    /**
     * Método chamado quando há trigger do evento de movimentação da formiga
     *Incremento da contagem de movimentos na simulação
     *Atualiza a formiga e obtém  o instante de tempo do próximo evento
     */
    public float trigger() {
        sim.m_increase();  
        return super.get_time_stamp() + ant.update();
    }
}
