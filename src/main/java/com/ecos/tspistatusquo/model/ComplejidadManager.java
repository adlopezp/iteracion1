/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.tspistatusquo.model;

import com.ecos.tspistatusquo.vo.Aplicacion;
import com.ecos.tspistatusquo.vo.Clase;
import com.ecos.tspistatusquo.vo.Metodo;
import com.ecos.tspistatusquo.vo.Paquete;

/**
 *
 * @author Alvaro
 */
public class ComplejidadManager {
    
    public static void calcularComplejidad(final Aplicacion aplicacion) {
        
        for(Paquete paquete : aplicacion.getPaquetes()){
            for(Clase clase : paquete.getClases()) {
                for(Metodo metodo : clase.getMetodos()) {
                    
                }
            }
        }
    }
    
    private static void calcularComplejidad(final Metodo metodo) {
        int complejidad =1; // TODO: Verificar Setters y Getters
        for(String string : metodo.getCodigo()) {
            if(string.contains("if")){
                complejidad++;
                // TODO: AND Y OR
            } else if (string.contains("for")) {
                complejidad++;
            } else if (string.contains("while")) {
                complejidad++;
            } else if (string.contains("catch")) {
                complejidad++;
            } else if (string.contains("return")) {
                complejidad++;
            }
        }
        metodo.setComplejidad(complejidad);
    }
}
