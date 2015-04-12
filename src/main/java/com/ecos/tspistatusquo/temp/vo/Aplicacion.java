package com.ecos.tspistatusquo.temp.vo;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Alvaro
 */
public class Aplicacion {
    
    private String nombre;
    final private List<Clase> clases = new ArrayList<Clase>(); 

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
     * @return the clases
     */
    public List<Clase> getClases() {
        return clases;
    }
    
    /**
     * @param clase objeto Clase a ser agregado
     */
    public void addMetodo(final Clase clase){
        clases.add(clase);
    }
    
}
