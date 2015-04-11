/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.ecosdeve4.model;

import com.ecos.ecosdeve4.controller.exceptions.ExceptionApp;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedList;

/**
 * clase CalcularMediaDesviacion
 *
 * esta clase calcula y almacena los valores relacionados con el calculo de la
 * media y desviacion estandar
 * 
 * @param LinkedList<BigDecimal> list    : lista de datos para el calculo de la media y desviacion estandar
 * @param BigDecimal media               : almacena el valor de la media de la lista enlazada
 * @param BigDecimal desviacion          : almacena el valor de la desviacion estandar de la lista enlazada
 * @author Dev
 */
public class CalcularMediaDesviacion {

    private LinkedList<BigDecimal> list = new LinkedList<BigDecimal>();
    private BigDecimal media = BigDecimal.ZERO;
    private BigDecimal desviacion = null;

    /**
     * metodo constructor CalcularMediaDesviacion()
     *
     *inicializa la clase al ser instanciada con una lista de datos inicial para aplicar pruebas y ver los resultados
     *
     * @author Dev
     */
    public CalcularMediaDesviacion() {
        list.add(new BigDecimal(160));
        list.add(new BigDecimal(591));
        list.add(new BigDecimal(114));
        list.add(new BigDecimal(229));
        list.add(new BigDecimal(230));
        list.add(new BigDecimal(270));
        list.add(new BigDecimal(128));
        list.add(new BigDecimal(1657));
        list.add(new BigDecimal(624));
        list.add(new BigDecimal(1503));
    }

    /**
     * metodo constructor CalcularMediaDesviacion()
     *
     * calcula la media de los datos almacenados en la propiedad List
     * 
     * @throws Exception 
     * @author Dev
     */
    public void calcularMedia() throws Exception {
        BigDecimal sumaDatos = BigDecimal.ZERO;
        for (BigDecimal dato : list) {
            sumaDatos = sumaDatos.add(dato);
        }
        setMedia(sumaDatos.divide(new BigDecimal(list.size()), MathContext.DECIMAL64));//m
    }

    /**
     * metodo calculardesviacion()
     * 
     *  calcula la desviacion estandar de los datos almacenados en la propiedad List
     * 
     * @throws Exception
     */
    public void calculardesviacion() throws Exception {
        BigDecimal sumadesviacion = BigDecimal.ZERO;
        BigDecimal divicion;
        if (media != null) {
            for (BigDecimal dato : list) {
                sumadesviacion = sumadesviacion.add((dato.subtract(media)).pow(2));
            }
            divicion = sumadesviacion.divide(new BigDecimal(list.size() - 1), BigDecimal.ROUND_HALF_UP);
            setDesviacion(new BigDecimal(Math.sqrt(divicion.doubleValue())));
        } else {
            throw new ExceptionApp("Primero debe calcularse la media");
        }
    }

    /**
     *
     * @return
     */
    public LinkedList<BigDecimal> getList() {
        return list;
    }

    /**
     *
     * @param list
     */
    public void setList(LinkedList<BigDecimal> list) {
        this.list = list;
    }

    public BigDecimal getMedia() {
        return media;
    }

    public void setMedia(BigDecimal media) {
        this.media = media;
    }

    /**
     *
     * @return
     */
    public BigDecimal getDesviacion() {
        return desviacion;
    }

    public void setDesviacion(BigDecimal desviacion) {
        this.desviacion = desviacion;
    }
}
