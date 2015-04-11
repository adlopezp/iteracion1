/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.tspistatusquo.model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Dev
 */
public class CalcularLoc {

    private int indexClass = -1;
    private int indexMetodos = -1;
    private BigInteger contadorLMod = BigInteger.ZERO;
    private BigInteger contadorLEli = BigInteger.ZERO;
    private BigInteger contadorLoc = BigInteger.ZERO;
    //d
    private List<BigInteger> contadorLocClases = new ArrayList<BigInteger>();
    private List<List<String>> nombreClases = new ArrayList<List<String>>();
    private List<List<String>> nombreMetodos = new ArrayList<List<String>>();
    private List<List<String>> nombreAtributos = new ArrayList<List<String>>();
    private FileInputStream fstream;
    private DataInputStream entrada = null;
    private BufferedReader buffer;
    private String strLinea = null;

    public void leerRuta(String ruta, String separador) throws Exception {
        try {
            File path = new File(ruta);
            File[] ficheros = path.listFiles();
            if (ficheros == null) {
                System.out.println("No hay ficheros en el directorio especificado");
            } else {
                for (int x = 0; x < ficheros.length; x++) {
                    if (ficheros[x].isDirectory()) {
                        leerRuta(ruta + separador+ ficheros[x].getName(), separador);
                    } else {
                        if (ficheros[x].getName().toLowerCase().contains(".java")) {
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
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un Error : " + e.getMessage());
        }
    }

    private void SumarVariables(String linea) {
        boolean flag = true;
        Pattern pt;
        Matcher matcher;
        if (flag) {
            pt = Pattern.compile("(public\\s|private\\s|protected\\s)(class)\\s+([a-zA-Z0-9]+)", Pattern.CASE_INSENSITIVE);
            matcher = pt.matcher(linea);
            while (matcher.find()) {
                indexClass++;
                contadorLocClases.add(BigInteger.ZERO);
                nombreClases.add(new ArrayList<String>());
                nombreAtributos.add(new ArrayList<String>());
                nombreMetodos.add(new ArrayList<String>());
                indexMetodos = 0;
                nombreClases.get(indexClass).add(matcher.group(1) + matcher.group(2) + " " + matcher.group(3));
                flag = false;
            }
        }
        if (flag) {
            pt = Pattern.compile("(public\\s|private\\s|protected\\s)\\s*(?:readonly\\s+)?(?:static\\s+)?(?!class)([a-zA-Z0-9]*<?[a-zA-Z0-9]*>?+)\\s+(\\w+)\\s?(=|;)", Pattern.CASE_INSENSITIVE);
            matcher = pt.matcher(linea);
            while (matcher.find()) {
                nombreAtributos.get(indexClass).add(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
                flag = false;
            }
        }
        if (flag) {
            pt = Pattern.compile("(public\\s|private\\s|protected\\s)\\s*(?:readonly\\s+)?(?:static\\s+)?(?!class)([a-zA-Z0-9]*<?[a-zA-Z0-9]*>?+)\\s+(\\w+)\\s?\\(", Pattern.MULTILINE);
            matcher = pt.matcher(linea);
            while (matcher.find()) {
                indexMetodos++;
                nombreMetodos.get(indexClass).add(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
                flag = false;
            }
        }
        pt = Pattern.compile("^(?!\\s*$|\\s?import|\\s?package|[ \\s]*}$|[ \\s]*\\/\\/|[ \\s]*\\/\\*|[ \\s]*\\*)(.*)", Pattern.CASE_INSENSITIVE);
        matcher = pt.matcher(linea);
        while (matcher.find()) {
            contadorLocClases.set(indexClass, contadorLocClases.get(indexClass).add(BigInteger.ONE));
            setContadorLoc(contadorLoc.add(BigInteger.ONE));
        }
        if (linea.toLowerCase().contains("//m")) {
            setContadorLMod(contadorLMod.add(BigInteger.ONE));
        }
        if (linea.toLowerCase().contains("//e")) {
            setContadorLEli(contadorLEli.add(BigInteger.ONE));
        }
    }

    public BigInteger getContadorLoc() {
        return contadorLoc;
    }

    public void setContadorLoc(BigInteger contadorLoc) {
        this.contadorLoc = contadorLoc;
    }

    //d
    public List<List<String>> getNombreClases() {
        return nombreClases;
    }

    public void setNombreClases(List<List<String>> nombreClases) {
        this.nombreClases = nombreClases;
    }

    public List<List<String>> getNombreMetodos() {
        return nombreMetodos;
    }

    public void setNombreMetodos(List<List<String>> nombreMetodos) {
        this.nombreMetodos = nombreMetodos;
    }

    public List<List<String>> getNombreAtributos() {
        return nombreAtributos;
    }

    public void setNombreAtributos(List<List<String>> nombreAtributos) {
        this.nombreAtributos = nombreAtributos;
    }

    public int getIndexClass() {
        return indexClass;
    }

    public void setIndexClass(int indexClass) {
        this.indexClass = indexClass;
    }

    public int getIndexMetodos() {
        return indexMetodos;
    }

    public void setIndexMetodos(int indexMetodos) {
        this.indexMetodos = indexMetodos;
    }

    public List<BigInteger> getContadorLocClases() {
        return contadorLocClases;
    }

    public void setContadorLocClases(List<BigInteger> contadorLocClases) {
        this.contadorLocClases = contadorLocClases;
    }

    public BigInteger getContadorLMod() {
        return contadorLMod;
    }

    public void setContadorLMod(BigInteger contadorLMod) {
        this.contadorLMod = contadorLMod;
    }

    public BigInteger getContadorLEli() {
        return contadorLEli;
    }

    public void setContadorLEli(BigInteger contadorLEli) {
        this.contadorLEli = contadorLEli;
    }
}
