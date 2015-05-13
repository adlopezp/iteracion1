/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.tspistatusquo.view;

import com.ecos.tspistatusquo.exceptions.ExceptionApp;
import com.ecos.tspistatusquo.model.ComplejidadManager;
import com.ecos.tspistatusquo.model.PackageManager;
import com.ecos.tspistatusquo.vo.Aplicacion;
import com.ecos.tspistatusquo.vo.Atributo;
import com.ecos.tspistatusquo.vo.Clase;
import com.ecos.tspistatusquo.vo.Metodo;
import com.ecos.tspistatusquo.vo.Paquete;
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
            List<Aplicacion> aplicaciones = PackageManager.buscarProyectos();
            for (Aplicacion aplicacion : aplicaciones) {
                ComplejidadManager.calcularComplejidad(aplicacion);
            }

            resp.setContentType("text/html");
            resp.getWriter().println("<html><head>");
            resp.getWriter().println("<style type=\"text/css\">");
            resp.getWriter().println("body { font-family: 'Open Sans', sans-serif;}");
            resp.getWriter().println(".titulo1 {font-size: 20px; font-weight: bold; }");
            resp.getWriter().println(".titulo2 {font-size: 15px; font-weight: bold; }");
            resp.getWriter().println(".myTable {background-color:#F0F0F0; border-collapse:collapse;font-size:13px; width:900px; }");
            resp.getWriter().println(".myTable th {background-color:#CCCCCC;}");
            resp.getWriter().println(".myTable td, .myTable th { padding:5px;border:1px solid #000;}");
            resp.getWriter().println("</style>");

            resp.getWriter().println("<style type=\"text/css\">");
            resp.getWriter().println(".myOtherTable { background-color:#FFFFE0;border-collapse:collapse;color:#000;font-size:12px; }");
            resp.getWriter().println(".myOtherTable th { background-color:#BDB76B;color:white;width:50%; }");
            resp.getWriter().println(".myOtherTable td, .myOtherTable th { padding:5px;border:0; }");
            resp.getWriter().println(".myOtherTable td { border-bottom:1px dotted #BDB76B; }");
            resp.getWriter().println("</style>");

            resp.getWriter().println("<script>");
            resp.getWriter().println("var seleccionado = 'seleccione';");
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
            resp.getWriter().println("<option value=\"seleccione\" >Seleccione...</option>");

            for (int index = 0; index < aplicaciones.size(); index++) {
                resp.getWriter().println("<option value=\"programa" + index + "\" > " + aplicaciones.get(index).getNombre() + " </option>");
            }
            resp.getWriter().println("</select></td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</table><br/>");
            resp.getWriter().println("</div>");
            resp.getWriter().println("</div>");

            resp.getWriter().println("<div id=\"seleccione\" name=\"seleccione\" style=\"display:block;\">");
            resp.getWriter().println("</div>");

            for (int index = 0; index < aplicaciones.size(); index++) {
                agregarPrograma(resp, aplicaciones.get(index), index);
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
     * @param aplicacion
     * @param numeroPrograma
     * @throws IOException
     */
    private static void agregarPrograma(HttpServletResponse resp, Aplicacion aplicacion, int numeroPrograma) throws Exception {
        try {

            resp.getWriter().println("<div id=\"programa" + numeroPrograma + "\" name=\"programa" + numeroPrograma + "\" style=\"display:none;\"><br/><br/>");
            resp.getWriter().println("<div align=\"center\" class=\"titulo1\">Programa: " + aplicacion.getNombre() + "</div><br/><br/>");
            resp.getWriter().println("<table class=\"myTable\" style=\"margin: 0 auto;\">");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td colspan = \"6\" style=\"text-align: center;\">");
            resp.getWriter().println("<span class=\"titulo2\">Descripci&oacute;n del Programa</span>");
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<th>Numero de paquetes</thd>");
            resp.getWriter().println("<td style=\"font-weight: bold;\"> " + aplicacion.getPaquetes().size() + "</td>");
            resp.getWriter().println("<th>Numero de clases</th>");
            resp.getWriter().println("<td style=\"font-weight: bold;\"> " + aplicacion.getNumeroTotalClases() + "</td>");
            resp.getWriter().println("<th> Numero de LOC</th>");
            resp.getWriter().println("<td style=\"font-weight: bold;\">" + aplicacion.getNumeroTotalLoc() + "</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</table><br/><br/>");

            resp.getWriter().println("<div align=\"center\" class=\"titulo1\">Detalle de inspecci&oacute;n</div><br/><br/>");
            resp.getWriter().println("<br/>");

            //        resp.getWriter().println("<th>Numero de parte</th>");
//            resp.getWriter().println("<th>Tipo de parte</th>");
//            resp.getWriter().println("<th>Acceso, tipo, Nombre de parte</th>");
//            resp.getWriter().println("<th>Numero de Atributos o clases</th>");
//            resp.getWriter().println("<th>Numero de Metodos</th>");
//            resp.getWriter().println("<th>Total de la parte</th>");

            Paquete paquete;
            Clase clase;
            Atributo atributo;
            Metodo metodo;
            for (int i = 0; i < aplicacion.getPaquetes().size(); i++) {
                paquete = aplicacion.getPaquetes().get(i);
                resp.getWriter().println("<table class=\"myTable\" style=\"margin: 0 auto; \">");
                resp.getWriter().println("<tr><td colspan =\"3\">");
                resp.getWriter().println("<div align=\"center\" class=\"titulo2\">Paquete " + (i + 1) + "</div>");
                resp.getWriter().println("</td></tr>");
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<th>Nombre</th>");
                resp.getWriter().println("<th>Numero de Clases</th>");
                resp.getWriter().println("<th>Total LOC</th>");
                resp.getWriter().println("</tr>");
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td>" + paquete.getNombre() + "</td>");
                resp.getWriter().println("<td>" + paquete.getClases().size() + "</td>");
                resp.getWriter().println("<td>" + paquete.getNumeroTotalLoc() + "</td>");
                resp.getWriter().println("</tr>");
                resp.getWriter().println("</table>");
                resp.getWriter().println("<br/>");

                for (int j = 0; j < paquete.getClases().size(); j++) {
                    clase = paquete.getClases().get(j);
                    resp.getWriter().println("<table class=\"myTable\" style=\"margin: 0 auto;\">");
                    resp.getWriter().println("<tr><td colspan =\"8\">");
                    resp.getWriter().println("<div align=\"center\" class=\"titulo2\">Clase " + (j + 1) + "</div>");
                    resp.getWriter().println("</td></tr>");
                    resp.getWriter().println("<tr>");
                    resp.getWriter().println("<th>Nombre</th>");
                    resp.getWriter().println("<td colspan = \"3\">" + clase.getNombre() + "</td>");
                    resp.getWriter().println("<th>Archivo</th>");
                    resp.getWriter().println("<td>" + clase.getNombreArchivo() + "</td>");
                    resp.getWriter().println("<th>Visibilidad</th>");
                    resp.getWriter().println("<td>" + clase.getVisibilidad() + "</td>");
                    resp.getWriter().println("</tr>");
                    resp.getWriter().println("<tr>");
                    resp.getWriter().println("<th width = \"80\">Atributos</th>");
                    resp.getWriter().println("<td width = \"30\">" + clase.getAtributos().size() + "</td>");
                    resp.getWriter().println("<th width = \"120\">Metodos</th>");
                    resp.getWriter().println("<td width = \"30\">" + clase.getMetodos().size() + "</td>");
                    resp.getWriter().println("<th width = \"20\">LOC</th>");
                    resp.getWriter().println("<td>" + clase.getLineas() + "</td>");
                    resp.getWriter().println("<th width = \"200\">Complejidad Ciclomática</th>");
                    resp.getWriter().println("<td width = \"50\">" + clase.getComplejidad() + "</td>");
                    resp.getWriter().println("</tr>");
                    resp.getWriter().println("</table>");

                    if (!clase.getAtributos().isEmpty()) {
                        resp.getWriter().println("<table class=\"myTable\" style=\"margin: 0 auto;\">");
                        resp.getWriter().println("<tr><td colspan =\"8\">");
                        resp.getWriter().println("<div class=\"titulo2\">Atributos</div>");
                        resp.getWriter().println("</td></tr>");
                        resp.getWriter().println("<tr>");
                        resp.getWriter().println("<th>No.</th>");
                        resp.getWriter().println("<th>Nombre</th>");
                        resp.getWriter().println("<th>Tipo</th>");
                        resp.getWriter().println("<th>Visibilidad</th>");
                        resp.getWriter().println("</tr>");

                        for (int k = 0; k < clase.getAtributos().size(); k++) {
                            atributo = clase.getAtributos().get(k);
                            resp.getWriter().println("<tr>");
                            resp.getWriter().println("<td>" + (k + 1) + "</td>");
                            resp.getWriter().println("<td>" + atributo.getNombre() + "</td>");
                            resp.getWriter().println("<td>" + atributo.getTipo() + "</td>");
                            resp.getWriter().println("<td>" + atributo.getVisbilidad() + "</td>");
                            resp.getWriter().println("</tr>");

                        }
                        resp.getWriter().println("</table>");
                    }
                    
                    List<Metodo> constructores = clase.getConstructores();
                    
                    if (!constructores.isEmpty()) {
                        resp.getWriter().println("<table class=\"myTable\" style=\"margin: 0 auto;\">");
                        resp.getWriter().println("<tr><td colspan =\"8\">");
                        resp.getWriter().println("<div class=\"titulo2\">Constructores</div>");
                        resp.getWriter().println("</td></tr>");
                        resp.getWriter().println("<tr>");
                        resp.getWriter().println("<th>No.</th>");
                        resp.getWriter().println("<th>Visibilidad</th>");
                        resp.getWriter().println("<th>Parámetros</th>");
                        resp.getWriter().println("<th>LOC</th>");
                        resp.getWriter().println("<th>Complejidad Ciclomatica</th>");
                        resp.getWriter().println("</tr>");
                        
                        for (int k = 0; k < constructores.size(); k++) {
                            metodo = constructores.get(k);
                            resp.getWriter().println("<tr>");
                            resp.getWriter().println("<td>" + (k + 1) + "</td>");
                            resp.getWriter().println("<td>" + metodo.getVisibilidad() + "</td>");
                            resp.getWriter().println("<td>0</td>");
                            resp.getWriter().println("<td>" + metodo.getLineas()+ "</td>");
                            resp.getWriter().println("<td>" + metodo.getComplejidad()+ "</td>");
                            resp.getWriter().println("</tr>");

                        }
                        resp.getWriter().println("</table>");
                    }
                    
                    List<Metodo> metodosValidados = clase.getMetodosValidados();
                    
                    if (!metodosValidados.isEmpty()) {
                        resp.getWriter().println("<table class=\"myTable\" style=\"margin: 0 auto;\">");
                        resp.getWriter().println("<tr><td colspan =\"8\">");
                        resp.getWriter().println("<div class=\"titulo2\">Métodos</div>");
                        resp.getWriter().println("</td></tr>");
                        resp.getWriter().println("<tr>");
                        resp.getWriter().println("<th>No.</th>");
                        resp.getWriter().println("<th>Nombre</th>");
                        resp.getWriter().println("<th>Tipo</th>");
                        resp.getWriter().println("<th>Visibilidad</th>");
                        resp.getWriter().println("<th>LOC</th>");
                        resp.getWriter().println("<th>Complejidad Ciclomatica</th>");
                        resp.getWriter().println("</tr>");

                        for (int k = 0; k < metodosValidados.size(); k++) {
                            metodo = metodosValidados.get(k);
                            resp.getWriter().println("<tr>");
                            resp.getWriter().println("<td>" + (k + 1) + "</td>");
                            resp.getWriter().println("<td>" + metodo.getNombre() + "</td>");
                            resp.getWriter().println("<td>" + metodo.getTipo() + "</td>");
                            resp.getWriter().println("<td>" + metodo.getVisibilidad() + "</td>");
                            resp.getWriter().println("<td>" + metodo.getLineas()+ "</td>");
                            resp.getWriter().println("<td>" + metodo.getComplejidad()+ "</td>");
                            resp.getWriter().println("</tr>");

                        }
                        resp.getWriter().println("</table>");
                    }
                    resp.getWriter().println("<br/>");
                    resp.getWriter().println("<br/>");
                }
            }
            resp.getWriter().println("</div>");

//            if (!aplicacion.getPaquetes().isEmpty()) {
//                int contadorPaquete = 0;
//                int contadorclases = 0;
//                Iterator it = aplicacion.getPaquetes().keySet().iterator();
//                while (it.hasNext()) {
//                    contadorPaquete++;
//                    String key = (String) it.next();
//                    resp.getWriter().println("<tr>");
//                    resp.getWriter().println("<td>" + contadorPaquete + "</td>");
//                    resp.getWriter().println("<td>Paquete</td>");
//                    resp.getWriter().println("<td>" + key + "</td>");
//                    resp.getWriter().println("<td>" + aplicacion.getPaquetes().get(key).size() + "</td>");
//                    resp.getWriter().println("<td></td>");
//                    resp.getWriter().println("<td></td>");
//                    resp.getWriter().println("</tr>");
//                    for (int index = 0; index < aplicacion.getPaquetes().get(key).size(); index++) {
//                        contadorclases++;
//                        resp.getWriter().println("<tr>");
//                        resp.getWriter().println("<td>" + contadorclases + "</td>");
//                        resp.getWriter().println("<td>Clase</td>");
//                        resp.getWriter().println("<td>" + aplicacion.getNombreClases().get(aplicacion.getPaquetes().get(key).get(index).intValue()) + "</td>");
//                        resp.getWriter().println("<td>" + aplicacion.getNombreAtributos().get(aplicacion.getPaquetes().get(key).get(index).intValue()).size() + "</td>");
//                        resp.getWriter().println("<td>" + aplicacion.getNombreMetodos().get(aplicacion.getPaquetes().get(key).get(index).intValue()).size() + "</td>");
//                        resp.getWriter().println("<td>" + aplicacion.getContadorLocClases().get(aplicacion.getPaquetes().get(key).get(index).intValue()) + "</td>");
//                        resp.getWriter().println("</tr>");
//                        if (!aplicacion.getNombreAtributos().get(aplicacion.getPaquetes().get(key).get(index).intValue()).isEmpty()) {
//                            for (int i = 0; i < aplicacion.getNombreAtributos().get(aplicacion.getPaquetes().get(key).get(index).intValue()).size(); i++) {
//                                resp.getWriter().println("<tr>");
//                                resp.getWriter().println("<td>" + (i + 1) + "</td>");
//                                resp.getWriter().println("<td>Atributo</td>");
//                                resp.getWriter().println("<td>" + aplicacion.getNombreAtributos().get(aplicacion.getPaquetes().get(key).get(index).intValue()).get(i) + "</td>");
//                                resp.getWriter().println("<td></td>");
//                                resp.getWriter().println("<td></td>");
//                                resp.getWriter().println("<td>1</td>");
//                                resp.getWriter().println("</tr>");
//                            }
//                        }
//                        if (!aplicacion.getNombreMetodos().get(aplicacion.getPaquetes().get(key).get(index).intValue()).isEmpty()) {
//                            for (int j = 0; j < aplicacion.getNombreMetodos().get(aplicacion.getPaquetes().get(key).get(index).intValue()).size(); j++) {
//                                resp.getWriter().println("<tr>");
//                                resp.getWriter().println("<td>" + (j + 1) + "</td>");
//                                resp.getWriter().println("<td>Metodo</td>");
//                                resp.getWriter().println("<td>" + aplicacion.getNombreMetodos().get(aplicacion.getPaquetes().get(key).get(index).intValue()).get(j) + "</td>");
//                                resp.getWriter().println("<td></td>");
//                                resp.getWriter().println("<td></td>");
//                                resp.getWriter().println("<td>" + aplicacion.getContadorLocMetodos().get(aplicacion.getPaquetes().get(key).get(index).intValue()).get(j) + "</td>");
//                                resp.getWriter().println("</tr>");
//                            }
//                        }
//                    }
//                }
//                resp.getWriter().println("<tr>");
//                resp.getWriter().println("<td colspan=\"5\" align=\"right\" style=\"font-weight: bold;\">Total</td>");
//                resp.getWriter().println("<td>"+ aplicacion.getContadorLoc() + "</td>");
//                resp.getWriter().println("</tr>");
//                resp.getWriter().println("</table>");
//                resp.getWriter().println("</div>");
//            }
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
