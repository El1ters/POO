package File;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
    private int n;
    private int weight;
    private int nest;
    private float alpha;
    private float delta;
    private float beta;
    private float ee1; //η
    private float ee2;  //ρ
    private float gama;
    private int colony_size;
    private int[][] matrix;
    private Float final_instant;


    //Construtor para gerar a matriz random
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
        this.final_instant = Float.parseFloat(args[10]);
    }

    //construtor para gerar a matriz pelo ficheiro
    public ReadFile(String file){
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            String[] tokens = data.split(" ");
            this.n = Integer.parseInt(tokens[0]);
            this.nest = Integer.parseInt(tokens[1]);
            this.alpha = Float.parseFloat(tokens[2]);
            this.beta = Float.parseFloat(tokens[3]);
            this.delta = Float.parseFloat(tokens[4]);
            this.ee1 = Float.parseFloat(tokens[5]);
            this.ee2 = Float.parseFloat(tokens[6]);
            this.gama = Float.parseFloat(tokens[7]);
            this.colony_size = Integer.parseInt(tokens[8]);
            this.final_instant = Float.parseFloat(tokens[9]);
            int[][] matrix = new int[this.n][this.n];
            int flag = 0;
            int flagc = 0;
            while (myReader.hasNext()) {
                data = myReader.next();
                if(flag == this.n){
                    flag = 0;
                    flagc++;
                }
                matrix[flagc][flag] = Integer.parseInt(data);
                flag++;
            }
            this.matrix = matrix;
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public int getNodes(){
        return this.n;
    }

    public int[][] getMatrix(){
        return this.matrix;
    }
}

