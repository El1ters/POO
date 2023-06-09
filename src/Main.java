import Graph.Graph;

import java.util.Arrays;

import Graph.AdjacencyMatrix;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String[] teste =  {"java","-jar","project.jar","-f","4","10","2","1","2","3","50","6","0.5","100","500"};
        ReadFile file;

        if(teste[3].equals("-r")){
            String aux = teste[4];
            file = new ReadFile(aux);
        }else{
            String[] aux = Arrays.copyOfRange(teste, 4, teste.length);
            System.out.println(aux[0] +" "+ aux[1] +" "+ aux[2]+" " + aux[3]);
            file = new ReadFile(aux);
        } 

        Graph r = new AdjacencyMatrix(4,10);
    }
}