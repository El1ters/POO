package File;

import java.util.Arrays;

/**
 * Classe utilizada para fazer instanciar o metodo de leitura dos parametros de entrada
 */
public class Factory {
    /**
     * Construtor default
     */
    public Factory(){

    }
    /**
     * Factory:
     * Classe que uma instância de leitura para as deiferentes maneira de input
     * @param input Array de strings de input
     * @return instancia para leitura de dados
    */
    public Read createReadMethod(String[] input){
        if(input[0].equals("-f")){
            String aux = input[1];
            return new ReadFile(aux);
        }else if(input[0].equals("-r")){
            String[] aux = Arrays.copyOfRange(input, 1, input.length);
            return new ReadTerminal(aux);
        }else{
            throw new IllegalArgumentException("Invalid input");
        }
    }
}
