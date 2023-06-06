package Graph;
import java.sql.Array;
import java.util.Random;
public class AdjacencyMatrix extends Graph{
    private int[][] matrix;

    //Fazer a matriz pelo ficheiro
    public AdjacencyMatrix(int n_nodes,int[][] matrix){
        super(n_nodes);
        this.matrix = matrix;
    }

    // Fazer a matriz random
    public AdjacencyMatrix(int n_nodes,int max_weight){
        super(n_nodes);

        Random random = new Random();
        int[] cycle = new int[n_nodes];
        for(int i = 0;i < cycle.length;i++){
            cycle[i] = i + 1;
        }
        shuffleNodes(cycle);
        this.matrix = createHamiltonianPath(cycle,max_weight);
        for (int[] ints : this.matrix) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
        for (int j : cycle) {
            System.out.println(j);
        }
        //min = n_nodes
        int max = (n_nodes * (n_nodes - 1)) / 2;
        int edge_weight = random.nextInt(max_weight + 1);
        int n_edges = random.nextInt(max - n_nodes + 1) + n_nodes;
        System.out.println("max: " + max + " min: " + n_nodes + " " + "Random: " +  n_edges);
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
        int weight = random.nextInt(maxWeight + 1);
        matrix[first - 1][last - 1] = weight;
        matrix[last - 1][first - 1] = weight;
        for(int i = 0; i < vec.length; i++){
            int current = vec[i];
            //Se existe vizinho à esquerda
            if(i > 0){
                int neighbour = vec[i - 1];
                weight = random.nextInt(maxWeight + 1);
                matrix[current - 1][neighbour - 1] = weight;
                matrix[neighbour - 1][current - 1] = weight;
            }

            //Se existe vizinho à direita
            if(i < vec.length - 1){
                int neighbour = vec[i + 1];
                weight = random.nextInt(maxWeight + 1);
                matrix[current - 1][neighbour - 1] = weight;
                matrix[neighbour - 1][current - 1] = weight;

            }
        }
        return  matrix;
    }

}
