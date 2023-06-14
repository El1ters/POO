package ACO;


import java.util.ArrayList;

public class OptimizerSolution {

    private ArrayList<Integer> path;
    private float wsum;

    public OptimizerSolution(ArrayList<Integer> path, float wsum) {
        this.path = path;
        for(int i:path)
            System.out.print(i + "-");
        System.out.println();
        System.out.println(wsum);
        this.wsum = wsum;
    }

    public float get_wsum() {
        return wsum;
    }

    public ArrayList<Integer> get_path(){
        return path;
    }
}
