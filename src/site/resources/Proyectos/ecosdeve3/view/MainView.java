/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.ecosdeve3.view;

import com.ecos.ecosdeve3.model.CalcularProbe;
import java.io.IOException;
import java.util.List;
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

        resp.getWriter().println("<script>");
        resp.getWriter().println("function addRow() {");

        resp.getWriter().println("var tableID=\"datos\";");
        resp.getWriter().println("var table = document.getElementById(tableID);");
        resp.getWriter().println("var rowCount = table.rows.length;");

        resp.getWriter().println("var row = table.insertRow(rowCount);");
        resp.getWriter().println("var cell1 = row.insertCell(0);");
        resp.getWriter().println("var cell2 = row.insertCell(1);");
        resp.getWriter().println("var element1 = document.createElement('input');");
        resp.getWriter().println("element1.type=\"text\";");
        resp.getWriter().println("element1.name=\"x\";");
        resp.getWriter().println("element1.id=\"x\";");
        resp.getWriter().println("cell1.appendChild(element1);");
        resp.getWriter().println("var element2 = document.createElement('input');");
        resp.getWriter().println("element2.type=\"text\";");
        resp.getWriter().println("element2.name=\"y\";");
        resp.getWriter().println("element2.id=\"y\";");
        resp.getWriter().println("cell2.appendChild(element2);");
        resp.getWriter().println("}");

        resp.getWriter().println("function removeRow(){");
        resp.getWriter().println("var tableID=\"datos\";");
        resp.getWriter().println("var table = document.getElementById(tableID);");
        resp.getWriter().println("var rowCount = table.rows.length;");

        resp.getWriter().println("if(rowCount>3){");
        resp.getWriter().println("table.deleteRow(-1);");
        resp.getWriter().println("}");
        resp.getWriter().println("}");
        resp.getWriter().println("</script>");
        try {

            resp.getWriter().println("Ingreso de variables");
            resp.getWriter().println("<form action=\"calc\" method=\"post\">");
            resp.getWriter().println("<table>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("<table name=\"datos\" id=\"datos\" class=\"myTable\">");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>X</td>");
            resp.getWriter().println("<td>Y</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td><input type=\"text\" name=\"x\" id=\"x\"></td>");
            resp.getWriter().println("<td><input type=\"text\" name=\"y\" id=\"x\"></td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td><input type=\"text\" name=\"x\" id=\"x\"></td>");
            resp.getWriter().println("<td><input type=\"text\" name=\"y\" id=\"x\"></td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</table>");
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td ALIGN=\"LEFT\" VALIGN=\"TOP\">");
            resp.getWriter().println("<input type=\"button\" value=\"+\" onclick=\"addRow()\"> <input type=\"button\" value=\"-\" onclick=\"removeRow()\">");
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>");
            resp.getWriter().println("Estimado X(k) :<input type=\"text\" name=\"estimado\" id=\"estimado\"><input type=\"submit\" value=\"Calc\">");
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</form> ");
        } catch (Exception e) {
            resp.getWriter().println("Ocurrio un Error : " + e.getMessage());
        }
    }

    public static void showResults(HttpServletRequest req, HttpServletResponse resp, List<CalcularProbe> res)
            throws ServletException, IOException {
        resp.getWriter().println("<table class=\"myTable\">");
        resp.getWriter().println("<tr><td colspan=\"11\">Metodo PROBE</td></tr>");
        resp.getWriter().println("<tr>");
        resp.getWriter().println("<td>Test</td>");
        resp.getWriter().println("<td colspan=\"5\">Valores Esperados</td>");
        resp.getWriter().println("<td colspan=\"5\">Valores Actuales</td>");
        resp.getWriter().println("</tr>");
        resp.getWriter().println("<tr>");
        resp.getWriter().println("<td></td>");
        resp.getWriter().println("<td>B0</td>");
        resp.getWriter().println("<td>B1</td>");
        resp.getWriter().println("<td>r(X,Y)</td>");
        resp.getWriter().println("<td>r^2</td>");
        resp.getWriter().println("<td>Y(k)</td>");
        resp.getWriter().println("<td>B0</td>");
        resp.getWriter().println("<td>B1</td>");
        resp.getWriter().println("<td>r(X,Y)</td>");
        resp.getWriter().println("<td>r^2</td>");
        resp.getWriter().println("<td>Y(k)</td>");
        resp.getWriter().println("</tr>");
        int index=1;
        for (CalcularProbe row : res) {
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>"+index+"</td>");
            resp.getWriter().println("<td>"+row.getB0() +"</td>");
            resp.getWriter().println("<td>"+row.getB1() +"</td>");
            resp.getWriter().println("<td>"+row.getCorrelacion() +"</td>");
            resp.getWriter().println("<td>"+row.getCorrelacionCuadrado() +"</td>");
            resp.getWriter().println("<td>"+row.getPrediccion() +"</td>");
            resp.getWriter().println("<td></td>");
            resp.getWriter().println("<td></td>");
            resp.getWriter().println("<td></td>");
            resp.getWriter().println("<td></td>");
            resp.getWriter().println("<td></td>");
            resp.getWriter().println("</tr>");
            index++;
        }
        resp.getWriter().println("</table>");
    }

    public static void error(HttpServletRequest req, HttpServletResponse resp, Exception ex)
            throws ServletException, IOException {
        resp.getWriter().println("Error!!! :" + ex.getMessage());
    }
}
