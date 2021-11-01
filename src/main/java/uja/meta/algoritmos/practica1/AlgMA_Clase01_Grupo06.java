package uja.meta.algoritmos.practica1;

import static uja.meta.utils.FuncionesAux.factorizacion;
import static uja.meta.utils.FuncionesAux.funcionCoste;
import static uja.meta.utils.FuncionesAux.intercambiaPos;
import static uja.meta.utils.FuncionesAux.masIntercambios;
import static uja.meta.utils.FuncionesAux.mayorFlujo;
import static uja.meta.utils.FuncionesAux.menorDist;
import static uja.meta.utils.FuncionesAux.menosIntercambios;
import static uja.meta.utils.FuncionesAux.visualizaVectorLog;
import uja.meta.utils.Log;
import uja.meta.utils.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import uja.meta.Configurador;

/**
 *
 * @author Carlos
 */
public class AlgMA_Clase01_Grupo06 {

    public static void greedyAleatorizado(int[][] F, int[][] D, int[] vFlujo, int[] vDistancia, int[] vSolGreAle) {

        Configurador config = new Configurador("config.txt");
        int N = F.length;
        boolean[] marcadosFlujo = new boolean[N];
        boolean[] marcadosDist = new boolean[N];
        Arrays.fill(marcadosFlujo, false); //inicializar el vector lleno de ceros
        Arrays.fill(marcadosDist, false);
        int[] posMayoresFlujo = new int[config.getCandidatosGRASP()];
        int[] posMenoresDist = new int[config.getCandidatosGRASP()];
        Random r = new Random();
        int posAux;
        int mayorF;
        int menorD;
        int cuantosQuedan;                 //variable para indicar cuantas localizaciones/unidades quedan por asignar para terminar una solucion candidata

        for (int i = 0; i < N; i++) {
            if (N - i >= 5) {
                cuantosQuedan = 5;
            } else {
                cuantosQuedan = N - i;
            }

            //cogemos los 5 mayores flujos y 5 menores distancias
            for (int j = 0; j < cuantosQuedan; j++) {
                posMayoresFlujo[j] = mayorFlujo(vFlujo, marcadosFlujo);
                posMenoresDist[j] = menorDist(vDistancia, marcadosDist);
            }

            //seleccionamos aleatoriamente 1 de los mayores flujos y los demás los desmarcamos para poder cogerlos en la siguiente iteracion
            posAux = r.nextInt(cuantosQuedan);  //aleatorio entre 0 y los que queden por asignar 
            mayorF = posMayoresFlujo[posAux];
            for (int j = 0; j < cuantosQuedan; j++) {
                if (posMayoresFlujo[j] != mayorF) {
                    marcadosFlujo[posMayoresFlujo[j]] = false;
                }
            }
            //lo mismo pero con las menores distancias
            posAux = r.nextInt(cuantosQuedan);  //aleatorio entre 0 y los que queden por asignar 
            menorD = posMenoresDist[posAux];
            for (int j = 0; j < cuantosQuedan; j++) {
                if (posMenoresDist[j] != menorD) {
                    marcadosDist[posMenoresDist[j]] = false;
                }
            }

            //asignamos la localizacion con la unidad seleccionadas
            vSolGreAle[mayorF] = menorD;
        }
    }

    public static long DLBit(int[][] F, int[][] D, int[] vSolucion) {
        Configurador config = new Configurador("config.txt");
        int N = F.length;
        long costeMejor;
        boolean mejora;
        boolean mejoraIteraciones;                                 //para ver si vDLB está lleno de 1, por lo tanto no existiria mejor movimiento
        int iteraciones = 0;
        int posInter = 0;
        int[] vDLB = new int[N];
        long coste = funcionCoste(F, D, vSolucion);                //calculamos nuestro coste inicial a comparar de la LRC[i]
        boolean esTabu;
        List<Pair> listaTabu = new ArrayList<>();                  //como podriamos inicializar una lista de tamaño 3 con 3 instancias de pares -1, -1?
        int[][] memLargoPlazo = new int[N][N];                     //memoria a largo plazo
        long costeMejorPeores = Long.MAX_VALUE;
        int[] vMejorPeores = new int[N];
        int contOscEst = 0;                                        //contador para saber cuando utilizar oscilacion estrategica
        long costeOptGlobal = Long.MAX_VALUE;                      //coste de la solucion optima global
        int[] vSolOptGlobal = new int[N];
        int contOscEstMejora = 0, contOscEstNoMejora = 0;          //contadores para saber cuantas veces la oscilación ha mejorado o no
        Random r = new Random();
        int[] vSolIntercambios = new int[N];                       //vector solucion de intercambios, cada posicion una unidad, lo que contiene cada posicion son las localizaciones más intercambiadas con dicha unidad durante la ejecución del algoritmo
        long costeOscEstMejora = Long.MAX_VALUE;                   //variable auxiliar para ir almacenando el coste de la última vez que recurrimos a la oscilacion estrategica                 

        for (int i = 0; i < N; i++) {
            vDLB[i] = 0;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                memLargoPlazo[i][j] = 0;
            }
        }

