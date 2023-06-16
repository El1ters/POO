package ACO;
/**
 * Pheromone:
 * Classe que representa a feromona associada á aresta na ACO
*/
public class Pheromone {
    OptimizerACO aco;
    private int i;
    private int j;
    private float ph;

    /**
     * Inicialização dos nós e nível de feromonas da aresta
     * @param aco referenciador para aco
     * @param i Índice do primeiro nó da aresta
     * @param j Índice do segundo nó da aresta
     * @param ph Nível de feromonas da aresta
     */
    public Pheromone(OptimizerACO aco, int i, int j, int ph) {
        this.aco = aco;
        this.i = i;
        this.j = j;
        this.ph = ph;
    }

    /**
     * Retorna o nível de feromonas da aresta
     * @return intensiade da pheromona
     */
    public float get_ph() {
        return this.ph;
    }
    /**
     * Verifica se o índice do nó coincide com o índice de algum dos nós da aresta
     * @param i nó 1
     * @param j nó 2
     * @return se essa aresta existe
     */
    public boolean is_edge(int i, int j) {
        return (i == this.i && j == this.j) || (i == this.j && j == this.i);
    }

    /**
     * Aumentar o número de feromonas pela quantidade indicada por 'amount'
     * @param amount quantidade de feromona a aumentar
     */
    public void addPH(float amount){
        this.ph = this.ph + amount;
    }

    /**
     * Diminuir a quantidade de feromonas e garantir que esta não fica negativa
     * Calcular e retornar o tempo para atualizar a quantidade de feromonas
     * @return -1 para evaporaçao e o tempo para o ant move
     */
    public float update() {
    	//evaporate pheromones
    	float amount = aco.get_rho();
        this.ph = this.ph - amount;
        if(this.ph <= 0){
        	this.ph = 0;
          	return -1;
        }
        return	aco.calcTime();
    }
}
