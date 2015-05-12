package com.ecos.tspistatusquo.model;

import com.ecos.tspistatusquo.exceptions.ExceptionApp;
import com.ecos.tspistatusquo.vo.Aplicacion;
import com.ecos.tspistatusquo.vo.Atributo;
import com.ecos.tspistatusquo.vo.Clase;
import com.ecos.tspistatusquo.vo.Metodo;
import com.ecos.tspistatusquo.vo.Paquete;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Clase encargada de calcular la cantidad de LOC
 * @author Dev
 */
public class CalcularLoc {

//    private BigInteger contadorLMod = BigInteger.ZERO;
//    private BigInteger contadorLEli = BigInteger.ZERO;
//    private BigInteger contadorLoc = BigInteger.ZERO;
//    private BigInteger contadorLocMetodo = BigInteger.ZERO;
    
    private String nombreProyecto = null;
    private String nombrePaquete = null;
//    private List<BigInteger> clasesXpaquetes = new ArrayList<BigInteger>();
    
    //d
//    private List<BigInteger> contadorLocClases = new ArrayList<BigInteger>();
    private Map<String, List<BigInteger>> paquetes = new HashMap<String, List<BigInteger>>();
    
//    private List<List<String>> nombreClases = new ArrayList<List<String>>();
//    private List<List<String>> nombreMetodos = new ArrayList<List<String>>();
    
//    private List<List<BigInteger>> contadorLocMetodos = new ArrayList<List<BigInteger>>();
    
//    private List<List<String>> nombreAtributos = new ArrayList<List<String>>();
    private FileInputStream fstream;
    private DataInputStream entrada = null;
    private BufferedReader buffer;
    private String strLinea = null;
    
    private boolean enMetodo = false;
    private int contadorCorchetes = 0;
    
    private Aplicacion aplicacion = new Aplicacion();
    private Paquete paqueteActual;
    private Clase claseActual;
    private Metodo metodoActual;

    /**
     * CalcularLoc
     *
     * metodo constructor encargado de inicializar el modulo de configuracion de
     * reglas de analisis de programas , carga el archivo de propiedades con las
     * reglas de validacion
     *
     */
    public CalcularLoc() throws Exception {
        Configuraciones.cargaPropiedades();
    }