        for (int i = 0; i < config.getTenenciaTabu(); i++) {
            listaTabu.add(new Pair(-1, -1));
        }

        while (iteraciones < config.getIteracionesMax()) {
            mejoraIteraciones = false; //para cuando vDLB esté lleno de 1
            iteraciones++;
            Log.LOGGER.info("#################### ITERACION " + iteraciones + " ####################");

            for (int i = posInter, ite = 0; ite < N; i++, ite++) {
                if (i == N) {
                    i = 0;
                }
                if (vDLB[i] == 0) {
                    mejora = false;

                    for (int j = i + 1; j != i; j++) {
                        if (j == N) { //por si la i es igual a N - 1
                            j = 0;
                        }

                        esTabu = false;
                        for (Pair tabus : listaTabu) {
                            if ((tabus.getL() == i && tabus.getR() == j) || (tabus.getL() == j && tabus.getR() == i)) {
                                esTabu = true;
                                break;
                            }
                        }

                        if (!esTabu) {
                            costeMejor = factorizacion(F, D, vSolucion, i, j, coste);
                            if (costeMejor < coste) {
                                Log.LOGGER.info("INTERCAMBIO I-J: " + i + "-" + j);
                                intercambiaPos(vSolucion, i, j);
                                coste = costeMejor;
                                vDLB[i] = vDLB[j] = 0;
                                mejora = true;
                                mejoraIteraciones = true;
                                posInter = j;  //guardamos la posicion del intercambio

                                for (int l = 0; l < N; l++) {
                                    memLargoPlazo[l][vSolucion[l]]++;   //incrementamos en 1 para todos los elementos de la solucion completa
                                }

                                listaTabu.add(new Pair(i, j));  //se añade por la cola
                                listaTabu.remove(0);

                                break; //cuando hay intercambio salimos del bucle doble
                            } else {
                                if (costeMejor < costeMejorPeores) {
                                    costeMejorPeores = costeMejor;
                                    for (int k = 0; k < N; k++) {
                                        vMejorPeores[k] = vSolucion[k];
                                    }
                                }
                            }
                        }
                        if (j == N - 1) { //para que la j sea circular
                            j = -1;
                        }
                    }
                    if (!mejora) {
                        vDLB[i] = 1;
                    } else {
                        break;    //si hay intercambio salimos del bucle
                    }
                }
            }

            if (mejoraIteraciones == false) {   //si el vDLB está lleno de 1, nos movemos a la mejor solucion de las peores
                coste = costeMejorPeores;
                for (int i = 0; i < N; i++) {
                    vSolucion[i] = vMejorPeores[i];
                }
                contOscEst++;
                for (int i = 0; i < N; i++) {
                    vDLB[i] = 0;
                }
            } else {
                if (coste < costeOptGlobal) {
                    costeOptGlobal = coste;
                    for (int i = 0; i < N; i++) {
                        vSolOptGlobal[i] = vSolucion[i];
                    }
                    contOscEst = 0;
                }
            }

            if (contOscEst == config.getIteracionesMax() / (100 / (100 * config.getPorcOscEst()))) {  //1000/(100/(100*0.05))=50
                int random = r.nextInt(100);
                if (random < config.getProbOscEst() * 100) {
                    masIntercambios(memLargoPlazo, vSolIntercambios);
                    Log.LOGGER.info("EJECUCION OSCILACION ESTRATEGICA MASINTERCAMBIOS");
                } else {
                    menosIntercambios(memLargoPlazo, vSolIntercambios);
                    Log.LOGGER.info("EJECUCION OSCILACION ESTRATEGICA MENOSINTERCAMBIOS");
                }
                contOscEst = 0;

                for (int i = 0; i < N; i++) {
                    vSolucion[i] = vSolIntercambios[i];
                }
                //vSolucion = vSolIntercambios;

                if (coste < costeOscEstMejora) {
                    costeOscEstMejora = coste;
                    contOscEstMejora++;
                    Log.LOGGER.info("OSCILACION ESTRATEGICA MEJORA");
                } else {
                    contOscEstNoMejora++;
                    Log.LOGGER.info("OSCILACION ESTRATEGICA NO MEJORA");
                }
                Log.LOGGER.info("COSTE OPTIMO GLOBAL: " + costeOptGlobal);

                coste = funcionCoste(F, D, vSolucion); 

                //reseteamos memoria a largo plazo
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        memLargoPlazo[i][j] = 0;
                    }
                }
                //Arrays.fill(memLargoPlazo, 0);   --> Comprobar si funciona

