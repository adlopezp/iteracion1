package com.ecos.tspistatusquo.temp.biz;

import com.ecos.tspistatusquo.temp.vo.Aplicacion;
import com.ecos.tspistatusquo.temp.vo.Clase;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class FileBiz {
    
    public static List<Aplicacion> buscarProyectos() throws IOException{
        final File raiz = new File("src/main/resources");
        final File[] aplicacionesFile = raiz.listFiles();
        
        final List<Aplicacion> aplicaciones = new ArrayList<Aplicacion>();
        
        for(File aplicacionFile : aplicacionesFile){
            final Aplicacion aplicacion = new Aplicacion();
            aplicacion.setNombre(aplicacionFile.getName());
            buscarClases(aplicacionFile, aplicacion.getClases());
            aplicaciones.add(aplicacion);
            
        }
        return aplicaciones;
    }
    
    private static void buscarClases(final File carpeta, final List<Clase> clases) throws IOException{
        
        for(File archivo : carpeta.listFiles()){
            if(archivo.isDirectory()){
                buscarClases(archivo, clases);
            } else {
                final FileManager manager = new FileManager(archivo);
                final Clase clase = manager.getClase();
                clases.add(clase);
            }
        }
    }
    
}
