package File;
/**
 * Read:
 * Interface que contém métodos para ir buscar os parâmetros de input
*/
public interface Read {
    /**
     * getter para o final instant
     * @return final instant
     */
    float getFinalInstant();

    /**
     * getter para o número de nós
     * @return número de nós
     */
    int getNodes();

    /**
     * getter para o tamanho da colónia
     * @return tamanho da colónia
     */
    int getColony_size();

    /**
     * getter para o indíce do nest
     * @return indíce do nest
     */
    int getNest();

    /**
     * getter para alpha
     * @return alpha
     */
    float getAlpha();

    /**
     * getter para beta
     * @return beta
     */
    float getBeta();

    /**
     * getter para gama
     * @return gama
     */
    float getGama();

    /**
     * getter para delta
     * @return delta
     */
    float getDelta();

    /**
     * getter para eta
     * @return eta
     */
    float getEta();

    /**
     * getter para rho
     * @return rho
     */
    float getRho();

    /**
     * getter para a matriz
     * @return matriz
     */
    int[][] getMatrix();

    /**
     * Método para o print de inputs de entrada
     */
    void printInputParam();
}
