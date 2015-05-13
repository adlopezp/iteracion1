package com.ecos.tspistatusquo.model;

import com.ecos.tspistatusquo.exceptions.ExceptionApp;
import com.ecos.tspistatusquo.vo.Atributo;
import com.ecos.tspistatusquo.vo.Clase;
import com.ecos.tspistatusquo.vo.Metodo;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase encargada de calcular la cantidad de LOC
 *
 * @author Dev
 */
public class ArchivoManager {


    private boolean enMetodo = false;
    private boolean enComentario = false;
    private int contadorCorchetes = 0;
    
    private Clase claseActual;
    private Metodo metodoActual;

    /**
     * analizarArchivo
     *
     * recorre recursivamente el directorio selecionado para el analsis de los
     * programas e identifica los paquetes conformados por el programa
     *
     * @param ruta
     * @param separador
     * @throws Exception
     */
    
    public Clase analizarArchivo(final File archivo) throws Exception {
        try {
            claseActual = new Clase();
            claseActual.setNombreArchivo(archivo.getName());

            final FileInputStream fstream = new FileInputStream(archivo);
            final DataInputStream entrada = new DataInputStream(fstream);
            final BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea = buffer.readLine();
            while (strLinea != null) {
                analizarLinea(strLinea);
                strLinea = buffer.readLine();
            }
            buffer.close();
            entrada.close();
            fstream.close();
            
            return claseActual;
                        
        } catch (Exception e) {
            System.out.println("Ocurrio un Error : " + e.getMessage());
            throw new ExceptionApp("Error al leer la ruta del programa. " + e.getMessage());
        }
    }

    /**
     * analizarLinea
     *
     * realiza el conteo y validacion de reglas asignando y acomulando las
     * variables del proceso de analisis para consolidar el resumen de el
     * programa
     *
     * @param linea
     *
     */
    private void analizarLinea(final String line) throws Exception {
        try {
            String linea = line.trim();
            boolean locValido = true;
            boolean flag = true;

            Pattern pt;
            Matcher matcher;
            // Ignorar lineas vacias
            // Ignorar lineas comentario simple
            if (linea.isEmpty() || linea.indexOf("//") == 0) {
                locValido = false;
            }

            if (locValido) {

                if (enComentario) {
                    locValido = false;
                }

                if (linea.indexOf("/*") == 0) {
                    locValido = false;
                    enComentario = true;
                }
                if (line.lastIndexOf("*/") == line.length() - 2) {
                    locValido = false;
                    enComentario = false;
                }

            }

            // Buscar Definicion Clase
            if (locValido) {
                pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.clases"), Pattern.CASE_INSENSITIVE);
                matcher = pt.matcher(linea);
                if (matcher.find()) {
                    claseActual.setNombre(matcher.group(4));
                    claseActual.setVisibilidad(matcher.group(1));
                    flag = false;
                }
            }

            // Buscar Definicion Atributos
            if (locValido && flag) {
                pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.atributos"), Pattern.CASE_INSENSITIVE);
                matcher = pt.matcher(linea);
                if (matcher.find()) {

                    final Atributo atributo = new Atributo();
                    atributo.setVisbilidad(matcher.group(1));
                    atributo.setTipo(matcher.group(2));
                    atributo.setNombre(matcher.group(3));
                    claseActual.addAtributo(atributo);
                    flag = false;
                }
            }

            // Buscar Definicion Metodos
            if (locValido && flag) {
                pt = Pattern.compile(Configuraciones.getProp().getProperty("regex.metodos"), Pattern.MULTILINE);
                matcher = pt.matcher(linea);
                if (matcher.find()) {
                    metodoActual = new Metodo();
                    metodoActual.setVisibilidad(matcher.group(1));
                    metodoActual.setTipo(matcher.group(2));
                    metodoActual.setNombre(matcher.group(4));
                    claseActual.addMetodo(metodoActual);
                    flag = false;
                    enMetodo = true;

                    if (linea.contains("{")) {
                        contadorCorchetes = 1;
                    } else {
                        contadorCorchetes = 0;
                    }
                }
            }
            
            if (locValido) {
                claseActual.addLOC();
                if (enMetodo) {
                    metodoActual.addLinea(linea);
                }
            }

            if (locValido && flag) {
                if (linea.contains("{") && enMetodo) {
                    contadorCorchetes++;
                }

                if (linea.contains("}") && enMetodo) {
                    contadorCorchetes--;
                    if (contadorCorchetes == 0) {
                        enMetodo = false;
                    }
                }
            }

            

        } catch (Exception ex) {
            System.out.println("Ocurrio un Error : " + ex.getMessage());
            throw new ExceptionApp("Error al calcular las variables de anailis del programa. " + ex.getMessage());
        }
    }
}
