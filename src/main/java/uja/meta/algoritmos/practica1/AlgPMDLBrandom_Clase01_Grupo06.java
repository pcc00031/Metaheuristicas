package uja.meta.algoritmos.practica1;

import static uja.meta.utils.FuncionesAux.factorizacion;
import static uja.meta.utils.FuncionesAux.intercambiaPos;
import static uja.meta.utils.FuncionesAux.visualizaVectorLog;
import uja.meta.utils.Log;
import java.util.Random;
import uja.meta.Configurador;

/**
 *
 * @author Pedro
 */
public class AlgPMDLBrandom_Clase01_Grupo06 {

    public static long DLBrandom(int[][] F, int[][] D, int[] vSolucion, long coste) {
        int N = F.length;
        long costeMejor;
        boolean mejora;
        boolean mejoraIteraciones; //para ver si vDLB está lleno de 1, por lo tanto no existiria mejor movimiento
        int posInter;
        int iteraciones = 0;
        int[] vDLB = new int[N];
        Configurador config = new Configurador("config.txt");
        Random r = new Random();
        long costeAux, costeSol = coste;
        int[] vSolucionAux = new int[N];
        int[] vSolucionSol = new int[N];
        System.arraycopy(vSolucion, 0, vSolucionSol, 0, N);

        Log.LOGGER.info("///////////////////   ALGORITMO DLBrandom   ///////////////////");

        for (int k = 0; k < config.getSemillas().size(); k++) {
            double tiempoInicial = System.nanoTime();
            r.setSeed(config.getSemillas().get(k));
            mejoraIteraciones = true;
            iteraciones = 0;
            System.arraycopy(vSolucion, 0, vSolucionAux, 0, N);
            costeAux = coste;

            Log.LOGGER.info("SEMILLA: " + config.getSemillas().get(k));

            for (int i = 0; i < N; i++) {
                vDLB[i] = 0;
            }

            //le ponemos el aleatorio a la i no a la j, siempre la j empieza en 1 más que la i, la j tiene que volver a la i
            while (iteraciones < config.getIteracionesMax() && mejoraIteraciones) {
                mejoraIteraciones = false;                //para cuando vDLB esté lleno de 1
                iteraciones++;
                posInter = r.nextInt(N);           //aleatorio entre 0 y N
                Log.LOGGER.info("#################### ITERACION " + iteraciones + " ####################");

                for (int i = posInter, ite = 0; ite < N; i++, ite++) {
                    if (i == N) {
                        i = 0;
                    }
                    if (vDLB[i] == 0) {
                        mejora = false;

                        for (int j = i + 1; j != i; j++) {
                            if (j == N) {  //por si la i es igual a F.length - 1
                                j = 0;
                            }
                            costeMejor = factorizacion(F, D, vSolucionAux, i, j, costeAux);
                            if (costeMejor < costeAux) {
                                Log.LOGGER.info("INTERCAMBIO I-J: " + i + "-" + j);
                                intercambiaPos(vSolucionAux, i, j);
                                costeAux = costeMejor;
                                vDLB[i] = vDLB[j] = 0;
                                mejora = true;
                                mejoraIteraciones = true;
                                break;
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

                Log.LOGGER.info("COSTE DLB: " + costeAux);
                Log.LOGGER.info("VECTOR DLB: " + visualizaVectorLog(vSolucionAux));
                Log.LOGGER.info("                                                                      ");
            }

            if (costeAux < costeSol) {
                costeSol = costeAux;
                System.arraycopy(vSolucionAux, 0, vSolucionSol, 0, N);
            }
            double tiempoFinal = System.nanoTime();
            Log.LOGGER.info("TIEMPO TRANSCURRIDO: " + (tiempoFinal - tiempoInicial) / 1000000 + "ms");

        }
        System.arraycopy(vSolucionSol, 0, vSolucion, 0, N);

        return costeSol;
    }
}
