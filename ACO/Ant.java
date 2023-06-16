package ACO;

import Graph.Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** 
 * Ant:
 * Classe que representa formiga a deslocar-se no grafo
 * A formiga guarda o seu caminho percorrido agenda o seu próximo movimento
*/
public class Ant {
    Graph graph;
    OptimizerACO aco;
    private int ID;
    private ArrayList<Integer> curr_path = new ArrayList<>();
    private ArrayList<float[]> J;
    /**
    *Parametro alpha, beta e delta para o evento do movimento da formiga
    */
    private float alpha;
    private float beta;
    private float delta;

    /**
     * Construtor
     * @param index diferencia as formigas
     * @param a alpha
     * @param b beta
     * @param d delta
     * @param graph referenciador para grafo
     * @param aco raferencidaor para aco
     */
    public Ant(int index,float a,float b,float d,Graph graph,OptimizerACO aco){
        this.ID = index;
        this.alpha = a;
        this.beta = b;
        this.delta = d;
        this.graph = graph;
        this.aco = aco;
         /*
         *Considerar 'nest' o ponto inicial do caminho da formiga
         *Inicialização da lista de feromonas
         */
        this.curr_path.add(aco.getNest());
        this.J = new ArrayList<>();
    }

    /**
     * Método que determina o próximo movimento da formiga (seleciona o próximo nó)
     *Vai buscar o nó atual e a sua lista de vizinhos
     *Cacula a influência da feromona
     *Escolher o nó seguinte e adicioná-lo ao caminho da formiga
     */
    private void move(){
        int curr_node = curr_path.get(curr_path.size() - 1);
        List<Integer> neighbour = graph.getNeighbours(curr_node);
        float ci;
        ci = getJ(neighbour,curr_node);
        if(ci == 0){
            int next;
            next = uniformDist(neighbour);
            this.curr_path.add(next);
        }else{
            List<float[]> P = getP(ci);
    
            int next = next_node(P);
            this.curr_path.add(next);
        }
    }

    /**
     * Verifica se a formiga percorreu todos os nós e se regressou ao nó de origem
     * Caso o ciclo de Hamilton tenha sido detetado, retornar 1
     * @return 1 caso hamlitoniano 0 caso contrario
     */
    private int hamiltonDetection(){
        int nest = curr_path.get(curr_path.size() - 1);
        return (curr_path.size() == aco.getNodes() + 1 && nest == aco.getNest()) ? 1:0;
    }

    /**
     * Método que calcula a distribuição exponencial
     * @param delta delta
     * @param weight peso da aresta
     * @return tempo da distribuiçao
     */
    public float calcTime(float delta,int weight){
        float mean = delta * weight;
        Random random = new Random();
        return (float) (-mean * Math.log(1 - random.nextFloat()));
    }

    /**
     * Método que atualiza o caminho percorrido pela formiga ao longo do mesmo
     *Encontrar a primeira e última ocorrência no caminho considerado
     *Remover nós entre a primeira e última ocorrência do nó seguinte
     */
    private void updatePath(){
        int next = curr_path.get(curr_path.size() - 1);
        int first = curr_path.indexOf(next);
        int last = curr_path.lastIndexOf(next);
        //
        if (first != -1 && last != -1) {
            curr_path.subList(first, last).clear();
        }
    }

    /**
     * Método que indica qual o próximo nó no caminho
     *Gerar um valor aleatório entre 0 e 1 - probabilidade - e comparar com cada elemento da lista P, se a probabilidade for inferior, retornar o nó i[0]
     */
    private int next_node(List<float[]> P){
        Random random = new Random();
        float prob = random.nextFloat();
        for(float[] i : P){
            if(prob < i[1])
                return (int) i[0];
        }
        return 0;
    }

    /**
     * Método que seleciona um dos vizinhos do nó através de uma distribuição uniforme
     */
    private int uniformDist(List<Integer> neighbour){
        Random random = new Random();
        //Escolher um indice random para percorrer a lista e escolher um
        int i = random.nextInt(neighbour.size());
        int aux = 0;
        for(int next : neighbour){
            if(aux == i)
                return next;
            aux++;
        }
        return 0;
    }

    /**
     * Método calcula a probabilidade comulativa de seleção do nó seguinte com base nos níveis de feromonas
     *Possui lista que armazena as probabilidade
     */
    private List<float[]> getP(float ci){
        List<float[]> P = new ArrayList<>();
        float ptotal;
        float aux = 0;
        for(float[] i: J){
            ptotal = i[1] / ci + aux;
            aux += i[1] / ci;
            P.add(new float[]{i[0],ptotal});
        }
        return P;
    }

    /**
     * Método que o nível de feromonas de um nó de forma a ser possível escolher o nó seguinte
     *Percorre a lista para ver se a formiga já passou por esse nó e adiciona à lista de nós não percorridos caso não tenha passado
     */
    private float getJ(List<Integer> neighbour,int curr_node){
        this.J.clear();
        float cijk;
        float ci = 0;
        //
        for(Integer i : neighbour){
            if(!curr_path.contains(i)){
                cijk = (this.alpha + aco.get_pheromone(curr_node, i)) / (this.beta + graph.getWeight(curr_node, i));
                J.add(new float[]{i,cijk});
                ci += cijk;
            }
        }
        return ci;
    }

    /**
     * Método que atualiza o estado da formiga
     *REaliza o movimento da formiga e chama hamiltonDetection para ver se foi encontrado o caminho hamiltoniano
     *Atualiza o caminho removendo os nós entre a primeira e a última ocorrência do nó atual
     * @return tempo do move da formiga
     */
    public float update(){
        move();
        if(hamiltonDetection() == 1){
            aco.ProcessPath(curr_path);
        }
        int curr_node = curr_path.get(curr_path.size() - 1);
        int last_seen = curr_path.get(curr_path.size() - 2);
        float time = calcTime(this.delta,graph.getWeight(last_seen,curr_node));
        updatePath();
        return time;
    }

    /**
     * Retorna ID da formiga
     * @return ID
     */
    public int getAntID(){
        return this.ID;
    }
}
