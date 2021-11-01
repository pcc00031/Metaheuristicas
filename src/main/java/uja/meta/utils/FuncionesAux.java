package uja.meta.utils;

import java.util.Arrays;

/**
 *
 * @author Carlos
 */
public class FuncionesAux {

    public static long funcionCoste(int[][] F, int[][] D, int[] vSolucion) {
        long sum = 0;
        for (int i = 0; i < F.length; i++) {
            for (int j = 0; j < F.length; j++) {
                if (i != j) {
                    sum = sum + (F[i][j] * D[vSolucion[i]][vSolucion[j]]);
                }
            }
        }
        return sum;
    }

    public static void visualizaMatriz(int[][] matriz, int tam) {
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                System.out.print(matriz[i][j]);
                System.out.print("  ");
            }
            System.out.println();
        }
    }

    public static String visualizaVectorLog(int[] vSolucion) {  //le sumamos 1 ya que la unidad 0 suponemos que no existe!
        String vector = "";
        for (int i = 0; i < vSolucion.length; i++) {
            vector = vector + Integer.toString(vSolucion[i]) + " ";
            System.out.print(vSolucion[i]);
            System.out.print("  ");
        }
        System.out.println();
        return vector;
    }

    public static void visualizaVector(int[] vSolucion) {  //le sumamos 1 ya que la unidad 0 suponemos que no existe!
        for (int i = 0; i < vSolucion.length; i++) {
            System.out.print(vSolucion[i]);
            System.out.print("  ");
        }
        System.out.println();
    }

    public static void sumaFilas(int[][] matriz, int[] vector, int tamMatVec) {
        int sum = 0;
        for (int i = 0; i < tamMatVec; i++) {
            for (int j = 0; j < tamMatVec; j++) {
                sum = sum + matriz[i][j];
            }
            vector[i] = sum;
            sum = 0;
        }
    }

    public static long factorizacion(int[][] F, int[][] D, int[] vSolu, int i, int j, long coste) {
        long sum = 0;
        for (int k = 0; k < F.length; k++) {
            if (k != i && k != j) {
                sum = sum + (F[i][k] * (D[vSolu[j]][vSolu[k]] - D[vSolu[i]][vSolu[k]]));
                sum = sum + (F[j][k] * (D[vSolu[i]][vSolu[k]] - D[vSolu[j]][vSolu[k]]));

                sum = sum + (F[k][i] * (D[vSolu[k]][vSolu[j]] - D[vSolu[k]][vSolu[i]]));
                sum = sum + (F[k][j] * (D[vSolu[k]][vSolu[i]] - D[vSolu[k]][vSolu[j]]));
            }
        }
        sum = sum + (F[i][i] * (D[vSolu[j]][vSolu[j]] - D[vSolu[i]][vSolu[i]]));
        sum = sum + (F[j][j] * (D[vSolu[i]][vSolu[i]] - D[vSolu[j]][vSolu[j]]));
        sum = sum + (F[i][j] * (D[vSolu[j]][vSolu[i]] - D[vSolu[i]][vSolu[j]]));
        sum = sum + (F[j][i] * (D[vSolu[i]][vSolu[j]] - D[vSolu[j]][vSolu[i]]));
        return coste + sum;
    }

    public static void intercambiaPos(int[] vSolucion, int i, int j) {
        int aux = vSolucion[i];
        vSolucion[i] = vSolucion[j];
        vSolucion[j] = aux;
    }

    //rellena un vector con las unidades (posiciones del vector) y las localizaciones que más cambios han hecho con dichas unidades
    public static void masIntercambios(int[][] memLargoPlazo, int[] vIntercambios) {
        int posFil = 0, posCol = 0;   
        int maximo = Integer.MIN_VALUE;
        boolean[] marcadosFil = new boolean[memLargoPlazo.length];
        boolean[] marcadosCol = new boolean[memLargoPlazo.length];
        Arrays.fill(marcadosFil, false);
        Arrays.fill(marcadosCol, false);
        for (int i = 0; i < memLargoPlazo.length; i++) {
            for (int j = 0; j < memLargoPlazo.length; j++) {
                if (marcadosFil[j] == false) {
                    for (int k = 0; k < memLargoPlazo.length; k++) {
                        if (marcadosCol[k] == false && memLargoPlazo[j][k] >= maximo) { 
                            maximo = memLargoPlazo[j][k];
                            posFil = j;
                            posCol = k;
                        }
                    }
                }
            }
            maximo = Integer.MIN_VALUE;
            vIntercambios[posFil] = posCol;
            marcadosFil[posFil] = true;
            marcadosCol[posCol] = true;
        }
    }

    public static void menosIntercambios(int[][] memLargoPlazo, int[] vIntercambios) {
        int posFil = 0, posCol = 0;   
        int minimo = Integer.MAX_VALUE;
        boolean[] marcadosFil = new boolean[memLargoPlazo.length];
        boolean[] marcadosCol = new boolean[memLargoPlazo.length];
        Arrays.fill(marcadosFil, false);
        Arrays.fill(marcadosCol, false);
        for (int i = 0; i < memLargoPlazo.length; i++) {
            for (int j = 0; j < memLargoPlazo.length; j++) {
                if (marcadosFil[j] == false) {
                    for (int k = 0; k < memLargoPlazo.length; k++) {
                        if (marcadosCol[k] == false && memLargoPlazo[j][k] <= minimo) {  
                            minimo = memLargoPlazo[j][k];
                            posFil = j;
                            posCol = k;
                        }
                    }
                }
            }
            minimo = Integer.MAX_VALUE;
            vIntercambios[posFil] = posCol;
            marcadosFil[posFil] = true;
            marcadosCol[posCol] = true;
        }
    }

    public static int mayorFlujo(int[] vFlujo, boolean[] marcadosFlujo) {
        long maximo = -1;
        int posMax = 0;
        for (int i = 0; i < vFlujo.length; i++) {
            if (vFlujo[i] > maximo && !marcadosFlujo[i]) {
                maximo = vFlujo[i];
                posMax = i;
            }
        }
        marcadosFlujo[posMax] = true;
        return posMax;
    }

    public static int menorDist(int[] vDist, boolean[] marcadosDist) {
        long minimo = Long.MAX_VALUE;
        int posMin = 0;
        for (int i = 0; i < vDist.length; i++) {
            if (vDist[i] < minimo && !marcadosDist[i]) {
                minimo = vDist[i];
                posMin = i;
            }
        }
        marcadosDist[posMin] = true;
        return posMin;
    }
}

//0.15 - 0.55 - 0.185 - 0.582 
