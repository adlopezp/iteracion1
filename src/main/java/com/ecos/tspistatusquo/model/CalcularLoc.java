package com.ecos.tspistatusquo.model;

import com.ecos.tspistatusquo.exceptions.ExceptionApp;
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

    private int indexClass = -1;
    private int indexMetodos = -1;
    private BigInteger contadorLMod = BigInteger.ZERO;
    private BigInteger contadorLEli = BigInteger.ZERO;
    private BigInteger contadorLoc = BigInteger.ZERO;
    private BigInteger contadorLocMetodo = BigInteger.ZERO;
    private String nombreProyecto = null;
    private String nombrePaquete = null;
    private List<BigInteger> clasesXpaquetes = new ArrayList<BigInteger>();
    ;
    //d
    private List<BigInteger> contadorLocClases = new ArrayList<BigInteger>();
    private Map<String, List<BigInteger>> paquetes = new HashMap<String, List<BigInteger>>();
    private List<List<String>> nombreClases = new ArrayList<List<String>>();
    private List<List<String>> nombreMetodos = new ArrayList<List<String>>();
    private List<List<BigInteger>> contadorLocMetodos = new ArrayList<List<BigInteger>>();
    private List<List<String>> nombreAtributos = new ArrayList<List<String>>();
    private FileInputStream fstream;
    private DataInputStream entrada = null;
    private BufferedReader buffer;
    private String strLinea = null;
    private boolean enMetodo = false;
    private int contadorCorchetes = 0;

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
            if (flag) {
                pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.clases"), Pattern.CASE_INSENSITIVE);
                matcher = pt.matcher(linea);
                while (matcher.find()) {
                    indexClass++;
                    contadorLocClases.add(BigInteger.ZERO);
                    nombreClases.add(new ArrayList<String>());
                    nombreAtributos.add(new ArrayList<String>());
                    nombreMetodos.add(new ArrayList<String>());
                    contadorLocMetodos.add(new ArrayList<BigInteger>());
                    indexMetodos = -1;
                    nombreClases.get(indexClass).add(matcher.group(1) + matcher.group(3) + " " + matcher.group(4));
                    flag = false;
                }
            }
            if (flag) {
                pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.atributos"), Pattern.CASE_INSENSITIVE);
                matcher = pt.matcher(linea);
                while (matcher.find()) {
                    nombreAtributos.get(indexClass).add(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
                    flag = false;
                }
            }
            if (flag) {
                pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.metodos"), Pattern.MULTILINE);
                matcher = pt.matcher(linea);
                while (matcher.find()) {
                    indexMetodos++;
                    nombreMetodos.get(indexClass).add(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(4));
                    flag = false;
                    setEnMetodo(true);
                    contadorCorchetes = 0;

                }
            }
            if (linea.contains("{") && isEnMetodo() == true) {
                contadorCorchetes++;
            }
            if (linea.contains("}") && isEnMetodo() == true) {
                contadorCorchetes--;
                if (contadorCorchetes == 0) {
                    getContadorLocMetodos().get(indexClass).add(getContadorLocMetodo());
                    setContadorLocMetodo(BigInteger.ZERO);
                    setEnMetodo(false);
                }
            }
            pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.contadorLOC"), Pattern.CASE_INSENSITIVE);
            matcher = pt.matcher(linea);
            while (matcher.find()) {
                contadorLocClases.set(indexClass, contadorLocClases.get(indexClass).add(BigInteger.ONE));
                setContadorLoc(contadorLoc.add(BigInteger.ONE));
                if (isEnMetodo() == true && getContadorCorchetes() != 0) {
                    setContadorLocMetodo(contadorLocMetodo.add(BigInteger.ONE));
                }
            }

            if (linea.toLowerCase().contains("//m")) {
                setContadorLMod(contadorLMod.add(BigInteger.ONE));
            }
            if (linea.toLowerCase().contains("//e")) {
                setContadorLEli(contadorLEli.add(BigInteger.ONE));
            }
        } catch (Exception ex) {
            System.out.println("Ocurrio un Error : " + ex.getMessage());
            throw new ExceptionApp("Error al calcular las variables de anailis del programa. " + ex.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public BigInteger getContadorLoc() {
        return contadorLoc;
    }

    /**
     *
     * @param contadorLoc
     */
    public void setContadorLoc(BigInteger contadorLoc) {
        this.contadorLoc = contadorLoc;
    }

    //d
    /**
     *
     * @return
     */
    public List<List<String>> getNombreClases() {
        return nombreClases;
    }

    /**
     *
     * @param nombreClases
     */
    public void setNombreClases(List<List<String>> nombreClases) {
        this.nombreClases = nombreClases;
    }

    /**
     *
     * @return
     */
    public List<List<String>> getNombreMetodos() {
        return nombreMetodos;
    }

    /**
     *
     * @param nombreMetodos
     */
    public void setNombreMetodos(List<List<String>> nombreMetodos) {
        this.nombreMetodos = nombreMetodos;
    }

    /**
     *
     * @return
     */
    public List<List<String>> getNombreAtributos() {
        return nombreAtributos;
    }

    /**
     *
     * @param nombreAtributos
     */
    public void setNombreAtributos(List<List<String>> nombreAtributos) {
        this.nombreAtributos = nombreAtributos;
    }

    /**
     *
     * @return
     */
    public int getIndexClass() {
        return indexClass;
    }

    /**
     *
     * @param indexClass
     */
    public void setIndexClass(int indexClass) {
        this.indexClass = indexClass;
    }

    /**
     *
     * @return
     */
    public int getIndexMetodos() {
        return indexMetodos;
    }

    /**
     *
     * @param indexMetodos
     */
    public void setIndexMetodos(int indexMetodos) {
        this.indexMetodos = indexMetodos;
    }

    /**
     *
     * @return
     */
    public List<BigInteger> getContadorLocClases() {
        return contadorLocClases;
    }

    /**
     *
     * @param contadorLocClases
     */
    public void setContadorLocClases(List<BigInteger> contadorLocClases) {
        this.contadorLocClases = contadorLocClases;
    }

    /**
     *
     * @return
     */
    public BigInteger getContadorLMod() {
        return contadorLMod;
    }

    /**
     *
     * @param contadorLMod
     */
    public void setContadorLMod(BigInteger contadorLMod) {
        this.contadorLMod = contadorLMod;
    }

    /**
     *
     * @return
     */
    public BigInteger getContadorLEli() {
        return contadorLEli;
    }

    /**
     *
     * @param contadorLEli
     */
    public void setContadorLEli(BigInteger contadorLEli) {
        this.contadorLEli = contadorLEli;
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
    public List<BigInteger> getClasesXpaquetes() {
        return clasesXpaquetes;
    }

    /**
     *
     * @param clasesXpaquetes
     */
    public void setClasesXpaquetes(List<BigInteger> clasesXpaquetes) {
        this.clasesXpaquetes = clasesXpaquetes;
    }

    /**
     *
     * @return
     */
    public boolean isEnMetodo() {
        return enMetodo;
    }

    /**
     *
     * @param enMetodo
     */
    public void setEnMetodo(boolean enMetodo) {
        this.enMetodo = enMetodo;
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

    /**
     *
     * @return
     */
    public BigInteger getContadorLocMetodo() {
        return contadorLocMetodo;
    }

    /**
     *
     * @param contadorLocMetodo
     */
    public void setContadorLocMetodo(BigInteger contadorLocMetodo) {
        this.contadorLocMetodo = contadorLocMetodo;
    }

    /**
     *
     * @return
     */
    public List<List<BigInteger>> getContadorLocMetodos() {
        return contadorLocMetodos;
    }

    /**
     *
     * @param contadorLocMetodos
     */
    public void setContadorLocMetodos(List<List<BigInteger>> contadorLocMetodos) {
        this.contadorLocMetodos = contadorLocMetodos;
    }
}
