package Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import File.ReadFile;
public class AdjacencyMatrix extends Graph{
    private final int[][] matrix;
    ReadFile read;
    public AdjacencyMatrix(ReadFile read){
        super(read.getNodes());
        this.read = read;
        this.matrix = read.getMatrix();
        printMatrix();
    }

    private void printMatrix(){
        for (int[] ints : this.matrix) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
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