    /**
     * leerRuta
     *
     * recorre recursivamente el directorio selecionado para el analsis de los
     * programas e identifica los paquetes conformados por el programa
     *
     * @param ruta
     * @param separador
     * @throws Exception
     */
    public void leerRuta(String ruta, String separador) throws Exception {
        try {
            String paqueteActual;
            File path = new File(ruta);
            if (getNombreProyecto() == null) {
                setNombreProyecto(path.getName());
            }
            File[] ficheros = path.listFiles();
            if (ficheros == null) {
                System.out.println("No hay ficheros en el directorio especificado");
            } else {
                for (int x = 0; x < ficheros.length; x++) {
                    if (ficheros[x].isDirectory()) {
                        leerRuta(ruta + separador + ficheros[x].getName(), separador);
                    } else {
                        if (ficheros[x].getName().toLowerCase().contains(Configuraciones.getProp().getProperty("extension"))) {
                            paqueteActual = ruta.substring(ruta.indexOf(getNombreProyecto()), ruta.length()).replace(separador, ".");
                            paqueteActual = paqueteActual.replace("..", ".");
                            if (paqueteActual.charAt(paqueteActual.length() - 1) == '.') {
                                paqueteActual = paqueteActual.substring(0, paqueteActual.length() - 1);
                            }
                            if (getNombrePaquete() == null) {
                                setNombrePaquete(paqueteActual);
                            }
                            if (getNombrePaquete().equals(paqueteActual)) {
                                getClasesXpaquetes().add(BigInteger.valueOf(indexClass + 1));
                            } else {
                                getPaquetes().put(getNombrePaquete(), getClasesXpaquetes());
                                setClasesXpaquetes(new ArrayList<BigInteger>());
                                getClasesXpaquetes().add(BigInteger.valueOf(indexClass + 1));
                                setNombrePaquete(paqueteActual);
                            }
                            fstream = new FileInputStream(ruta + separador + ficheros[x].getName());//m
                            entrada = new DataInputStream(fstream);
                            buffer = new BufferedReader(new InputStreamReader(entrada));
                            while ((strLinea = buffer.readLine()) != null) {
                                SumarVariables(strLinea);
                            }
                            entrada.close();
                        }
                    }
                }
                paquetes.put(getNombrePaquete(), getClasesXpaquetes());
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un Error : " + e.getMessage());
            throw new ExceptionApp("Error al leer la ruta del programa. " + e.getMessage());
        }
    }

    /**
     * SumarVariables
     *
     * realiza el conteo y validacion de reglas asignando y acomulando las
     * variables del proceso de analisis para consolidar el resumen de el
     * programa
     *
     * @param linea
     *
     */
    private void SumarVariables(String linea) throws Exception{
        try {
            boolean flag = true;
            Pattern pt;
            Matcher matcher;
            // Buscar Clases
            if (flag) {
                pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.clases"), Pattern.CASE_INSENSITIVE);
                matcher = pt.matcher(linea);
                if (matcher.find()) {
                    
                    claseActual = new Clase();
                    claseActual.addLOC();
                    claseActual.setNombre(matcher.group(4));
                    claseActual.setVisibilidad(matcher.group(1));
                    flag = false;
                }
            }
            // Buscar Atributos
            if (flag) {
                pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.atributos"), Pattern.CASE_INSENSITIVE);
                matcher = pt.matcher(linea);
                if (matcher.find()) {
                    
                    final Atributo atributo = new Atributo();
                    atributo.setVisbilidad(matcher.group(1));
                    atributo.setTipo(matcher.group(2));
                    atributo.setNombre(matcher.group(3));
                    claseActual.addAtributo(atributo);
                    claseActual.addLOC();
                    flag = false;
                }
            }
            // Buscar Metodos
            if (flag) {
                pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.metodos"), Pattern.MULTILINE);
                matcher = pt.matcher(linea);
                if (matcher.find()) {
                    metodoActual = new Metodo();
                    metodoActual.setVisibilidad(matcher.group(1));
                    metodoActual.setTipo(matcher.group(2));
                    metodoActual.setNombre(matcher.group(4));
                    metodoActual.addLOC();
                    claseActual.addMetodo(metodoActual);
                    claseActual.addLOC();
                    flag = false;
                    enMetodo = true;
                    
                    if (linea.contains("{")) {
                        contadorCorchetes = 1;
                    } else {
                        contadorCorchetes = 0;
                    }
                }
            }
            
            if (flag) {
                if (linea.contains("{")) {
                    claseActual.addLOC();
                    if(enMetodo) {
                        metodoActual.addLOC();
                        contadorCorchetes++;
                    }
                } 

                if (linea.contains("}")) {
                    claseActual.addLOC();
                    if(enMetodo){
                        metodoActual.addLOC();
                        contadorCorchetes--;
                        if (contadorCorchetes == 0) {
                            enMetodo = false;
                        }
                    }
                }
                
            }
            
            
            
//            pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.contadorLOC"), Pattern.CASE_INSENSITIVE);
//            matcher = pt.matcher(linea);
//            while (matcher.find()) {
//                contadorLocClases.set(indexClass, contadorLocClases.get(indexClass).add(BigInteger.ONE));
//                setContadorLoc(contadorLoc.add(BigInteger.ONE));
//                if (isEnMetodo() == true && getContadorCorchetes() != 0) {
//                    setContadorLocMetodo(contadorLocMetodo.add(BigInteger.ONE));
//                }
//            }
//
//            if (linea.toLowerCase().contains("//m")) {
//                setContadorLMod(contadorLMod.add(BigInteger.ONE));
//            }
//            if (linea.toLowerCase().contains("//e")) {
//                setContadorLEli(contadorLEli.add(BigInteger.ONE));
//            }
        } catch (Exception ex) {
            System.out.println("Ocurrio un Error : " + ex.getMessage());
            throw new ExceptionApp("Error al calcular las variables de anailis del programa. " + ex.getMessage());
        }
    }


    /**
     *
     * @return
     */
    public Map<String, List<BigInteger>> getPaquetes() {
        return paquetes;
    }

    /**
     *
     * @param paquetes
     */
    public void setPaquetes(Map<String, List<BigInteger>> paquetes) {
        this.paquetes = paquetes;
    }

    /**
     *
     * @return
     */
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    /**
     *
     * @param nombreProyecto
     */
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    /**
     *
     * @return
     */
    public String getNombrePaquete() {
        return nombrePaquete;
    }

    /**
     *
     * @param nombrePaquete
     */
    public void setNombrePaquete(String nombrePaquete) {
        this.nombrePaquete = nombrePaquete;
    }

    /**
     *
     * @return
     */
    public int getContadorCorchetes() {
        return contadorCorchetes;
    }

    /**
     *
     * @param contadorCorchetes
     */
    public void setContadorCorchetes(int contadorCorchetes) {
        this.contadorCorchetes = contadorCorchetes;
    }
}
