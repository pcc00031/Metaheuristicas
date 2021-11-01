package uja.meta.algoritmos.noLog;

import uja.meta.Configurador;
import static uja.meta.utils.FuncionesAux.factorizacion;
import static uja.meta.utils.FuncionesAux.intercambiaPos;
import static uja.meta.utils.FuncionesAux.visualizaVectorLog;
import uja.meta.utils.Log;

/**
 *
 * @author Pedro
 */
public class DLBitNoLogger {

    public static long DLBit(int[][] F, int[][] D, int[] vSolucion, long coste) {
        double tiempoInicial = System.nanoTime();
        int N = F.length;
        long costeMejor;
        boolean mejora;
        boolean mejoraIteraciones = true; //para ver si vDLB está lleno de 1, por lo tanto no existiria mejor movimiento
        int iteraciones = 0;
        int posInter = 0;
        int[] vDLB = new int[N];
        Configurador config = new Configurador("config.txt");

        Log.LOGGER.info("///////////////////   ALGORITMO DLBit   ///////////////////");

        for (int i = 0; i < N; i++) {
            vDLB[i] = 0;
        }

        while (iteraciones < config.getIteracionesMax() && mejoraIteraciones) {
            mejoraIteraciones = false; //para cuando vDLB esté lleno de 1
            iteraciones++;

            for (int i = posInter, ite = 0; ite < N; i++, ite++) {
                if (i == N) {
                    i = 0;
                }
                if (vDLB[i] == 0) {
                    mejora = false;

                    for (int j = i + 1; j != i; j++) {
                        if (j == N) { //por si la i es igual a F.length - 1
                            j = 0;
                        }
                        costeMejor = factorizacion(F, D, vSolucion, i, j, coste);
                        if (costeMejor < coste) {

                            intercambiaPos(vSolucion, i, j);
                            coste = costeMejor;
                            vDLB[i] = vDLB[j] = 0;
                            mejora = true;
                            mejoraIteraciones = true;
                            posInter = j;  //guardamos la posicion del intercambio
                            break; //cuando hay intercambio salimos del bucle doble
                        }
                        if (j == N - 1) { //para que la j sea circular
                            j = -1;
                        }
                    }
                    if (!mejora) {
                        vDLB[i] = 1;
                    } else {
                        break;
                    }
                }
            }
        }
        Log.LOGGER.info("Iteraciones: " + iteraciones);
        double tiempoFinal = System.nanoTime();
        Log.LOGGER.info(" - Tiempo transcurrido: " + (tiempoFinal - tiempoInicial) / 1000000 + "ms");
        return coste;
    }
}