                //reseteamos memoria a corto plazo
                for (int i = 0; i < config.getTenenciaTabu(); i++) {
                    listaTabu.remove(i);
                    listaTabu.add(i, new Pair(-1, -1));
                }
            }
            Log.LOGGER.info("COSTE DLB: " + coste);
            Log.LOGGER.info("VECTOR DLB: " + visualizaVectorLog(vSolucion));
            Log.LOGGER.info("                                                                      ");

        }
        Log.LOGGER.info("VECES QUE LA OSCILACION ESTRATEGICA MEJORA: " + contOscEstMejora);
        Log.LOGGER.info("VECES QUE LA OSCILACION ESTRATEGICA NO MEJORA: " + contOscEstNoMejora);

        System.out.println("Iteraciones: " + iteraciones);
        System.out.println("OscEstMej: " + contOscEstMejora);
        System.out.println("OscEstNoMej: " + contOscEstNoMejora);
        for (int i = 0; i < N; i++) {
            vSolucion[i] = vSolOptGlobal[i];
        }
        coste = costeOptGlobal;

        return coste;
    }

    public static long multiArranque(int[][] F, int[][] D, int[] vFlujo, int[] vDistancia, int[] vSolMA) {
        double tiempoInicial = System.nanoTime();
        Configurador config = new Configurador("config.txt");
        int N = F.length;
        int[][] LRC = new int[config.getTamanioLRC()][N];
        int[] vSolGreAle = new int[N];
        long mejorCoste = Long.MAX_VALUE;
        long coste;
        Log.LOGGER.info("///////////////////   ALGORITMO MA   ///////////////////");

        for (int i = 0; i < config.getTamanioLRC(); i++) {
            greedyAleatorizado(F, D, vFlujo, vDistancia, vSolGreAle);
            System.arraycopy(vSolGreAle, 0, LRC[i], 0, N); //LRC[i]=vSolGreAle;
        }

        for (int i = 0; i < config.getTamanioLRC(); i++) {
            System.out.println("LRC[i] antes de DLB: ");
            Log.LOGGER.info("SOLUCION INICIAL LRC " + i + ": " + visualizaVectorLog(LRC[i]));
            coste = DLBit(F, D, LRC[i]);
            Log.LOGGER.info("COSTE FINAL LRC: " + coste);
            Log.LOGGER.info("VECTOR FINAL LRC: " + visualizaVectorLog(LRC[i]));
            Log.LOGGER.info("                                                                      ");
            System.out.println("LRC[i] despues de DLB: ");
            System.out.println("Coste LRC[i]: " + coste);
            if (coste < mejorCoste) {
                mejorCoste = coste;
                for (int j = 0; j < N; j++) {
                    vSolMA[j] = LRC[i][j];
                }
            }
        }
        double tiempoFinal = System.nanoTime();
        Log.LOGGER.info("TIEMPO TRANSCURRIDO: " + (tiempoFinal - tiempoInicial) / 1000000 + "ms");
        return mejorCoste;
    }
}
