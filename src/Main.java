import ACO.OptimizerACO;
import File.ReadFile;
import Graph.Graph;

import java.util.Arrays;

import Graph.AdjacencyMatrix;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String[] teste =  {"java","-jar","project.jar","-r","5","6","1","1.0","1.0","0.2","2.0","10.0","0.5","200","300.0"};
        //String[] teste = {"java","-jar","project.jar","-f", "D:/Utilizador/Desktop/teste.txt" };
        ReadFile file = ReadFile.getInstance();

        if(teste[3].equals("-f")){
            String aux = teste[4];
            //file = new ReadFile(aux);
            file.setData(aux);
        }else{
            String[] aux = Arrays.copyOfRange(teste, 4, teste.length);
            //file = new ReadFile(aux);
            file.setData(aux);
        }
        Graph r = new AdjacencyMatrix(file);
        OptimizerACO f = new OptimizerACO(file,r);
    }
}