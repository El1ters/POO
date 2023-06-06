package Graph;

public class AdjacencyMatrix extends Graph{
    private int[][] matrix;

    //Fazer a matriz pelo ficheiro
    public AdjacencyMatrix(int n_nodes,int[][] matrix){
        super(n_nodes);
        this.matrix = matrix;
    }

    // Fazer a matriz random
    public AdjacencyMatrix(int n_nodes){
        super(n_nodes);
    }


}
