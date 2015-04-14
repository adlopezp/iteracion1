/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.tspistatusquo.view;

import com.ecos.tspistatusquo.exceptions.ExceptionApp;
import com.ecos.tspistatusquo.model.CalcularLoc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MainView
 *
 * modela la capa de presentacion e interaciones con los usuarios
 *
 * @author Dev
 */
public class MainView {

    private static String sDirectorioTrabajo = System.getProperty("user.dir");
    private static String sSeparator = System.getProperty("file.separator");

    /**
     * showHome
     *
     * devuelve la respuesta a la peticion http con la vista inicial de la
     * aplicacion
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public static void showHome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<CalcularLoc> analisisPrograma = new ArrayList<CalcularLoc>();
            resp.setContentType("text/html");
            resp.getWriter().println("<html><head>");
            resp.getWriter().println("<style type=\"text/css\">");
            resp.getWriter().println("body { font-family: 'Open Sans', sans-serif;}");
            resp.getWriter().println(".titulo1 { font-size: 20px; font-weight: bold; }");
            resp.getWriter().println(".titulo2 { font-size: 15px; font-weight: bold; }");
            resp.getWriter().println(".myTable {background-color:#F0F0F0; border-collapse:collapse;font-size:13px; }");
            resp.getWriter().println(".myTable th {background-color:#CCCCCC;}");
            resp.getWriter().println(".myTable td, .myTable th { padding:5px;border:1px solid #000; }");
            resp.getWriter().println("</style>");

            resp.getWriter().println("<style type=\"text/css\">");
            resp.getWriter().println(".myOtherTable { background-color:#FFFFE0;border-collapse:collapse;color:#000;font-size:12px; }");
            resp.getWriter().println(".myOtherTable th { background-color:#BDB76B;color:white;width:50%; }");
            resp.getWriter().println(".myOtherTable td, .myOtherTable th { padding:5px;border:0; }");
            resp.getWriter().println(".myOtherTable td { border-bottom:1px dotted #BDB76B; }");
            resp.getWriter().println("</style>");
            CalcularLoc programa = new CalcularLoc();
            programa.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve1" + sSeparator, sSeparator);//m
            analisisPrograma.add(programa);
            programa = new CalcularLoc();
            programa.leerRuta("src" + sSeparator + "main" + sSeparator + "java" + sSeparator + "com" + sSeparator + "ecos" + sSeparator + "tspistatusquo" + sSeparator, sSeparator);//m
            analisisPrograma.add(programa);
            programa = new CalcularLoc();
            programa.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve3" + sSeparator, sSeparator);
            analisisPrograma.add(programa);
            programa = new CalcularLoc();
            programa.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve4" + sSeparator, sSeparator);
            analisisPrograma.add(programa);
            programa = new CalcularLoc();
            programa.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve5" + sSeparator, sSeparator);
            analisisPrograma.add(programa);
            programa = new CalcularLoc();
            programa.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve6" + sSeparator, sSeparator);
            analisisPrograma.add(programa);

            resp.getWriter().println("<script>");
            resp.getWriter().println("var seleccionado = 'programa0';");
            resp.getWriter().println("function seleccionarTabla(combo) {");
            resp.getWriter().println("var activar = document.getElementById(combo);");
            resp.getWriter().println("activar.style.display='block';");
            resp.getWriter().println("var ocultar = document.getElementById(seleccionado);");
            resp.getWriter().println("ocultar.style.display = 'none';");
            resp.getWriter().println("seleccionado=combo;");
            resp.getWriter().println("}");
            resp.getWriter().println("</script>");
            resp.getWriter().println("</head>");
            resp.getWriter().println("<body>");
            
            resp.getWriter().println("<div align=\"center\">");
            resp.getWriter().println("<div style=\"text-align: center; background-color:#F0F0F0; border: 1px solid #ccc; width: 900px;\">");
            resp.getWriter().println("<H2>Status Quo</H2>");
            resp.getWriter().println("<H3>Proyecto Ciclo 1 </H3> <p>An&aacute;lisis de programas</p>");

            resp.getWriter().println("<table align=\"center\">");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>Seleccionar Programa :</td>");
            resp.getWriter().println("<td><select name=\"programas\" onchange=\"seleccionarTabla(this.value)\">");
            for (int index = 0; index < analisisPrograma.size(); index++) {
                resp.getWriter().println("<option value=\"programa" + index + "\" > " + analisisPrograma.get(index).getNombreProyecto() + " </option>");
            }
            resp.getWriter().println("</select></td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</table><br/>");
            resp.getWriter().println("</div>");
            resp.getWriter().println("</div>");
            
            for (int index = 0; index < analisisPrograma.size(); index++) {
                agregarPrograma(resp, analisisPrograma.get(index), index);
            }
            resp.getWriter().println("</body>");
            resp.getWriter().println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
            error(req, resp, e);
        }
    }

    /**
     * agregarPrograma
     *
     * adiciona dinamicamente los programas adicionados para el analsis de sus
     * caracteristicas
     *
     *
     * @param resp
     * @param programa
     * @param numeroPrograma
     * @throws IOException
     */
    private static void agregarPrograma(HttpServletResponse resp, CalcularLoc programa, int numeroPrograma) throws Exception {
        try {
            if (numeroPrograma == 0) {
                resp.getWriter().println("<div id=\"programa" + numeroPrograma + "\" name=\"programa" + numeroPrograma + "\" style=\"display:block;\"><br/><br/>");
            } else {
                resp.getWriter().println("<div id=\"programa" + numeroPrograma + "\" name=\"programa" + numeroPrograma + "\" style=\"display:none;\"><br/><br/>");
            }
            resp.getWriter().println("<div align=\"center\" class=\"titulo1\">Programa: " + programa.getNombreProyecto()+"</div><br/><br/>");
            resp.getWriter().println("<table class=\"myTable\" style=\"margin: 0 auto;\">");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td colspan = \"6\" style=\"text-align: center;\">");
            resp.getWriter().println("<span class=\"titulo2\">Descripci&oacute;n del Programa</span>");
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>Numero de paquetes</td>");
            resp.getWriter().println("<td style=\"font-weight: bold;\"> " + programa.getPaquetes().size() + "</td>");
            resp.getWriter().println("<td>Numero de clases</td>");
            resp.getWriter().println("<td style=\"font-weight: bold;\"> " + programa.getNombreClases().size() + "</td>");
            resp.getWriter().println("<td> Numero de LOC</td>");
            resp.getWriter().println("<td style=\"font-weight: bold;\">" + programa.getContadorLoc() + "</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</table><br/><br/>");


            
            resp.getWriter().println("<table class=\"myTable\" style=\"margin: 0 auto;\">");
            resp.getWriter().println("<tr><td colspan =\"6\">");
            resp.getWriter().println("<div align=\"center\" class=\"titulo2\">Detalle de inspecci&oacute;n</div>");
            resp.getWriter().println("</td></tr>");
            resp.getWriter().println("<tr>");        
            resp.getWriter().println("<th>Numero de parte</th>");
            resp.getWriter().println("<th>Tipo de parte</th>");
            resp.getWriter().println("<th>Acceso, tipo, Nombre de parte</th>");
            resp.getWriter().println("<th>Numero de Atributos o clases</th>");
            resp.getWriter().println("<th>Numero de Metodos</th>");
            resp.getWriter().println("<th>Total de la parte</th>");
            resp.getWriter().println("</tr>");
            if (!programa.getPaquetes().isEmpty()) {
                int contadorPaquete = 0;
                int contadorclases = 0;
                Iterator it = programa.getPaquetes().keySet().iterator();
                while (it.hasNext()) {
                    contadorPaquete++;
                    String key = (String) it.next();
                    resp.getWriter().println("<tr>");
                    resp.getWriter().println("<td>" + contadorPaquete + "</td>");
                    resp.getWriter().println("<td>Paquete</td>");
                    resp.getWriter().println("<td>" + key + "</td>");
                    resp.getWriter().println("<td>" + programa.getPaquetes().get(key).size() + "</td>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("<td></td>");
                    resp.getWriter().println("</tr>");
                    for (int index = 0; index < programa.getPaquetes().get(key).size(); index++) {
                        contadorclases++;
                        resp.getWriter().println("<tr>");
                        resp.getWriter().println("<td>" + contadorclases + "</td>");
                        resp.getWriter().println("<td>Clase</td>");
                        resp.getWriter().println("<td>" + programa.getNombreClases().get(programa.getPaquetes().get(key).get(index).intValue()) + "</td>");
                        resp.getWriter().println("<td>" + programa.getNombreAtributos().get(programa.getPaquetes().get(key).get(index).intValue()).size() + "</td>");
                        resp.getWriter().println("<td>" + programa.getNombreMetodos().get(programa.getPaquetes().get(key).get(index).intValue()).size() + "</td>");
                        resp.getWriter().println("<td>" + programa.getContadorLocClases().get(programa.getPaquetes().get(key).get(index).intValue()) + "</td>");
                        resp.getWriter().println("</tr>");
                        if (!programa.getNombreAtributos().get(programa.getPaquetes().get(key).get(index).intValue()).isEmpty()) {
                            for (int i = 0; i < programa.getNombreAtributos().get(programa.getPaquetes().get(key).get(index).intValue()).size(); i++) {
                                resp.getWriter().println("<tr>");
                                resp.getWriter().println("<td>" + (i + 1) + "</td>");
                                resp.getWriter().println("<td>Atributo</td>");
                                resp.getWriter().println("<td>" + programa.getNombreAtributos().get(programa.getPaquetes().get(key).get(index).intValue()).get(i) + "</td>");
                                resp.getWriter().println("<td></td>");
                                resp.getWriter().println("<td></td>");
                                resp.getWriter().println("<td>1</td>");
                                resp.getWriter().println("</tr>");
                            }
                        }
                        if (!programa.getNombreMetodos().get(programa.getPaquetes().get(key).get(index).intValue()).isEmpty()) {
                            for (int j = 0; j < programa.getNombreMetodos().get(programa.getPaquetes().get(key).get(index).intValue()).size(); j++) {
                                resp.getWriter().println("<tr>");
                                resp.getWriter().println("<td>" + (j + 1) + "</td>");
                                resp.getWriter().println("<td>Metodo</td>");
                                resp.getWriter().println("<td>" + programa.getNombreMetodos().get(programa.getPaquetes().get(key).get(index).intValue()).get(j) + "</td>");
                                resp.getWriter().println("<td></td>");
                                resp.getWriter().println("<td></td>");
                                resp.getWriter().println("<td>" + programa.getContadorLocMetodos().get(programa.getPaquetes().get(key).get(index).intValue()).get(j) + "</td>");
                                resp.getWriter().println("</tr>");
                            }
                        }
                    }
                }
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td colspan=\"5\" align=\"right\" style=\"font-weight: bold;\">Total</td>");
                resp.getWriter().println("<td>"+ programa.getContadorLoc() + "</td>");
                resp.getWriter().println("</tr>");
                resp.getWriter().println("</table>");
                resp.getWriter().println("</div>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExceptionApp("Error al agregar los resultados del programa. " + ex.getMessage());
        }
    }

    /**
     * error
     *
     * administra los posibles mensajes de excepcion controlados en la
     * aplicacion
     *
     * @param req
     * @param resp
     * @param ex
     * @throws ServletException
     * @throws IOException
     */
    public static void error(HttpServletRequest req, HttpServletResponse resp, Exception ex) throws ServletException, IOException {
        resp.getWriter().println("Error!!! :" + ex.getMessage());
    }

    /**
     *
     * @return
     */
    public String getsDirectorioTrabajo() {
        return sDirectorioTrabajo;
    }

    /**
     *
     * @param sDirectorioTrabajo
     */
    public void setsDirectorioTrabajo(String sDirectorioTrabajo) {
        this.sDirectorioTrabajo = sDirectorioTrabajo;
    }

    /**
     *
     * @return
     */
    public String getsSeparator() {
        return sSeparator;
    }

    /**
     *
     * @param sSeparator
     */
    public void setsSeparator(String sSeparator) {
        this.sSeparator = sSeparator;
    }
}
