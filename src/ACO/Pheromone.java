package ACO;


/** -----------------------------------------------------------------------------------------------------------------------
* Pheromone:
* 
* 
----------------------------------------------------------------------------------------------------------------------- */
public class Pheromone {
    OptimizerACO aco;
    private int i;    //Índice do primeiro nó da aresta
    private int j;    //Índice do segundo nó da aresta
    private float ph;    //Nível de feromonas da aresta

    /**
    * Inicialização dos nós e nível de feromonas da aresta
    */
    public Pheromone(OptimizerACO aco, int i, int j, int ph) {
        this.aco=aco;
        this.i=i;
        this.j=j;
        this.ph=ph;
    }
    
    /**
    * Retorna o nível de feromonas da aresta
    */
    public float get_ph() {
        return this.ph;
    }

    /**
    * Verifica se o índice do nó coincide com o índice de algum dos nós da aresta
    */
    public boolean is_edge(int i, int j) {
        return (i == this.i && j == this.j) || (i == this.j && j == this.i);
    }

    /**
    * Aumentar o número de feromonas pela quantidade indicada por 'amount'
    */
    public void addPH(float amount){
        this.ph = this.ph + amount;
    }

    /**
    * Diminuir a quantidade de feromonas e garantir que esta não fica negativa
    * Calcular e retornar o tempo para atualizar a quantidade de feromonas
    */
    public float update() {
    	float amount = aco.get_rho();
        this.ph = this.ph - amount;
        if(this.ph <= 0){
        	this.ph = 0;
          	return -1;
        }
        return	aco.calcTime();
    }
}
