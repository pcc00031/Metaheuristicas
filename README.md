
# METAHEURÍSTICAS
  ## Descripción
  Para realizar esta serie de prácticas partimos del problema de la asignación quadrática. Dados unos archivos de datos para fábricas de coches, 
  desarrollamos una serie de algoritmos para analizar y estudiar su comportamiento en ejecución.
  
  ## Manual de uso
  Este repositorio corresponde a un proyecto de Netbeans 12.1 usando la plataforma Maven, precisamente la version 3.8.1
  Descarga el proyecto y ejecútalo siguiendo la configuración mencionada
  
  ### Archivos de datos
  En la carpeta datos encontrarás los archivos sobre los cuales realizamos las pruebas algorítmicas
  
  ### Archivo config.txt
  En este archivo podemos configurar el tipo de ejecución del programa siguiendo los siguientes aspectos:
  
  - Archivos=ford01.dat ford02.dat ford03.dat ford04.dat nissan01.dat nissan02.dat nissan03.dat nissan04.dat 
    - Puedes escoger cualquier tipo de configuración para Archivos
  - Semillas=75931474 59314747 93147475 31474759 14747593   --> Usadas como generador de número aleatorio
  - Algoritmos=DLB MA     --> Opciones: DLB, MA, EstOX, EstPMX, GenOX2, GenPMX
  - SelectorDLB=1         --> Escoger entre 0 (DLB iterativo) y 1 (DLB aleatorio)
  - IteracionesMax=1000
 
  ATENCIÓN: se recomienda no modificar estos parámetros ya que están preparados para conseguir la mejor versión del programa
  
  - TenenciaTabu=3
  - TamanioLRC=10
  - CandidatosGRASP=5
  - Porcentaje Oscilacion Estrategica=0.05
  - Probabilidad Oscilacion Estrategica=0.5
