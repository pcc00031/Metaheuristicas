package uja.meta;
//
//import static uja.meta.pr1.algoritmos.AlgMA_Clase01_Grupo06.multiArranque;
//import static uja.meta.pr1.algoritmos.AlgPMDLBit_Clase01_Grupo06.DLBit;
//import static uja.meta.pr1.algoritmos.AlgPMDLBrandom_Clase01_Grupo06.DLBrandom;

import static uja.meta.algoritmos.practica1.AlgGRE_Clase01_Grupo06.greedy;
import static uja.meta.algoritmos.noLog.MAnoLogger.multiArranque;
import static uja.meta.algoritmos.noLog.DLBitNoLogger.DLBit;
import static uja.meta.algoritmos.noLog.DLBaleNoLogger.DLBrandom;

import static uja.meta.utils.FuncionesAux.*;
import uja.meta.utils.Log;

/**
 *
 * @author Carlos
 */
public class main {

    public static void main(String[] args) {
        double tiempoInicial = System.nanoTime();
        Configurador config = new Configurador(args[0]);   //args[0] = config.txt, lo hemos añadido en propiedades del proyecto
        Log log = new Log();

        //Declaracion variables
        int N;
        int[][] F;
        int[][] D;
        int[] vFlujo;
        int[] vDistancia;
        int[] vSolucion;

        for (int k = 0; k < config.getArchivos().size(); k++) {
            ArchivoDatos archDatos = new ArchivoDatos(config.getArchivos().get(k));  //le pasamos el primer archivo (ford01.dat)
            N = archDatos.getMatriz1().length; //tamaño del problema

            //Volcado de datos
            F = new int[N][N];
            D = new int[N][N];

            for (int i = 0; i < N; i++) {
                //System.out.println(Arrays.toString(archDatos.getMatriz1()[i]));  //para mostrar por filas 
                F = archDatos.getMatriz1(); //tambien se puede conseguir la matriz del archivo de datos asi
                D = archDatos.getMatriz2();
            }

            //Visualizar
            System.out.println("///////////// FICHERO " + config.getArchivos().get(k) + " ////////////////");
            Log.LOGGER.info("*********************** FICHERO " + config.getArchivos().get(k) + " ***********************");

            //Algoritmo Greedy
            vSolucion = new int[N];
            vFlujo = new int[N];
            vDistancia = new int[N];

            sumaFilas(F, vFlujo, N);
            sumaFilas(D, vDistancia, N);

            greedy(vFlujo, vDistancia, vSolucion);

            long coste = funcionCoste(F, D, vSolucion);
            Log.LOGGER.info("COSTE GREEDY: " + coste);
            Log.LOGGER.info("VECTOR SOLUCION GREEDY: " + visualizaVectorLog(vSolucion));
            Log.LOGGER.info("                                                                      ");

            if (config.getAlgoritmos().contains("DLB")) {
                //Algoritmo DLB
                long costeDLB = 0;

                if (config.getSelectorDLB() == 0) {
                    costeDLB = DLBit(F, D, vSolucion, coste);
                } else {
                    costeDLB = DLBrandom(F, D, vSolucion, coste);
                }

                System.out.println("VECTOR SOLUCION DLB: ");
                Log.LOGGER.info("COSTE SOLUCION DLB: " + costeDLB);
                Log.LOGGER.info("VECTOR SOLUCION DLB: " + visualizaVectorLog(vSolucion));
                Log.LOGGER.info("                                                                      ");

            }
            if (config.getAlgoritmos().contains("MA")) {
                //Algoritmo MultiArranque
                long costeMA;
                int[] vSolMA = new int[N];
                costeMA = multiArranque(F, D, vFlujo, vDistancia, vSolMA);

                Log.LOGGER.info("COSTE SOLUCION MA: " + costeMA);
                Log.LOGGER.info("VECTOR SOLUCION MA: " + visualizaVectorLog(vSolMA));
                Log.LOGGER.info("                                                                      ");

            }
            if (config.getAlgoritmos().contains("EstOX")) {
                //Algoritmo Estacionario con cruce OX
            }
            if (config.getAlgoritmos().contains("EstPMX")) {
                //Algoritmo Estacionario con cruce PMX
            }
            if (config.getAlgoritmos().contains("GenOX2")) {
                //Algoritmo Generacional con cruce OX2
            }
            if (config.getAlgoritmos().contains("GenPMX")) {
                //Algoritmo Generacional con cruce PMX
            }
        }
        double tiempoFinal = System.nanoTime();
        Log.LOGGER.info("TIEMPO TRANSCURRIDO TOTAL: " + (tiempoFinal - tiempoInicial) / 1000000 + "ms");
    }
}
