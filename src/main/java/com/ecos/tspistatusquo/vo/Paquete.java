/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.tspistatusquo.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Paquete {
    
    private String nombre;
    final private List<Clase> clases;

    public Paquete() {
        this.clases = new ArrayList<Clase>();
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the metodos
     */
    public List<Clase> getClases() {
        return clases;
    }
    
    /**
     * @param metodo objeto Metodo a ser agregado
     */
    public void addClase(final Clase clase){
        clases.add(clase);
    }
}
