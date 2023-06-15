import ACO.OptimizerACO;
import File.ReadFile;
import Graph.Graph;
import Simulation.Sim;

import java.util.Arrays;

import Graph.AdjacencyMatrix;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String[] teste =  {"-r","5","6","1","1.0","1.0","0.2","2.0","10.0","0.5","10","100.0"};
        //String[] teste = {"-f", "D:/Utilizador/Desktop/teste.txt" };
        ReadFile file = ReadFile.getInstance();

        if(teste[0].equals("-f")){
            String aux = teste[1];
            //file = new ReadFile(aux);
            file.setData(aux);
            file.printInputParam();
        }else{
            String[] aux = Arrays.copyOfRange(teste, 1, teste.length);
            //file = new ReadFile(aux);
            file.setData(aux);
            file.printInputParam();
        }
        
        Graph r = new AdjacencyMatrix(file);
        OptimizerACO f = new OptimizerACO(file,r);
        
        Sim simulation = new Sim(file.getFinalInstant(), f);
        simulation.RunSim();
    }
}