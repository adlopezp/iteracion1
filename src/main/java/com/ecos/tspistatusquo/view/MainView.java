/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.tspistatusquo.view;

import com.ecos.tspistatusquo.model.CalcularLoc;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dev
 */
public class MainView {

    private static String sDirectorioTrabajo = System.getProperty("user.dir");
    private static String sSeparator = System.getProperty("file.separator");

    public static void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CalcularLoc e1 = new CalcularLoc();
        CalcularLoc e2 = new CalcularLoc();
        CalcularLoc e3 = new CalcularLoc();
        CalcularLoc e4 = new CalcularLoc();
        CalcularLoc e5 = new CalcularLoc();
        CalcularLoc e6 = new CalcularLoc();
        resp.setContentType("text/html");
        resp.getWriter().println("<style type=\"text/css\">");
        resp.getWriter().println(".myTable { background-color:#eee;border-collapse:collapse; }");
        resp.getWriter().println(".myTable th { background-color:#000;color:white;width:50%; }");
        resp.getWriter().println(".myTable td, .myTable th { padding:5px;border:1px solid #000; }");
        resp.getWriter().println("</style>");

        resp.getWriter().println("<style type=\"text/css\">");
        resp.getWriter().println(".myOtherTable { background-color:#FFFFE0;border-collapse:collapse;color:#000;font-size:18px; }");
        resp.getWriter().println(".myOtherTable th { background-color:#BDB76B;color:white;width:50%; }");
        resp.getWriter().println(".myOtherTable td, .myOtherTable th { padding:5px;border:0; }");
        resp.getWriter().println(".myOtherTable td { border-bottom:1px dotted #BDB76B; }");
        resp.getWriter().println("</style>");



        try {
            e1.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve1" + sSeparator, sSeparator);//m
            e2.leerRuta("src" + sSeparator + "main" + sSeparator + "java" + sSeparator + "com" + sSeparator + "ecos" + sSeparator + "tspistatusquo" + sSeparator, sSeparator);//m
            e3.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve3" + sSeparator, sSeparator);
            e4.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve4" + sSeparator, sSeparator);
            e5.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve5" + sSeparator, sSeparator);
            e6.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve6" + sSeparator, sSeparator);
            resp.getWriter().println("Medicion de LOC");
            resp.getWriter().println("<table class=\"myTable\">");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>Numero Programa</td>");
            resp.getWriter().println("<td>Tipo de parte</td>");
            resp.getWriter().println("<td>Acceso, tipo, Nombre de parte</td>");
            resp.getWriter().println("<td>Numero de Atributos</td>");
            resp.getWriter().println("<td>Numero de Metodos</td>");
            resp.getWriter().println("<td>Total de la parte</td>");
            resp.getWriter().println("<td>Total tamaño</td>");
            resp.getWriter().println("</tr>");
            agregarPrograma(resp, e1, 1); 
            agregarPrograma(resp, e3, 2);
            agregarPrograma(resp, e4, 3);
            agregarPrograma(resp, e5, 4);
            agregarPrograma(resp, e6, 5);
            agregarPrograma(resp, e2, 6);
            resp.getWriter().println("</table>");
        } catch (Exception e) {
            resp.getWriter().print("Ocurrio un Error : " + e.getMessage());
        }
    }

    private static void agregarPrograma(HttpServletResponse resp, CalcularLoc e1, int numeroPrograma) throws Exception {
        for (int x = 0; x < e1.getNombreClases().size(); x++) {
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>" + numeroPrograma + "</td>");
            resp.getWriter().println("<td>Clase</td>");
            resp.getWriter().println("<td>" + e1.getNombreClases().get(x).toString() + "</td>");
            resp.getWriter().println("<td>" + e1.getNombreAtributos().get(x).size() + "</td>");
            resp.getWriter().println("<td>" + e1.getNombreMetodos().get(x).size() + "</td>");
            resp.getWriter().println("<td>" + e1.getContadorLocClases().get(x).toString() + "</td>");
            resp.getWriter().println("<td></td>");
            resp.getWriter().println("</tr>");
            if (!e1.getNombreAtributos().get(x).isEmpty()) {
                for (int i = 0; i < e1.getNombreAtributos().get(x).size(); i++) {
                    resp.getWriter().println("<tr>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("<td>Atributo</td>");
                    resp.getWriter().println("<td>               " + e1.getNombreAtributos().get(x).get(i).toString() + "</td>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("</tr>");
                }
            }
            if (!e1.getNombreMetodos().get(x).isEmpty()) {
                for (int j = 0; j < e1.getNombreMetodos().get(x).size(); j++) {
                    resp.getWriter().println("<tr>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("<td>Metodo</td>");
                    resp.getWriter().println("<td>                              " + e1.getNombreMetodos().get(x).get(j).toString() + "</td>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("</tr>");

                }
            }
        }
        resp.getWriter().println("<tr>");
        resp.getWriter().println("<td>" + numeroPrograma + "</td>");
        resp.getWriter().println("<td></td>");
        resp.getWriter().println("<td></td>");
        resp.getWriter().println("<td></td>");
        resp.getWriter().println("<td>Lineas Eliminadas : " + e1.getContadorLEli() + "</td>");
        resp.getWriter().println("<td>Lineas Modificadas : " + e1.getContadorLMod() + "</td>");
        resp.getWriter().println("<td>Total :" + e1.getContadorLoc().toString() + "</td>");
        resp.getWriter().println("</tr>");
    }

    public String getsDirectorioTrabajo() {
        return sDirectorioTrabajo;
    }

    public void setsDirectorioTrabajo(String sDirectorioTrabajo) {
        this.sDirectorioTrabajo = sDirectorioTrabajo;
    }

    public String getsSeparator() {
        return sSeparator;
    }

    public void setsSeparator(String sSeparator) {
        this.sSeparator = sSeparator;
    }
}
