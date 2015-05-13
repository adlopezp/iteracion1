package com.ecos.tspistatusquo.model;

import com.ecos.tspistatusquo.vo.Aplicacion;
import com.ecos.tspistatusquo.vo.Clase;
import com.ecos.tspistatusquo.vo.Paquete;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class PackageManager {

    private Aplicacion aplicacion;

    public Aplicacion cargarProyecto(final String ruta) throws Exception {
        final File raiz = new File(ruta);

        aplicacion = new Aplicacion();
        aplicacion.setNombre(raiz.getName());
        buscarClases(raiz, "");

        return aplicacion;
    }

    private void buscarClases(final File path, final String ruta) throws Exception {
        Paquete paquete = null;
        final String pathPaquete = ruta.isEmpty() ? "" : ruta + ".";

        for (File archivo : path.listFiles()) {
            if (archivo.isDirectory()) {
                buscarClases(archivo, pathPaquete+archivo.getName());
            } else if (archivo.getName().toLowerCase().contains(Configuraciones.getProp().getProperty("extension"))) {
                if (paquete == null) {
                    paquete = new Paquete();
                    paquete.setNombre(ruta);
                }
                final ArchivoManager analisis = new ArchivoManager();
                final Clase clase = analisis.analizarArchivo(archivo);
                paquete.addClase(clase);
            }
        }

        if (paquete != null) {
            aplicacion.addPaquete(paquete);
        }
    }
    
    public static List<Aplicacion> buscarProyectos() throws Exception{
        final String path = "src/site/resources/Proyectos";
        final File raiz = new File(path);
        final File[] aplicacionesFile = raiz.listFiles();
        
        final List<Aplicacion> aplicaciones = new ArrayList<Aplicacion>();
        
        for(File aplicacionFile : aplicacionesFile){
            final PackageManager manager = new PackageManager();
            final Aplicacion aplicacion = manager.cargarProyecto(path+"/"+aplicacionFile.getName());
            aplicaciones.add(aplicacion);
            
        }
        return aplicaciones;
    }
}
