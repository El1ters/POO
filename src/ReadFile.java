public class ReadFile {
    private int n;
    private int weight;
    private int nest;
    private float alpha;
    private float delta;
    private float beta;
    private float ee1; //n com a perna longa η
    private float ee2;  // ró ρ
    private float gama;
    private int colony_size;
    private int final_instant;
    private String file;
    

    public ReadFile(String[] args){
        this.n = Integer.parseInt(args[0]);
        this.weight = Integer.parseInt(args[1]);
        this.nest = Integer.parseInt(args[2]);
        this.alpha = Float.parseFloat(args[3]);
        this.beta = Float.parseFloat(args[4]);
        this.delta = Float.parseFloat(args[5]);
        this.ee1 = Float.parseFloat(args[6]);
        this.ee2 = Float.parseFloat(args[7]);
        this.gama = Float.parseFloat(args[8]);
        this.colony_size = Integer.parseInt(args[9]);
        this.final_instant = Integer.parseInt(args[10]);
    }

    public ReadFile(String file){
        this.file = file;
    }
}
