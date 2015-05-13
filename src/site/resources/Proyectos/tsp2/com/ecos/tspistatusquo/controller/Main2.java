/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.tspistatusquo.controller;

import com.ecos.tspistatusquo.model.ComplejidadManager;
import com.ecos.tspistatusquo.model.PackageManager;
import com.ecos.tspistatusquo.vo.Aplicacion;
import com.ecos.tspistatusquo.vo.Atributo;
import com.ecos.tspistatusquo.vo.Clase;
import com.ecos.tspistatusquo.vo.Metodo;
import com.ecos.tspistatusquo.vo.Paquete;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Main2 {
    
    public static void main(String[] args) throws Exception {
        List<Aplicacion> aplicaciones = PackageManager.buscarProyectos();
        
        for(Aplicacion aplicacion : aplicaciones) {
            ComplejidadManager.calcularComplejidad(aplicacion);
            imprimirAplicaciones(aplicacion);
        }
    }
    
    private static void imprimirAplicaciones(final Aplicacion aplicacion){
        System.out.println("Aplicación "+aplicacion.getNombre()+"\n");
        for(Paquete paquete : aplicacion.getPaquetes()) {
            imprimirPaquetes(paquete);
        }
    }
    
    private static void imprimirPaquetes(final Paquete paquete){
        System.out.println("Paquete "+paquete.getNombre()+"\n");
        for(Clase clase : paquete.getClases()) {
            imprimirClases(clase);
        }
    }
    
    private static void imprimirClases(final Clase clase){
        System.out.println("Clase "+clase.getNombre()+"\n");
        
        for(Atributo atributo : clase.getAtributos()) {
            System.out.println("Atributo "+atributo.getNombre()+"\n");
        }
        
        for(Metodo metodo : clase.getMetodos()) {
            System.out.println("Metodo "+metodo.getNombre()+"\n");
            System.out.println("Complejidad "+metodo.getComplejidad()+"\n");
            System.out.println("Código:\n");
            for(String str : metodo.getCodigo()){
                System.out.println(str);
            }
            System.out.println("\n");
        }
    }
    
}
