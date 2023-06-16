package ACO;


import java.util.ArrayList;


/** -----------------------------------------------------------------------------------------------------------------------
* OptimizerSolution:
* Classe que representa uma solução do ACO
* Consiste num caminho e o seu peso
----------------------------------------------------------------------------------------------------------------------- */
public class OptimizerSolution {

    private ArrayList<Integer> path;    //Caminho da solução
    private float wsum;    //Peso do caminho da solução

    public OptimizerSolution(ArrayList<Integer> path, float wsum) {
        this.path = path;    //Inicializar o caminho 
        this.wsum = wsum;    //Inicializar o peso
    }

    /**
    * Retorna o peso da soma de todo o caminho
    */
    public float get_wsum() {
        return wsum;
    }

    /**
    * Retorna o caminho em forma de array
    */
    public ArrayList<Integer> get_path(){
        return path;
    }
}
