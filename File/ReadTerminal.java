package File;

import java.util.Random;
/**
 * ReadTerminal:
 * Classe que executa a leitura dos parâmetros de input
 * Inicializa os parametros associados ao ficheiro e atribui-lhes um valor através dos parâmetros de input da linha de comandos
 * Dá print a matriz 'matrix' que representa o grafo
*/
public class ReadTerminal implements Read {
     /**
    *Parâmetros de entrada
    *n_nodes: número de nós
    *weight: peso da aresta
    *nest: primeiro nó
    *alpha: α
    *delta: δ
    *beta: β
    *ee1: η
    *ee2: ρ
    *gama: γ
    *colony_size: ν
    *final_instant: τ
    */
    private final int n_nodes; 
    private final int nest; 
    private final float alpha; 
    private final float delta; 
    private final float beta; 
    private final float ee1; 
    private final float ee2;  
    private final float gama; 
    private final int colony_size; 
    private final Float final_instant; 
    private final int[][] matrix;

    /**
     * Construtor
     * @param input Lista de strings para o construtor
     */
    public ReadTerminal(String[] input){
        this.n_nodes = Integer.parseInt(input[0]);
        /**
        *Atributo relativo à matriz random, diz o peso max de uma edge
        */
        int weight = Integer.parseInt(input[1]);
        this.nest = Integer.parseInt(input[2]);
        this.alpha = Float.parseFloat(input[3]);
        this.beta = Float.parseFloat(input[4]);
        this.delta = Float.parseFloat(input[5]);
        this.ee1 = Float.parseFloat(input[6]);
        this.ee2 = Float.parseFloat(input[7]);
        this.gama = Float.parseFloat(input[8]);
        this.colony_size = Integer.parseInt(input[9]);
        this.final_instant = Float.parseFloat(input[10]);

        Random random = new Random();
        int[] cycle = new int[n_nodes];
        for(int i = 0;i < cycle.length;i++){
            cycle[i] = i + 1;
        }
        shuffleNodes(cycle);
        this.matrix = createHamiltonianPath(cycle, weight);
        int max = (n_nodes * (n_nodes - 1)) / 2;
        int n_edges = random.nextInt(max - n_nodes + 1) + n_nodes; 
        int edges_left = n_edges - n_nodes;
        sampleEdgesLeft(edges_left, weight);
    }
 
    /**
     * Método que dá shuffle dos nós no array
     *O vetor é percorrido desde a sua última posição até à segunda e é escolhido um índice aleatório, j
     *Os valores da posição i e j do array 'vec' são trocados
     */
    private void shuffleNodes(int[] vec){
        Random random = new Random();
        for (int i = vec.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = vec[i];
            vec[i] = vec[j];
            vec[j] = temp;
        }
    }
    /**
     * Método que cria caminho hamiltoniano
     *Cria a matrix e objeto Random para gerar pesos aleatórios
     *Vai buscar o primeiro e último valor do vetor
     *Gera um peso entre 1 e o máximo peso possível
     */
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
            /**
            *Se existe vizinho à esquerda
            *Atribui peso à aresta entre o nó e o seu vizinho da esquerda
            */
            if(i > 0){
                int neighbour = vec[i - 1];
                weight = random.nextInt(maxWeight) + 1;
                matrix[current - 1][neighbour - 1] = weight;
                matrix[neighbour - 1][current - 1] = weight;
            }

            /**
            *Se existe vizinho à direita
            *Atribui peso à aresta entre o nó e o seu vizinho da direita
            */
            if(i < vec.length - 1){
                int neighbour = vec[i + 1];
                weight = random.nextInt(maxWeight) + 1;
                matrix[current - 1][neighbour - 1] = weight;
                matrix[neighbour - 1][current - 1] = weight;

            }
        }
        return  matrix;
    }
    /**
     * Atribui pesos aleatórios às arestas vazias
     * Peso máximo é especificado por max_weight.
     */
    private void sampleEdgesLeft(int edges_left,int max_weight){
        Random random = new Random();
        int size = this.n_nodes; 
        while (edges_left != 0) {
            int weight = random.nextInt(max_weight) + 1;
            int aux = random.nextInt(size);
            int aux1 = random.nextInt(size);
            if(this.matrix[aux][aux1] == 0 && aux != aux1){
                this.matrix[aux][aux1] = weight;
                this.matrix[aux1][aux] = weight;
                edges_left--;
            }
        }
    }
    /*
     * Print da matriz
     */
    private void printMatrix(){
        for (int[] ints : this.matrix) {
            System.out.print("\t\t");
            for (int j = 0; j < this.matrix[0].length; j++) {
                System.out.format("%-2d ",ints[j]);
            }
            System.out.println();
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
    public float getAlpha(){
        return this.alpha;
    }
    public float getBeta(){
        return  this.beta;
    }
    public float getGama(){
        return  this.gama;
    }
    public float getDelta(){
        return  this.delta;
    }
    public float getEta(){
        return  this.ee1;
    }
    public float getRho(){
        return  this.ee2;
    }
    public float getFinalInstant() {
        return final_instant;
    }
    public int[][] getMatrix(){
        return this.matrix;
    }
    /**
     * Print dos parâmetros de input
     */
    public void printInputParam(){
        System.out.println("Input Parameters:");
        System.out.format("\t\t%-6d : number of nodes in the graph\n",n_nodes);
        System.out.format("\t\t%-6d : the nest node\n",nest);
        System.out.format("\t\t%-6.1f : alpha, ant move event\n",alpha);
        System.out.format("\t\t%-6.1f : beta, ant move event\n",beta);
        System.out.format("\t\t%-6.1f : delta, ant move event\n",delta);
        System.out.format("\t\t%-6.1f : eta, pheromone evaporation event\n",ee1);
        System.out.format("\t\t%-6.1f : rho, pheromone evaporation event\n",ee2);
        System.out.format("\t\t%-6.1f : pheromone level\n",gama);
        System.out.format("\t\t%-6d : ant colony size\n",colony_size);
        System.out.format("\t\t%-6.1f : final instant\n",final_instant);
        System.out.println();
        System.out.println("with graph:");
        printMatrix();
    }
}
