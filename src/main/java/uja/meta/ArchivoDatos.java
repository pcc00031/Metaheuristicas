
package uja.meta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Carlos
 */
public class ArchivoDatos {

    private String nombre; //nombre del archivo
    private int matriz1[][];
    private int matriz2[][];

    public ArchivoDatos(String rutaArchDatos) {
        nombre = rutaArchDatos.split("./datos/.\\.")[0];  //nos quedamos con lo que hay antes de la extension (.txt)
        rutaArchDatos = "datos/" + rutaArchDatos;
        String linea;
        FileReader f = null;
        try {
            f = new FileReader(rutaArchDatos);
            BufferedReader b = new BufferedReader(f);
            int N = Integer.parseInt(b.readLine().substring(1));  //la primera linea (que es el tamaño de matriz nxn) lo guardamos // el .substring(1) porque empieza con un espacio en blanco
            matriz1 = new int[N][N];
            matriz2 = new int[N][N];
            linea = b.readLine(); //leemos la segunda linea del archivo (vacia)
            
            //rellenamos la primera matriz
            for (int i = 0; i < N; i++) {
                linea = b.readLine();
                String[] separador = linea.split(" ");
                int errores = 0;
                for (int j = 0; j < separador.length; j++) {
                    try {
                        matriz1[i][j - errores] = Integer.parseInt(separador[j]);
                    } catch (NumberFormatException ex) {  //excepcion si contiene espacio en blanco(ya que puede haber 2 espacios en blanco entre numeros)
                        errores++;
                    }
                }
            }
            linea = b.readLine(); //lectura vacía

            //rellenamos la segunda matriz
            for (int i = 0; i < N; i++) {
                linea = b.readLine();
                String[] separador = linea.split(" ");
                int errores = 0;
                for (int j = 0; j < separador.length; j++) {
                    try {
                        matriz2[i][j - errores] = Integer.parseInt(separador[j]);
                    } catch (NumberFormatException ex) {  //excepcion si contiene espacio en blanco(ya que puede haber 2 espacios en blanco entre numeros)
                        errores++;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the matriz1
     */
    public int[][] getMatriz1() {
        return matriz1;
    }

    /**
     * @return the matriz2
     */
    public int[][] getMatriz2() {
        return matriz2;
    }

}
