package com.ecos.tspistatusquo.temp.biz;

import com.ecos.tspistatusquo.temp.vo.Clase;
import com.ecos.tspistatusquo.temp.vo.Metodo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alvaro
 */
public class FileManager {
    
    private static final String PATRON = "(public|private|protected)+ +(static )* *[_A-Za-z0-9<>\\[\\]]+ +([_A-Za-z0-9]+) *\\( *((final)* *[_A-Za-z0-9<>\\[\\]]+ +[_A-Za-z0-9]+ *)*\\) *(throws)* *[_A-Za-z0-9, ]* *\\{";
    
    private final Clase clase = new Clase();
    private int loc =0; // Contador de LOC global de la clase
    
    public FileManager(final File archivo) throws FileNotFoundException, IOException{
        clase.setNombre(archivo.getName().replace(".java", ""));
        analizarClase(archivo);
        clase.setLineas(loc);
    }

    /**
     * Analiza el archivo unicamente con codigo valido
     * sin lineas en blanco ni comentarios, cuenta las LOC
     * reales y crea arreglo de metodos
     *
     */
    private void analizarClase(final File archivo) throws FileNotFoundException, IOException{
        final FileReader fr = new FileReader(archivo);
        final BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        
        boolean comentario = false; // Bandera que indica si actualmente se encuentra dentro de un comentario /**/
        boolean metodo = false; // Bandera que indica si actualmente se encuentra dentro de un método
        
        String nombreMetodo = null;
        
        int locMetodo = 0; // Contador de LOC de cada metodo
        int corchetes = 0; // Contador de corchetes
        
        while (line != null){
            // Evita lineas en blanco
            // Evita lineas que inicien con "//"
            if(!line.trim().isEmpty() && line.trim().indexOf("//")!=0){
                // Evita todas las lineas dentro de un comentario "/*"
                if(comentario){
                    if(line.trim().lastIndexOf("*/")==line.trim().length()-2){
                        comentario = false;
                    }
                } else if(line.trim().indexOf("/*")==0){                
                    comentario = true;
                } else {
                    loc++;
                    
                    if(metodo) {
                        locMetodo++;
                        if(line.contains("{")){
                            corchetes++;
                        }
                        
                        if(line.contains("}")){
                            corchetes--;
                            if(corchetes==0){
                                agregarMetodo(nombreMetodo,locMetodo);
                                metodo = false;
                            }
                        }
                    } else if(isInicioMetodo(line.trim())){
                        metodo = true;
                        locMetodo = 1;
                        corchetes = 1;
                        nombreMetodo = getNombreMetodo(line);
                    } 
                }
            }
            line = br.readLine();
        }
        fr.close();
    }
    
    private boolean isInicioMetodo(String linea){
        Pattern patron = Pattern.compile(PATRON);
        Matcher buscar = patron.matcher(linea);
        return buscar.matches();
    }
    
    private String getNombreMetodo(String linea){
        Pattern patron = Pattern.compile(PATRON);
        Matcher buscar = patron.matcher(linea);
        buscar.find();
        return buscar.group(3);
    }
    
    private void agregarMetodo(final String nombre, final int lineas){
        Metodo metodo = new Metodo();
        metodo.setNombre(nombre);
        metodo.setLineas(lineas);
        getClase().getMetodos().add(metodo);
    }

    /**
     * @return the clase
     */
    public Clase getClase() {
        return clase;
    }
}
