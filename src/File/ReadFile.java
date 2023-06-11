package File;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class ReadFile {
    private int n_nodes;
    private int weight;
    private int nest;
    private float alpha; // α
    private float delta; // δ
    private float beta; //β
    private float ee1; //η
    private float ee2;  //ρ
    private float gama; //γ
    private int colony_size; //ν
    private int[][] matrix;
    private Float final_instant; //τ
    //Construtor para gerar a matriz random
    public ReadFile(String[] args){
        this.n_nodes = Integer.parseInt(args[0]);
        this.weight = Integer.parseInt(args[1]);
        this.nest = Integer.parseInt(args[2]);
        this.alpha = Float.parseFloat(args[3]);
        this.beta = Float.parseFloat(args[4]);
        this.delta = Float.parseFloat(args[5]);
        this.ee1 = Float.parseFloat(args[6]);
        this.ee2 = Float.parseFloat(args[7]);
        this.gama = Float.parseFloat(args[8]);
        this.colony_size = Integer.parseInt(args[9]);
        this.final_instant = Float.parseFloat(args[10]);

        Random random = new Random();
        int[] cycle = new int[n_nodes];
        for(int i = 0;i < cycle.length;i++){
            cycle[i] = i + 1;
        }
        shuffleNodes(cycle);
        this.matrix = createHamiltonianPath(cycle,this.weight);
        printCycle(cycle);
        //min = n_nodes
        int max = (n_nodes * (n_nodes - 1)) / 2;
        int n_edges = random.nextInt(max - n_nodes + 1) + n_nodes; //Número total de arestas
        int edges_left = n_edges - n_nodes;
        sampleEdgesLeft(edges_left,this.weight);
        System.out.println("max: " + max + " min: " + n_nodes + " " + "n_edges: " +  n_edges);
    }
    //construtor para gerar a matriz pelo ficheiro
    public ReadFile(String file){
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            String[] tokens = data.split(" ");
            this.n_nodes = Integer.parseInt(tokens[0]);
            this.nest = Integer.parseInt(tokens[1]);
            this.alpha = Float.parseFloat(tokens[2]);
            this.beta = Float.parseFloat(tokens[3]);
            this.delta = Float.parseFloat(tokens[4]);
            this.ee1 = Float.parseFloat(tokens[5]);
            this.ee2 = Float.parseFloat(tokens[6]);
            this.gama = Float.parseFloat(tokens[7]);
            this.colony_size = Integer.parseInt(tokens[8]);
            this.final_instant = Float.parseFloat(tokens[9]);
            int[][] matrix = new int[this.n_nodes][this.n_nodes];
            int flag = 0;
            int flagc = 0;
            while (myReader.hasNext()) {
                data = myReader.next();
                if(flag == this.n_nodes){
                    flag = 0;
                    flagc++;
                }
                matrix[flagc][flag] = Integer.parseInt(data);
                flag++;
            }
            this.matrix = matrix;
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void printCycle(int [] cycle){
        for (int j = 0 ; j < cycle.length;j++) {
            System.out.print(cycle[j]);
            if (j != cycle.length - 1)
                System.out.print("-");
        }
        System.out.println();
    }
    private void shuffleNodes(int[] vec){
        // Shuffle the array
        Random random = new Random();
        for (int i = vec.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            int temp = vec[i];
            vec[i] = vec[j];
            vec[j] = temp;
        }
    }
    private int[][] createHamiltonianPath(int[] vec,int maxWeight){
        int[][] matrix = new int[vec.length][vec.length];
        Random random = new Random();
        int first = vec[0];
        int last = vec[vec.length - 1];
        int weight = random.nextInt(maxWeight) + 1;
        matrix[first - 1][last - 1] = weight;
        matrix[last - 1][first - 1] = weight;
        for(int i = 0; i < vec.length; i++){
            int current = vec[i];
            //Se existe vizinho à esquerda
            if(i > 0){
                int neighbour = vec[i - 1];
                weight = random.nextInt(maxWeight) + 1;
                matrix[current - 1][neighbour - 1] = weight;
                matrix[neighbour - 1][current - 1] = weight;
            }

            //Se existe vizinho à direita
            if(i < vec.length - 1){
                int neighbour = vec[i + 1];
                weight = random.nextInt(maxWeight) + 1;
                matrix[current - 1][neighbour - 1] = weight;
                matrix[neighbour - 1][current - 1] = weight;

            }
        }
        return  matrix;
    }
    private void sampleEdgesLeft(int edges_left,int max_weight){
        Random random = new Random();
        int size = this.n_nodes; //vai ser utiizado para o random
        while (edges_left != 0) {
            int weight = random.nextInt(max_weight) + 1;
            int aux = random.nextInt(size);
            int aux1 = random.nextInt(size);
            if(this.matrix[aux][aux1] == 0 && aux != aux1){
                System.out.println((aux+1) + " " + (aux1+1));
                this.matrix[aux][aux1] = weight;
                this.matrix[aux1][aux] = weight;
                edges_left--;
            }
        }
    }
    public int getNodes(){
        return this.n_nodes;
    }
    public int getColony_size(){
        return this.colony_size;
    }
    public int getNest(){
        return this.nest;
    }
    public int[][] getMatrix(){
        return this.matrix;
    }
}

