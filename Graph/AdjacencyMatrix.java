package Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import File.ReadFile;

/* ---------------------------------------------------------------------------------------------------------
 AdjacencyMatrix:
 Classe que extende a classe 'Graph'
 Implementação de um grafo em forma de matriz de adjacências que possui métodos que permitem alteração das propriedades da matriz
 ----------------------------------------------------------------------------------------------------------*/
public class AdjacencyMatrix extends Graph{
    private final int[][] matrix;
    //Leitura do ficheiro de entrada e inicialização da superclasse 'Graph' com os parâmetros do ficheiro
    ReadFile read;
    public AdjacencyMatrix(ReadFile read){
        //Chama o construtor do grafo com o número de nós
        super(read.getNodes());
        this.read = read;
        this.matrix = read.getMatrix();
        //Calcular o peso total do grafo (tendo em conta todas as edges)
        getGraphWeight();
        printMatrix();
    }
    
    //Print da matrix
    private void printMatrix(){
        for (int[] ints : this.matrix) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    //Método que retorna o peso do caminho entre dois nós ('node1' e 'node2')
    public int getWeight(int node1,int node2){
        return this.matrix[node1 - 1][node2 - 1];
    }

    //Método que retorna todos os vizinhos de um dado nó num array 'neighbours'
    public List<Integer> getNeighbours(int node){
        List<Integer> neighbours = new ArrayList<>();
        //Iterar a linha da matrix do nó em questão e verificar se existe edge entre o nó em questão e o nó considerado
        for(int i = 0;i < this.matrix[0].length;i++){
            if(this.matrix[node - 1][i] != 0){
                neighbours.add(i + 1);
            }
        }
        return neighbours;
    }

    //Método que obtém o peso total de todas as edges do grafo
    private void getGraphWeight(){
        //Iterar cada linha e coluna da matrix
        for(int[] i:matrix){
            for(int j:i){
                //Acumular o peso de todas as edges
                this.max_weight += j;
                //Sempre que é encontrado no grafo uma edge, incrementar a sua contagem
                if (j != 0)
                    this.n_edges++;
            }
        }
        //Divisão por dois do número de edges obtido porque num grafo unidirecial cada uma é contada duas vezes
        this.n_edges /= 2;
        this.max_weight /= 2;
        setAttributes(n_edges,max_weight);
    }
}

