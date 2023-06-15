package ACO;
public class Pheromone {
    OptimizerACO aco;
    private int i;
    private int j;
    private float ph;

    public Pheromone(OptimizerACO aco, int i, int j, int ph) {
        this.aco=aco;
        this.i=i;
        this.j=j;
        this.ph=ph;
    }

    public float get_ph() {
        return this.ph;
    }

    public boolean is_edge(int i, int j) {
        return (i == this.i && j == this.j) || (i == this.j && j == this.i);
    }

    public void addPH(float amount){
        this.ph = this.ph + amount;
    }

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
