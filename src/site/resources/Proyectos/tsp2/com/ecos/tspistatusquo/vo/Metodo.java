package com.ecos.tspistatusquo.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Metodo {
    
    private String nombre;
    private String visibilidad;
    private String tipo;
    private int complejidad;
    private List<String> codigo = new ArrayList<String>();

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


    public int getLineas() {
        return getCodigo().size();
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void addLinea(final String linea) {
        getCodigo().add(linea);
    }

    public List<String> getCodigo() {
        return codigo;
    }

    public int getComplejidad() {
        return complejidad;
    }

    public void setComplejidad(int complejidad) {
        this.complejidad = complejidad;
    }
}
