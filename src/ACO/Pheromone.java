package ACO;



public class Pheromone {
    OptimizerACO aco;
    private int i;
    private int j;
    private int ph;

    public Pheromone(OptimizerACO aco, int i, int j, int ph) {
        this.aco=aco;
        this.i=i;
        this.j=j;
        this.ph=ph;
    }

    public int get_ph() {
        return this.ph;
    }

    public int is_edge(int i, int j) {
        if((i == this.i && j == this.j) && (i == this.j && j == this.i)) {
            return 1;
        }
        return 0;
    }

    public void add(int amount){
        this.ph = this.ph + amount;
    }

    public void evaporate(int amount) {
        this.ph = this.ph - amount;
    }
}
