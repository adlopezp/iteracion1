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
public class Clase {

    private String nombre;
    private String nombreArchivo;
    private String visibilidad;
    private int lineas = 0;
    private int lineasError = 0;
    private final List<Metodo> metodos = new ArrayList<Metodo>();
    private final List<Atributo> atributos = new ArrayList<Atributo>();

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
    public void addMetodo(final Metodo metodo) {
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

    public void addLOC() {
        lineas++;
    }

    public void addError() {
        lineasError++;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void addAtributo(final Atributo atributo) {
        atributos.add(atributo);
    }

    public int getLineasError() {
        return lineasError;
    }

    public void setLineasError(int lineasError) {
        this.lineasError = lineasError;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public int getComplejidad() {
        int total = 0;
        for (Metodo metodo : metodos) {
            total += metodo.getComplejidad();
        }
        return total;
    }
    
    public List<Metodo> getConstructores() {
        List<Metodo> constructores = new ArrayList<Metodo>();
        for(Metodo metodo : metodos) {
            if(metodo.getNombre()==null || metodo.getNombre().isEmpty()) {
                constructores.add(metodo);
            }
        }
        return constructores;
    }
    
    public List<Metodo> getMetodosValidados() {
        List<Metodo> validados = new ArrayList<Metodo>();
        for(Metodo metodo : metodos) {
            if(metodo.getNombre()!=null && !metodo.getNombre().isEmpty()) {
                validados.add(metodo);
            }
        }
        return validados;
    }
}
