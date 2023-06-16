package ACO;


import java.util.ArrayList;

/**
 * OptimizerSolution:
 * Classe que representa uma solução do ACO
 * Consiste num caminho e o seu peso
*/
public class OptimizerSolution {

    /**
    * Solução: caminho e o seu peso
    */
    private ArrayList<Integer> path;
    private float wsum;
 
    /**
    * Inicializar o caminho e o peso
     * @param path caminho realizado pela formiga
     * @param wsum peso do caminho da formiga
    */
    public OptimizerSolution(ArrayList<Integer> path, float wsum) {
        this.path = path;
        this.wsum = wsum;
    }
    /**
     * Retorna o peso da soma de todo o caminho
     * @return peso do caminho
     */
    public float get_wsum() {
        return wsum;
    }
    /**
     * Retorna o caminho em forma de array
     * @return caminho da formiga
     */
    public ArrayList<Integer> get_path(){
        return path;
    }
}
