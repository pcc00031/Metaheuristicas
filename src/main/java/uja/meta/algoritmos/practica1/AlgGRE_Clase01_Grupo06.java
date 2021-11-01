package uja.meta.algoritmos.practica1;

import uja.meta.utils.Log;

/**
 * @author Pedro
 */
public class AlgGRE_Clase01_Grupo06 {

    Log log = new Log();

    public static void greedy(int[] vFlujo, int[] vDistancia, int[] vSolucion) {
        double tiempoInicial = System.nanoTime();
        Long max = Long.MIN_VALUE;
        Long min = Long.MAX_VALUE;
        int k = 0, l = 0;
        int N = vFlujo.length;
        int[] auxFlujo = new int[N];
        int[] auxDist = new int[N];

        Log.LOGGER.info("///////////////////   ALGORITMO GREEDY   ///////////////////");

        for (int i = 0; i < N; i++) { //ya que necesitamos los vectores sin modificar para multiArranque
            auxFlujo[i] = vFlujo[i];
            auxDist[i] = vDistancia[i];
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (auxFlujo[j] > max) {
                    max = Long.valueOf(auxFlujo[j]); //de int a long
                    k = j;
                }
                if (auxDist[j] < min) {
                    min = Long.valueOf(auxDist[j]);
                    l = j;
                }
            }
            vSolucion[k] = l;
            auxFlujo[k] = Integer.MIN_VALUE;
            auxDist[l] = Integer.MAX_VALUE;
            max = Long.MIN_VALUE;
            min = Long.MAX_VALUE;

        }
        double tiempoFinal = System.nanoTime();
        Log.LOGGER.info("TIEMPO TRANSCURRIDO: " + (tiempoFinal - tiempoInicial) / 1000000 + "ms");
    }
}
