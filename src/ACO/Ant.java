package ACO;

import Graph.Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/** ------------------------------------------------------------------------------------------------------------------
* Ant:
* Classe que representa formiga a deslocar-se no grafo
* A formiga guarda o seu caminho percorrido agenda o seu próximo movimento
------------------------------------------------------------------------------------------------------------------- */
public class Ant {
    Graph graph;
    OptimizerACO aco;
    private int ID;
    private ArrayList<Integer> curr_path = new ArrayList<>();    //Caminho atual da formiga
    private ArrayList<float[]> J;
    private float alpha; //Parametro alpha para o evento do movimento da formiga
    private float beta; //Parametro beta para o evento do movimento da formiga
    private float delta; //Parametro delta para o evento do movimento da formiga
    public Ant(int index,float a,float b,float d,Graph graph,OptimizerACO aco){
        this.ID = index;
        this.alpha = a;
        this.beta = b;
        this.delta = d;
        this.graph = graph;
        this.aco = aco;
        this.curr_path.add(aco.getNest());  //Considerar 'nest' o ponto inicial do caminho da formiga
        this.J = new ArrayList<>();    //Inicializaçnao da lista de feromonas
    }

    // Método que determina o próximo movimento da formiga (seleciona o próximo nó)
    private void move(){
        //Ir buscar o nó atual
        int curr_node = curr_path.get(curr_path.size() - 1);
        //Obter lista dos vizinhos do nó atual
        List<Integer> neighbour = graph.getNeighbours(curr_node);
        float ci;
        //Calcular a influência da feromona - primeiro elemento é o no destino e o segundo é o Cijk
        ci = getJ(neighbour,curr_node);
        //System.out.println("Ci:" + ci);
        //printCijk();
        if(ci == 0){
            int next;
            next = uniformDist(neighbour);
//            System.out.println("escolhido: " + next);
            this.curr_path.add(next);
        }else{
            List<float[]> P = getP(ci); //primeiro elemento é o nó destino e o segundo é prob comulativa
            //printP(P);
            //Escolher, aleatoriamente, o nó seguinte e adicioná-lo ao caminho da formiga
            int next = next_node(P);
//            System.out.println("Escolhido: " + next);
            this.curr_path.add(next);
        }
    }

    // Método que verifica se a formiga percorreu todos os nós e se regressou ao nó de origem
    Caso o ciclo de Hamilton tenha sido detetado, retornar 1
    private int hamiltonDetection(){
        int nest = curr_path.get(curr_path.size() - 1);
        return (curr_path.size() == aco.getNodes() + 1 && nest == aco.getNest()) ? 1:0;
    }

    // Método que com base no parâmetro delta e peso da aresta, calcula o tempo (utiliza distribuição exponencial)
    public float calcTime(float delta,int weight){
        float mean = delta * weight;
        Random random = new Random();
        return (float) (-mean * Math.log(1 - random.nextFloat()));
    }

    // Método que atualiza o caminho percorrido pela formiga ao longo do mesmo
    private void updatePath(){
        int next = curr_path.get(curr_path.size() - 1);
        int first = curr_path.indexOf(next);
        int last = curr_path.lastIndexOf(next);
        //Encontrar a primeira e última ocorrência no caminho considerado
        if (first != -1 && last != -1) {
            //Remover entre a primeira e última ocorrência do nó seguinte
            curr_path.subList(first, last).clear();
        }
    }

    // Método que indica qual o próximo nó no caminho
    private int next_node(List<float[]> P){
        Random random = new Random();
        float prob = random.nextFloat();    //Gerar um valor aleatório entre 0 e 1 - probabilidade
        //Comparar a probabilidade com cada elemento da lista P, se a probabilidade for inferior, retornar o nó i[0]
        for(float[] i : P){
            if(prob < i[1])
                return (int) i[0];
        }
        return 0;
    }

    // Método que seleciona um dos vizinhos do nó através de uma distribuição uniforme
    private int uniformDist(List<Integer> neighbour){
        Random random = new Random();
        //Escolher um indice random para percorrer a lista e escolher um
        int i = random.nextInt(neighbour.size());
        int aux = 0;
        for(int next : neighbour){
            //System.out.println("no: " + next);
            if(aux == i)
                return next;
            aux++;
        }
        return 0;
    }

    // Método calcula a probabilidade comulativa de seleção do nó seguinte com base nos níveis de feromonas
    private List<float[]> getP(float ci){
        List<float[]> P = new ArrayList<>();    //Lista que armazena as probabilidade
        float ptotal;
//      float Pijk;
        float aux = 0;
        for(float[] i: J){
//            Pijk = i[1] / ci;
            ptotal = i[1] / ci + aux;
            aux += i[1] / ci;
            //System.out.println("Pijk:"+Pijk);
            //Adicinar o nó e a probabilidade `å lista
            P.add(new float[]{i[0],ptotal});
        }
        return P;
    }

    //Método que o nível de feromonas de um nó de forma a ser possível escolher o nó seguinte
    private float getJ(List<Integer> neighbour,int curr_node){
        this.J.clear();
        float cijk;
        float ci = 0;
        //Percorrer a lista ver se a formiga já passou por esse nó e adicionar à lista de nós não percorridos
        for(Integer i : neighbour){
            if(!curr_path.contains(i)){
                cijk = (this.alpha + aco.get_pheromone(curr_node, i)) / (this.beta + graph.getWeight(curr_node, i));
                J.add(new float[]{i,cijk});
                ci += cijk;
            }
        }
        return ci;
    }
    //    private void printCijk(){ //Dar print dos Cijk associados aos caminhos
//        boolean isFirst;
//        for(float[] k : this.J) {
//            isFirst = true;
//            for (float value : k) {
//                System.out.println(isFirst ? "nó: " + value: "Cijk: " + value);
//                isFirst = false;
//            }
//        }
//    }
//    private void printPath(){
//        for(int i : this.curr_path)
//            System.out.print(i+"-");
//        System.out.println();
//    }
//    private void printP(List<float[]> P){ //Dar print das prob comulativas
//        boolean isFirst;
//        for(float[] k : P) {
//            isFirst = true;
//            for (float value : k) {
//                System.out.println(isFirst ? "nó: " + value: "P: " + value);
//                isFirst = false;
//            }
//        }
//    }

    // Método que atualiza o estado da formiga
    public float update(){
        //Mover a formiga para o próximo nó
        move();
        //Chamar deteta hamilton para ver se o caminho já representa um caminho hamiltoniano
        if(hamiltonDetection() == 1){
//            System.out.println("Hamiltoniano");
            aco.ProcessPath(curr_path);
        }
//        printPath();
        //Ir buscar o nó atual e o último visitado no caminho
        int curr_node = curr_path.get(curr_path.size() - 1);
        int last_seen = curr_path.get(curr_path.size() - 2);
        //Calcula o tempo que a formiga vai ficar no nó atual
        float time = calcTime(this.delta,graph.getWeight(last_seen,curr_node));
        //Atualiza o caminho removendo os nós entre a primeira e a última ocorrência do nó atual
        updatePath();
//        printPath();
        return time;
    }

    //Retorna o ID da formiga
    public int getAntID(){
        return this.ID;
    }
}
