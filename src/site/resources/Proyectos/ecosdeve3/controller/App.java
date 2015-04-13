package com.ecos.ecosdeve3.controller;

import com.ecos.ecosdeve3.Exeptions.ExceptionApp;
import com.ecos.ecosdeve3.model.CalcularProbe;
import com.ecos.ecosdeve3.view.MainView;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 *
 *
 */
public class App extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MainView.showHome(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            MainView.showHome(req, resp);
            consoleInput(req, resp);
        } catch (Exception ex) {
            MainView.error(req, resp, ex);
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws Exception {
        //Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        Server server = new Server(80);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new App()), "/*");
        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
    }

    /**
     * Method to set the console input for the numbers
     */
    public void consoleInput(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String[] x = req.getParameterValues("x");
        String[] y = req.getParameterValues("y");
        String est = req.getParameter("estimado");
        List<CalcularProbe> listaResultados = new ArrayList<CalcularProbe>();
        listaResultados = cargarTest(req, resp, new CalcularProbe(1,new BigDecimal(386)), new CalcularProbe(3,new BigDecimal(386)), listaResultados);
        listaResultados = cargarTest(req, resp, new CalcularProbe(1,new BigDecimal(386)), new CalcularProbe(4,new BigDecimal(386)), listaResultados);
        listaResultados = cargarTest(req, resp, new CalcularProbe(2,new BigDecimal(386)), new CalcularProbe(3,new BigDecimal(386)), listaResultados);
        listaResultados = cargarTest(req, resp, new CalcularProbe(2,new BigDecimal(386)), new CalcularProbe(4,new BigDecimal(386)), listaResultados);
        CalcularProbe listaX = new CalcularProbe();
        CalcularProbe listaY = new CalcularProbe();
        LinkedList<BigDecimal> numbersListX = new LinkedList<BigDecimal>();
        LinkedList<BigDecimal> numbersListY = new LinkedList<BigDecimal>();
        for (String strElement : x) {
            try {
                numbersListX.add(new BigDecimal(strElement));
            } catch (NumberFormatException ex) {
                throw new ExceptionApp("Por Favor solo ingresar valores numericos");
            }
        }
        listaX.setList(numbersListX);
        try {
            listaX.setEstimado(new BigDecimal(est));
        } catch (NumberFormatException ex) {
            throw new ExceptionApp("Por Favor solo ingresar valores numericos");
        }
        for (String strElement : y) {
            try {
                numbersListY.add(new BigDecimal(strElement));
            } catch (NumberFormatException ex) {
                throw new ExceptionApp("Por Favor solo ingresar valores numericos");
            }
        }
        listaY.setList(numbersListY);
        listaResultados = cargarTest(req, resp, listaX, listaY, listaResultados);
        MainView.showResults(req, resp, listaResultados);
    }
    
    public List<CalcularProbe> cargarTest(HttpServletRequest req, HttpServletResponse resp,CalcularProbe listaX, CalcularProbe listaY,List<CalcularProbe> listaResultados)  throws Exception{ 
        listaX.calcularPrediccion(listaY);
        listaResultados.add(listaX);
        return listaResultados;
    }
}
