package Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class AdjacencyMatrix extends Graph{
    private final int[][] matrix;

    //Fazer a matriz pelo ficheiro
    public AdjacencyMatrix(int n_nodes,int[][] matrix){
        super(n_nodes);
        this.matrix = matrix;
    }

    //Construtor para a matriz Random
    public AdjacencyMatrix(int n_nodes,int max_weight){
        super(n_nodes);
        Random random = new Random();
        int[] cycle = new int[n_nodes];
        for(int i = 0;i < cycle.length;i++){
            cycle[i] = i + 1;
        }
        shuffleNodes(cycle);
        this.matrix = createHamiltonianPath(cycle,max_weight);
        printCycle(cycle);
        //min = n_nodes
        int max = (n_nodes * (n_nodes - 1)) / 2;
        int n_edges = random.nextInt(max - n_nodes + 1) + n_nodes; //Número total de arestas
        int edges_left = n_edges - n_nodes;
        sampleEdgesLeft(edges_left,max_weight);
        printMatrix();
        System.out.println("max: " + max + " min: " + n_nodes + " " + "n_edges: " +  n_edges);
    }
    private void sampleEdgesLeft(int edges_left,int max_weight){
        Random random = new Random();
        int size = n_nodes; //vai ser utiizado para o random
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

    private void printCycle(int [] cycle){
        for (int j = 0 ; j < cycle.length;j++) {
            System.out.print(cycle[j]);
            if (j != cycle.length - 1)
                System.out.print("-");
        }
        System.out.println();
    }
    private void printMatrix(){
        for (int[] ints : this.matrix) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
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

    public int getWeight(int node1,int node2){
        return this.matrix[node1 - 1][node2 - 1];
    }
    public List<Integer> getNeighbours(int node){
        List<Integer> neighbours = new ArrayList<>();
        for(int i = 0;i < this.matrix[0].length;i++){
            if(this.matrix[node - 1][i] != 0){
                neighbours.add(i + 1);
            }
        }
        return neighbours;
    }
}
