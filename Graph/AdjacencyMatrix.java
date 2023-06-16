package Graph;
import java.util.ArrayList;
import java.util.List;

import File.Read;

/**
 *AdjacencyMatrix:
 *Classe que extende a classe 'Graph'
 *Implementação de um grafo em forma de matriz de adjacências que possui métodos que permitem alteração das propriedades da matriz
*/
public class AdjacencyMatrix extends Graph{
    private final int[][] matrix;
    Read read;
    /**
     *Leitura do ficheiro de entrada e inicialização da superclasse 'Graph' com os parâmetros do ficheiro
     *Chama o construtor do grafo com o número de nós
     *Calcular o peso total do grafo (tendo em conta todas as edges)
     * @param read objeto de leitura
     */
    public AdjacencyMatrix(Read read){
        //
        super(read.getNodes());
        this.read = read;
        this.matrix = read.getMatrix();
        //
        getGraphWeight();
    }

    /**
    *Método que retorna o peso do caminho entre dois nós ('node1' e 'node2')
     * @param node2 nó 2
     * @param node1 nó 1
     * @return peso da aresta
    */
    public int getWeight(int node1,int node2){
        return this.matrix[node1 - 1][node2 - 1];
    }

    /**
    *Método que retorna todos os vizinhos de um dado nó num array 'neighbours'
    *Iterar a linha da matrix do nó em questão e verificar se existe edge entre o nó em questão e o nó considerado
     * @param node nó
     * @return lista dos vizinhos do nó
    */
    public List<Integer> getNeighbours(int node){
        List<Integer> neighbours = new ArrayList<>();
        for(int i = 0;i < this.matrix[0].length;i++){
            if(this.matrix[node - 1][i] != 0){
                neighbours.add(i + 1);
            }
        }
        return neighbours;
    }

    /**
    *Método que obtém o peso total de todas as edges do grafo
    *Iterar cada linha e coluna da matrix e acumula o peso de todas as edges
    *Sempre que é encontrado no grafo uma edge, incrementar a sua contagem
    *Divisão por dois do número de edges obtido porque num grafo unidirecial cada uma é contada duas vezes
    */
    private void getGraphWeight(){
        for(int[] i:matrix){
            for(int j:i){
                this.max_weight += j;
                if (j != 0)
                    this.n_edges++;
            }
        }
        this.n_edges /= 2;
        this.max_weight /= 2;
        setAttributes(n_edges,max_weight);
    }
}


