/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.ecosdeve4.view;

import com.ecos.ecosdeve4.model.DistribuirTamanoLOC;
import java.io.IOException;
import java.math.RoundingMode;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dev
 */
public class MainView {

    public static void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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
            DistribuirTamanoLOC calcular = new DistribuirTamanoLOC();
            calcular.calcularTamanosLoc();
            resp.getWriter().println("<table>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("Tabla 1");
            resp.getWriter().println("<table class=\"myTable\">");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("Class Name");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("Class LOC");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("Number of Methods");
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
            for (int x = 0; x < calcular.getClases().size(); x++) {
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td>");
                resp.getWriter().println(calcular.getClases().get(x));
                resp.getWriter().println("</td>");
                resp.getWriter().println("<td>");
                resp.getWriter().println(calcular.getLoc().get(x));
                resp.getWriter().println("</td>");
                resp.getWriter().println("<td>");
                resp.getWriter().println(calcular.getNumeroMetodos().get(x));
                resp.getWriter().println("</td>");
                resp.getWriter().println("</tr>");
            }
            resp.getWriter().println("</table>");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("Tabla 2");
            resp.getWriter().println("<table class=\"myTable\">");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("Chapter");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("Pages");
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
            for (int x = 0; x < calcular.getCapitulos().size(); x++) {
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td>");
                resp.getWriter().println(calcular.getCapitulos().get(x));
                resp.getWriter().println("</td>");
                resp.getWriter().println("<td>");
                resp.getWriter().println(calcular.getPaginas().get(x));
                resp.getWriter().println("</td>");
                resp.getWriter().println("</tr>");
            }
            resp.getWriter().println("</table>");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("Tabla 3");
            resp.getWriter().println("<table class=\"myTable\">");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("VS");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("S");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("M");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("L");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("VL");
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("LOC/Method");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(calcular.getLocXmetodos().get(0).setScale(4, RoundingMode.CEILING));
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(calcular.getLocXmetodos().get(1).setScale(4, RoundingMode.CEILING));
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(calcular.getLocXmetodos().get(2).setScale(4, RoundingMode.CEILING));
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(calcular.getLocXmetodos().get(3).setScale(4, RoundingMode.CEILING));
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(calcular.getLocXmetodos().get(4).setScale(4, RoundingMode.CEILING));
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("Pgs/Chapter");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(calcular.getPaginasXcapitulo().get(0).setScale(4, RoundingMode.CEILING));
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(calcular.getPaginasXcapitulo().get(1).setScale(4, RoundingMode.CEILING));
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(calcular.getPaginasXcapitulo().get(2).setScale(4, RoundingMode.CEILING));
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(calcular.getPaginasXcapitulo().get(3).setScale(4, RoundingMode.CEILING));
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(calcular.getPaginasXcapitulo().get(4).setScale(4, RoundingMode.CEILING));
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</table>");
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</table>");
        } catch (Exception e) {
            resp.getWriter().println("Ocurrio un Error : " + e.getMessage());
        }
    }

    public static void error(HttpServletRequest req, HttpServletResponse resp, Exception ex)
            throws ServletException, IOException {
        resp.getWriter().println("Error!!! :" + ex.getMessage());
    }
}