package File;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *ReadFile:
 *Classe que executa a leitura do ficherio de entrada
 *Inicializa os parametros associados ao ficheiro e atribui-lhes um valor através da leitura do ficheiro
 *Dá print a matriz 'matrix' que representa o grafo
*/
public class ReadFile implements Read {
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
    private int n_nodes; 
    private int nest;
    private float alpha; 
    private float delta; 
    private float beta; 
    private float ee1; 
    private float ee2;  
    private float gama; 
    private int colony_size; 
    private Float final_instant; 
    private int[][] matrix;

    /**
     *  Construtor
     * @param file string para tirar atribuir aos atributos
     */
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
        }
        /**
        *Caso nao seja encontrado um ficheiro, apresentar mensagem de erro 
        */
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     *
     * @return número de nós
     */
    public int getNodes(){
        return this.n_nodes;
    }

    /**
     *
     * @return tamanho da colónia
     */
    public int getColony_size(){
        return this.colony_size;
    }

    /**
     *
     * @return retorna o indíce do nest
     */
    public int getNest(){
        return this.nest;
    }

    /**
     *
     * @return intensidade de alfa
     */
    public float getAlpha(){
        return this.alpha;
    }

    /**
     *
     * @return intensidade de beta
     */
    public float getBeta(){
        return  this.beta;
    }

    /**
     *
     * @return intensidade de gama
     */
    public float getGama(){
        return  this.gama;
    }

    /**
     *
     * @return intensidade de delta
     */
    public float getDelta(){
        return  this.delta;
    }

    /**
     *
     * @return eta
     */
    public float getEta(){
        return  this.ee1;
    }

    /**
     *
     * @return rho
     */
    public float getRho(){
        return  this.ee2;
    }

    /**
     *
     * @return final instant
     */
    public float getFinalInstant() {
        return final_instant;
    }

    /**
     *
     * @return matriz
     */
    public int[][] getMatrix(){
        return this.matrix;
    }
    /**
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
