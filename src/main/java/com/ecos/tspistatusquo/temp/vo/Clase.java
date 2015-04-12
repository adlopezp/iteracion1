/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.tspistatusquo.temp.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Clase {
    
    private String nombre;
    private int lineas;
    final private List<Metodo> metodos = new ArrayList<Metodo>();

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the metodos
     */
    public List<Metodo> getMetodos() {
        return metodos;
    }
    
    /**
     * @param metodo objeto Metodo a ser agregado
     */
    public void addMetodo(final Metodo metodo){
        metodos.add(metodo);
    }

    /**
     * @return the lineas
     */
    public int getLineas() {
        return lineas;
    }

    /**
     * @param lineas the lineas to set
     */
    public void setLineas(int lineas) {
        this.lineas = lineas;
    }
    
}
