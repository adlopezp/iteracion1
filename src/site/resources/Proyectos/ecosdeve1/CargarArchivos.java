/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.ecosdeve1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * @class CargarArchivos
 * 
 * que permite leer archivos planos txt
 * 
 * @author Dev
 */
public class CargarArchivos {
    /**
     * propiedad que identifica la ruta de ejecucion del proyecto java
     */
    private String sDirectorioTrabajo = System.getProperty("user.dir");

    /**
     *
     */
    public CargarArchivos() {
    }
    
    /**
     * metodo leerArchivo
     * 
     * lee datos numericos separados por salto de linea y los almacena en una lista enlazada
     * 
     * @param ruta
     * @return LinkedList<BigDecimal>
     * @throws Exception
     */
    public LinkedList<BigDecimal> leerArchivo(String ruta) throws Exception{
        LinkedList<BigDecimal> lista =new LinkedList<BigDecimal>();
       try{
            FileInputStream fstream = new FileInputStream(sDirectorioTrabajo + "\\" + ruta);
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            while ((strLinea = buffer.readLine()) != null)   {
                lista.add(new BigDecimal(strLinea.trim()));
            }
            if (lista.size()==1){
                throw new ExceptionMedia("Debe ingresar minimo 2 datos para calcular media y desviacion estandar");
            }
            entrada.close();
        }catch (Exception e){
            throw new ExceptionMedia("Ocurrio un error: " + e.getMessage());
        }
       return lista;
    }

    /**
     *
     * @return
     */
    public String getsDirectorioTrabajo() {
        return sDirectorioTrabajo;
    }

    /**
     *
     * @param sDirectorioTrabajo
     */
    public void setsDirectorioTrabajo(String sDirectorioTrabajo) {
        this.sDirectorioTrabajo = sDirectorioTrabajo;
    }
    
    
}
