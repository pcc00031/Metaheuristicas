package uja.meta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
//clase con los atributos del archivo config
public class Configurador {

    private ArrayList<String> archivos;
    private ArrayList<String> algoritmos;
    private ArrayList<Long> semillas;
    private Integer selectorDLB; 
    private Integer iteracionesMax; 
    private Integer tenenciaTabu; 
    private Integer tamanioLRC; 
    private Integer candidatosGRASP;
    private float porcOscEst; 
    private float probOscEst; 

    public Configurador(String rutaArchConfig) {
        archivos = new ArrayList<>();
        algoritmos = new ArrayList<>();
        semillas = new ArrayList<>();

        String linea;
        FileReader f = null;
        try {
            f = new FileReader(rutaArchConfig);        //
            BufferedReader b = new BufferedReader(f); //codigo basico para inicializar un archivo
            while ((linea = b.readLine()) != null) {  //leemos linea por linea
                String[] separador = linea.split("="); //separador de linea asignado a una variable --> vector con posicion 0 el nombre, y posicion 1 los parametros
                switch (separador[0]) {
                    case "Archivos":
                        String[] paramArch = separador[1].split(" ");
                        for (int i = 0; i < paramArch.length; i++) {
                            archivos.add(paramArch[i]);
                        }
                        break;
                    case "Semillas":
                        String[] paramSem = separador[1].split(" ");
                        for (int i = 0; i < paramSem.length; i++) {
                            semillas.add(Long.parseLong(paramSem[i])); //transformamos el string en Long
                        }
                        break;
                    case "Algoritmos":
                        String[] paramAlg = separador[1].split(" ");
                        for (int i = 0; i < paramAlg.length; i++) {
                            algoritmos.add(paramAlg[i]);
                        }
                        break;
                    case "SelectorDLB":
                        selectorDLB = Integer.parseInt(separador[1]);   //transformamos el string en Integer
                    case "IteracionesMax":
                        iteracionesMax = Integer.parseInt(separador[1]);   
                    case "TenenciaTabu":
                        tenenciaTabu = Integer.parseInt(separador[1]);   
                    case "TamanioLRC":
                        tamanioLRC = Integer.parseInt(separador[1]);   
                    case "CandidatosGRASP":
                        candidatosGRASP = Integer.parseInt(separador[1]);   
                    case "Porcentaje Oscilacion Estrategica":
                        porcOscEst = Float.parseFloat(separador[1]);   
                    case "Probabilidad Oscilacion Estrategica":
                        probOscEst = Float.parseFloat(separador[1]);   
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * @return the archivos
     */
    public ArrayList<String> getArchivos() {
        return archivos;
    }

    /**
     * @param archivos the archivos to set
     */
    public void setArchivos(ArrayList<String> archivos) {
        this.archivos = archivos;
    }

    /**
     * @return the algoritmos
     */
    public ArrayList<String> getAlgoritmos() {
        return algoritmos;
    }

    /**
     * @param algoritmos the algoritmos to set
     */
    public void setAlgoritmos(ArrayList<String> algoritmos) {
        this.algoritmos = algoritmos;
    }

    /**
     * @return the semillas
     */
    public ArrayList<Long> getSemillas() {
        return semillas;
    }

    /**
     * @param semillas the semillas to set
     */
    public void setSemillas(ArrayList<Long> semillas) {
        this.semillas = semillas;
    }

    /**
     * @return the DLB
     */
    public Integer getSelectorDLB() {
        return selectorDLB;
    }

    /**
     * @param DLB the DLB to set
     */
    public void setSelectorDLB(Integer DLB) {
        this.selectorDLB = DLB;
    }

    /**
     * @return the iteracionesMax
     */
    public Integer getIteracionesMax() {
        return iteracionesMax;
    }

    /**
     * @param iteracionesMax the iteracionesMax to set
     */
    public void setIteracionesMax(Integer iteracionesMax) {
        this.iteracionesMax = iteracionesMax;
    }

    /**
     * @return the tenenciaTabu
     */
    public Integer getTenenciaTabu() {
        return tenenciaTabu;
    }

    /**
     * @param tenenciaTabu the tenenciaTabu to set
     */
    public void setTenenciaTabu(Integer tenenciaTabu) {
        this.tenenciaTabu = tenenciaTabu;
    }

    /**
     * @return the tamañoLRC
     */
    public Integer getTamanioLRC() {
        return tamanioLRC;
    }

    /**
     * @param tamañoLRC the tamañoLRC to set
     */
    public void setTamanioLRC(Integer tamañoLRC) {
        this.tamanioLRC = tamañoLRC;
    }

    /**
     * @return the candidatosGRASP
     */
    public Integer getCandidatosGRASP() {
        return candidatosGRASP;
    }

    /**
     * @param candidatosGRASP the candidatosGRASP to set
     */
    public void setCandidatosGRASP(Integer candidatosGRASP) {
        this.candidatosGRASP = candidatosGRASP;
    }

    /**
     * @return the porcOscEst
     */
    public float getPorcOscEst() {
        return porcOscEst;
    }

    /**
     * @param porcOscEst the porcOscEst to set
     */
    public void setPorcOscEst(Integer porcOscEst) {
        this.porcOscEst = porcOscEst;
    }

    /**
     * @return the probOscEst
     */
    public float getProbOscEst() {
        return probOscEst;
    }

    /**
     * @param probOscEst the probOscEst to set
     */
    public void setProbOscEst(Integer probOscEst) {
        this.probOscEst = probOscEst;
    }
    
}
