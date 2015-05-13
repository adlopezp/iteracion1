/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.tspistatusquo.model;

import com.ecos.tspistatusquo.vo.Aplicacion;
import com.ecos.tspistatusquo.vo.Clase;
import com.ecos.tspistatusquo.vo.Metodo;
import com.ecos.tspistatusquo.vo.Paquete;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alvaro
 */
public class ComplejidadManager {

    private static final String REGEX_IF = "^(if\\s*\\()|(\\sif\\s*\\()";
    private static final String REGEX_IF_COM = "(\\\"(.)*?if\\s*\\((.)*?\\\")";
    private static final String REGEX_FOR = "^(for\\s*\\()|(\\sfor\\s*\\()";
    private static final String REGEX_FOR_COM = "(\\\"(.)*?for\\s*\\((.)*?\\\")";
    private static final String REGEX_WHILE = "^(while\\s*\\()|(\\swhile\\s*\\()";
    private static final String REGEX_WHILE_COM = "(\\\"(.)*?while\\s*\\((.)*?\\\")";
    private static final String REGEX_CASE = "^(case\\s+)|(\\scase\\s+)";
    private static final String REGEX_CASE_COM = "(\\\"(.)*?case\\s+(.)*?\\\")";
    private static final String REGEX_CATCH = "(}\\s*catch\\s*\\()";
    private static final String REGEX_CATCH_COM = "(\\\"(.)*?(}\\s*catch\\s*\\()(.)*?\\\")";
    private static final String REGEX_THROW = "^(throw\\s+new\\s+)|(\\sthrow\\s+new\\s+)";
    private static final String REGEX_THROW_COM = "(\\\"(.)*?(\\s*throw\\s+new\\s+)(.)*?\\\")";
    private static final String REGEX_RETURN = "^(return\\s+)|(\\sreturn\\s+)";
    private static final String REGEX_RETURN_COM = "(\\\"(.)*?(\\s*return\\s+)(.)*?\\\")";
    private static final String REGEX_AND = "(\\s*&&\\s*)";
    private static final String REGEX_AND_COM = "(\\\"(.)*?(\\s*&&\\s*)(.)*?\\\")";
    private static final String REGEX_OR = "(\\s*\\|\\|\\s*)";
    private static final String REGEX_OR_COM = "(\\\"(.)*?(\\s*\\|\\|\\s*)(.)*?\\\")";
    private static final String REGEX_INT = "(\\?)";
    private static final String REGEX_INT_COM = "(\\\"(.)*?(\\?)(.)*?\\\")";
    private static final String REGEX_INT_DAT = "(\\<(.)*?(\\?)(.)*?\\>)";
    private static final String REGEX_SETTER = "(void\\s+set.+\\s*\\(\\s*(final)*\\s*.+\\s+.+\\s*\\))";
    private static final String REGEX_GETTER = "(.*\\s+get.+\\s*\\(\\s*\\))|(.*\\s+is.+\\s*\\(\\s*\\))";

    public static void calcularComplejidad(final Aplicacion aplicacion) {

        for (Paquete paquete : aplicacion.getPaquetes()) {
            for (Clase clase : paquete.getClases()) {
                for (Metodo metodo : clase.getMetodos()) {
                    calcularComplejidad(metodo);
                }
            }
        }
    }

    private static void calcularComplejidad(final Metodo metodo) {

        int complejidad = 1; // TODO: Verificar Setters y Getters

        for (int i = 0; i < metodo.getCodigo().size(); i++) {
            final String linea = metodo.getCodigo().get(i);
            complejidad += evaluarLinea(linea, REGEX_IF, REGEX_IF_COM);
            complejidad += evaluarLinea(linea, REGEX_AND, REGEX_AND_COM);
            complejidad += evaluarLinea(linea, REGEX_OR, REGEX_OR_COM);
            complejidad += evaluarLinea(linea, REGEX_INT, REGEX_INT_COM, REGEX_INT_DAT);
            complejidad += evaluarLinea(linea, REGEX_FOR, REGEX_FOR_COM);
            complejidad += evaluarLinea(linea, REGEX_WHILE, REGEX_WHILE_COM);
            complejidad += evaluarLinea(linea, REGEX_CASE, REGEX_CASE_COM);
            complejidad += evaluarLinea(linea, REGEX_CATCH, REGEX_CATCH_COM);
            complejidad += evaluarLinea(linea, REGEX_THROW, REGEX_THROW_COM);

            final int returns = evaluarLinea(linea, REGEX_RETURN, REGEX_RETURN_COM);
            if (returns > 0) {
                if (i >= metodo.getCodigo().size() - 2) {
                    if (returns > 1) {
                        complejidad += returns - 1;
                    }
                } else {
                    complejidad += returns;
                }
            }
        }
        metodo.setComplejidad(complejidad);

        if (isSetter(metodo)) {
            metodo.setComplejidad(0);
        } else if (isGetter(metodo)) {
            metodo.setComplejidad(0);
        }
    }

    private static int evaluarLinea(final String linea, final String regex, final String regexCom) {
        return evaluarLinea(linea, regex, regexCom, null);
    }

    private static int evaluarLinea(final String linea, final String regex, final String regexCom, final String regexOp) {
        int match = 0;
        int matchComs = 0;
        int matchOp = 0;

        Pattern pt = Pattern.compile(regex);
        Matcher matcher = pt.matcher(linea);
        if (matcher.find()) {
            match++;
        }

        pt = Pattern.compile(regexCom);
        matcher = pt.matcher(linea);
        if (matcher.find()) {
            matchComs++;
        }

        if (regexOp != null) {
            pt = Pattern.compile(regexOp);
            matcher = pt.matcher(linea);
            if (matcher.find()) {
                matchOp++;
            }
        }
        return (match - matchComs - matchOp);
    }

    private static boolean isSetter(final Metodo metodo) {
        boolean rsp = false;
        if (metodo.getComplejidad() == 1 && metodo.getCodigo().size() <= 4) {
            Pattern pt = Pattern.compile(REGEX_SETTER);
            Matcher matcher = pt.matcher(metodo.getCodigo().get(0));
            if (matcher.find()) {
                rsp = true;
            }
        }
        return rsp;
    }

    private static boolean isGetter(final Metodo metodo) {
        boolean rsp = false;
        if (metodo.getComplejidad() == 1 && metodo.getCodigo().size() <= 4) {
            Pattern pt = Pattern.compile(REGEX_GETTER);
            Matcher matcher = pt.matcher(metodo.getCodigo().get(0));
            if (matcher.find()) {
                rsp = true;
            }
        }
        return rsp;
    }
}
