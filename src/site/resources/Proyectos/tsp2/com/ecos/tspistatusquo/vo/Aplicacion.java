package com.ecos.tspistatusquo.vo;

import com.ecos.tspistatusquo.vo.Paquete;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Aplicacion {

    private String nombre;
    final private List<Paquete> paquetes = new ArrayList<Paquete>();

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

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public void addPaquete(final Paquete paquete) {
        paquetes.add(paquete);
    }

    public int getNumeroTotalClases() {
        int total = 0;
        for (Paquete paquete : paquetes) {
            total += paquete.getClases().size();
        }
        return total;
    }

    public int getNumeroTotalLoc() {
        int total = 0;
        for (Paquete paquete : paquetes) {
            for (Clase clase : paquete.getClases()) {
                total += clase.getLineas();
            }
        }
        return total;
    }
}
