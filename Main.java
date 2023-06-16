import ACO.OptimizerACO;
import File.Factory;
import File.Read;
import Graph.Graph;

import Graph.AdjacencyMatrix;
import Simulation.Sim;

/**
 *Main:
 *Classe principal que inicia a execução do programa
*/
public class Main {
    public static void main(String[] args) {
        Factory factory = new Factory();
        Read file = factory.createReadMethod(args);
        /*
        *Criação do grafo através da matrix de adjacências
        *Imprime parâmetros de input do ficheiro
        *Cria o otimizador ACO através do ficheiro de input e do grafo
        *Criação do simulador com o instante final e o otimizador ACO
        *Execução do simulador
        */
        Graph r = new AdjacencyMatrix(file);
        file.printInputParam();
        OptimizerACO f = new OptimizerACO(file,r);
        Sim simulation = new Sim(file.getFinalInstant(), f); 
        simulation.RunSim();
    }
}
